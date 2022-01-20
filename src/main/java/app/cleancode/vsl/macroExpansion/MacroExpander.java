package app.cleancode.vsl.macroExpansion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import app.cleancode.parser.NodeType;
import app.cleancode.parser.ParseException;
import app.cleancode.vsl.ast.AstNode;
import app.cleancode.vsl.ast.MacroDefinitionNode;
import app.cleancode.vsl.ast.MacroInvocationNode;
import app.cleancode.vsl.ast.ProgramNode;
import app.cleancode.vsl.ast.RuleNode;
import app.cleancode.vsl.ast.Symbol;

public class MacroExpander {
    public static AstNode expand(AstNode node) throws ParseException {
        if (!node.getType().equals(NodeType.PROGRAM)) {
            throw new IllegalArgumentException("Node must be a PROGRAM, not a " + node.getType());
        } else {
            Map<String, MacroDefinitionNode> macros = new HashMap<>();
            List<AstNode> newChildren = new ArrayList<>();
            int expansions = 0;
            for (AstNode subNode : ((ProgramNode) node).children) {
                switch (subNode.getType()) {
                    case MACRO_DEFINITION: {
                        MacroDefinitionNode definitionNode = (MacroDefinitionNode) subNode;
                        macros.put(definitionNode.name, definitionNode);
                        break;
                    }
                    case MACRO_INVOCATION: {
                        MacroInvocationNode invocationNode = (MacroInvocationNode) subNode;
                        if (!macros.containsKey(invocationNode.name)) {
                            throw new ParseException(0,
                                    String.format("Macro %s not defined", invocationNode.name));
                        } else {
                            newChildren.addAll(
                                    expandMacro(invocationNode, macros.get(invocationNode.name)));
                            expansions++;
                        }
                        break;
                    }
                    default:
                        newChildren.add(subNode);
                }
            }
            ProgramNode newNode = new ProgramNode(newChildren);
            if (expansions > 0) {
                return expand(newNode);
            } else {
                return node;
            }
        }
    }

    private static List<AstNode> expandMacro(MacroInvocationNode invocation,
            MacroDefinitionNode definition) throws ParseException {
        if (definition.parameters.size() != invocation.arguments.size()) {
            throw new ParseException(0, "Mismatch of macro parameters");
        }
        Map<String, Symbol> macroArguments =
                IntStream.range(0, definition.parameters.size()).boxed().collect(
                        Collectors.toMap(definition.parameters::get, invocation.arguments::get));
        return definition.statements.stream().map(node -> resolvSymbols(node, macroArguments))
                .toList();
    }

    private static AstNode resolvSymbols(AstNode node, Map<String, Symbol> symbolTable) {
        switch (node.getType()) {
            case RULE: {
                RuleNode rule = (RuleNode) node;
                String newName;
                List<Symbol> newComponents = new ArrayList<>();
                if (rule.name.startsWith("$")) {
                    newName = symbolTable.get(rule.name.substring(1)).identifier();
                } else {
                    newName = rule.name;
                }
                for (Symbol symbol : rule.components) {
                    if (symbol.identifier() != null && symbol.identifier().startsWith("$")) {
                        newComponents.add(symbolTable.get(symbol.identifier().substring(1)));
                    } else {
                        newComponents.add(symbol);
                    }
                }
                return new RuleNode(newName, newComponents);
            }
            case MACRO_INVOCATION: {
                MacroInvocationNode invocation = (MacroInvocationNode) node;
                String newName;
                List<Symbol> newArguments = new ArrayList<>();
                if (invocation.name.startsWith("$")) {
                    newName = symbolTable.get(invocation.name.substring(1)).identifier();
                } else {
                    newName = invocation.name;
                }
                for (Symbol symbol : invocation.arguments) {
                    if (symbol.identifier() != null && symbol.identifier().startsWith("$")) {
                        newArguments.add(symbolTable.get(symbol.identifier().substring(1)));
                    } else {
                        newArguments.add(symbol);
                    }
                }
                return new MacroInvocationNode(newName, newArguments);
            }
            default:
                return node;
        }
    }
}
