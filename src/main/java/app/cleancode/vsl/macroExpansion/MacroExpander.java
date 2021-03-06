package app.cleancode.vsl.macroExpansion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import app.cleancode.parser.Parser.NodeType;
import app.cleancode.vsl.ast.AstNode;
import app.cleancode.vsl.ast.MacroDefinitionNode;
import app.cleancode.vsl.ast.MacroInvocationNode;
import app.cleancode.vsl.ast.ProgramNode;
import app.cleancode.vsl.ast.RuleAttribute;
import app.cleancode.vsl.ast.RuleNode;
import app.cleancode.vsl.ast.Symbol;

public class MacroExpander {
    public static AstNode expand(AstNode node) {
        if (!node.getType().equals(NodeType.root)) {
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
                            throw new RuntimeException(
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
                return newNode;
            }
        }
    }

    private static List<AstNode> expandMacro(MacroInvocationNode invocation,
            MacroDefinitionNode definition) {
        if (definition.parameters.size() != invocation.arguments.size()) {
            throw new RuntimeException("Mismatch of macro parameters");
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
                List<RuleAttribute> newAttributes = new ArrayList<>();
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
                for (RuleAttribute attribute : rule.attributes) {
                    if (attribute.childName().startsWith("$")) {
                        newAttributes.add(new RuleAttribute(attribute.childNumber(),
                                symbolTable.get(attribute.childName().substring(1)).identifier(),
                                attribute.valueType()));
                    } else {
                        newAttributes.add(attribute);
                    }
                }
                return new RuleNode(newName, newComponents, newAttributes);
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
