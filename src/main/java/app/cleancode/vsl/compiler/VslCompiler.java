package app.cleancode.vsl.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import app.cleancode.parser.TokenDescription.ValueType;
import app.cleancode.vsl.ast.AstNode;
import app.cleancode.vsl.ast.ProgramNode;
import app.cleancode.vsl.ast.RuleAttribute;
import app.cleancode.vsl.ast.RuleNode;
import app.cleancode.vsl.ast.Symbol;

public class VslCompiler {
    public static CompileResult compile(ProgramNode program) {
        Set<NodeType> nodeTypes = new HashSet<>();
        String whitespacePattern = "[\\\\s\\t\\n]+";
        List<TokenRule> tokenRules = new ArrayList<>();
        List<Rule> rules = new ArrayList<>();
        for (AstNode node : program.children) {
            switch (node.getType()) {
                case RULE: {
                    RuleNode rule = (RuleNode) node;
                    if (rule.components.size() == 1 && rule.components.get(0).hasString()) {
                        String pattern = rule.components.get(0).string();
                        if (rule.name.equals("whitespace")) {
                            whitespacePattern = pattern.substring(1, pattern.length() - 1);
                        }
                        ValueType valueType = ValueType.NONE;
                        for (RuleAttribute attribute : rule.attributes) {
                            if (attribute.valueType() != null) {
                                valueType = attribute.valueType();
                            }
                        }
                        nodeTypes.add(new NodeType(rule.name, true));
                        tokenRules.add(new TokenRule(rule.name, pattern, valueType));
                    } else {
                        List<String> components = new ArrayList<>();
                        for (Symbol symbol : rule.components) {
                            if (symbol.hasIdentifier()) {
                                components.add(symbol.identifier());
                            } else {
                                String nodeTypeName = getUniqueName(symbol.string());
                                NodeType nodeType = new NodeType(nodeTypeName, true);
                                if (!nodeTypes.contains(nodeType)) {
                                    nodeTypes.add(nodeType);
                                    tokenRules.add(0, new TokenRule(nodeTypeName, symbol.string(),
                                            ValueType.NONE));
                                }
                                components.add(nodeTypeName);
                            }
                        }
                        Map<Integer, String> childNames = new HashMap<>();
                        for (RuleAttribute attribute : rule.attributes) {
                            if (attribute.childNumber() > 0) {
                                childNames.put(attribute.childNumber(), attribute.childName());
                            }
                        }
                        nodeTypes.add(new NodeType(rule.name, false));
                        rules.add(new Rule(rule.name, components, childNames));
                    }
                    break;
                }
                default:
                    throw new RuntimeException("Unknown node type " + node.getType());
            }
        }
        return new CompileResult(nodeTypes, whitespacePattern, tokenRules, "root", rules);
    }

    private static String getUniqueName(String symbolString) {
        // Encode as hex
        char[] inputChars = symbolString.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < inputChars.length; i++) {
            int character = (int) inputChars[i];
            hex.append(Integer.toHexString(character));
        }
        return "UNNAMED" + hex.toString();
    }
}
