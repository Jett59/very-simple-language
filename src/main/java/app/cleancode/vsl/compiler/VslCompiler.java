package app.cleancode.vsl.compiler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import app.cleancode.vsl.ast.AstNode;
import app.cleancode.vsl.ast.ProgramNode;
import app.cleancode.vsl.ast.RuleNode;
import app.cleancode.vsl.ast.Symbol;

public class VslCompiler {
    public static CompileResult compile(ProgramNode program) {
        Set<String> nodeTypes = new HashSet<>();
        List<TokenRule> tokenRules = new ArrayList<>();
        List<Rule> rules = new ArrayList<>();
        for (AstNode node : program.children) {
            switch (node.getType()) {
                case RULE: {
                    RuleNode rule = (RuleNode) node;
                    nodeTypes.add(rule.name);
                    if (rule.components.size() == 1 && rule.components.get(0).hasString()) {
                        tokenRules.add(new TokenRule(rule.name, rule.components.get(0).string()));
                    } else {
                        List<String> components = new ArrayList<>();
                        for (Symbol symbol : rule.components) {
                            if (symbol.hasIdentifier()) {
                                components.add(symbol.identifier());
                            } else {
                                String nodeType = getUniqueName(symbol.string());
                                if (!nodeTypes.contains(nodeType)) {
                                    nodeTypes.add(nodeType);
                                    tokenRules.add(new TokenRule(nodeType, symbol.string()));
                                }
                                components.add(nodeType);
                            }
                        }
                        rules.add(new Rule(rule.name, components));
                    }
                    break;
                }
                default:
                    throw new RuntimeException("Unknown node type " + node.getType());
            }
        }
        return new CompileResult(nodeTypes, tokenRules, rules);
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
