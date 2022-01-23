package app.cleancode.vsl.generator;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import app.cleancode.vsl.compiler.NodeType;
import app.cleancode.vsl.compiler.Rule;

public class Generator {
    private static String generateNodeCheck(int nodeNumber, String nodeType, boolean terminal,
            Optional<String> nodeName) {
        StringBuilder result = new StringBuilder();
        String name = nodeName.orElse("node" + nodeNumber);
        result.append(String.format("Node %s;\n", name));
        if (terminal) {
            result.append(String.format(
                    "if (!(%s = tokens.get(locationCounter.location)).type.equals(NodeType.%s)) {\n",
                    name, nodeType));
        } else {
            result.append(String.format("if ((%s = parse%s(tokens, locationCounter)) == null) {\n",
                    name, nodeType));
        }
        result.append("return null;\n");
        result.append("}");
        if (terminal) {
            result.append("else {\n");
            result.append("locationCounter.location++;\n");
            result.append("}");
        }
        return result.toString();
    }

    private static String generateRuleMethod(int optionNumber, Rule rule, Set<NodeType> nodeTypes) {
        StringBuilder result = new StringBuilder();
        result.append(String.format(
                "private Node parse%s%d(List<Node> tokens, LocationCounter locationCounter) {\n",
                rule.type(), optionNumber));
        for (int i = 0; i < rule.tokens().size(); i++) {
            NodeType tokenType = getNodeType(rule.tokens().get(i), nodeTypes);
            result.append(generateNodeCheck(i, tokenType.name(), tokenType.terminal(),
                    Optional.ofNullable(rule.childNames().get(i + 1))));
            result.append('\n');
        }
        result.append(String.format("Node result = new Node(NodeType.%s);\n", rule.type()));
        rule.childNames().forEach((num, name) -> {
            result.append(String.format("result.children.put(\"%s\", %s);\n", name, name));
        });
        result.append("return result;\n");
        result.append("}");
        return result.toString();
    }

    public static String generateRuleMethod(String ruleName, List<Rule> ruleAlternatives,
            Set<NodeType> nodeTypes) {
        StringBuilder result = new StringBuilder();
        result.append(String.format(
                "public Node parse%s(List<Token> tokens, LocationCounter locationCounter) {\n",
                ruleName));
        result.append("final int oldLocationCounter = locationCounter.location;\n");
        result.append("Node result;\n");
        for (int i = 0; i < ruleAlternatives.size(); i++) {
            result.append(String.format(
                    "if ((result = parse%s%d(tokens, locationCounter) == null) {\n", ruleName, i));
            result.append("locationCounter.location = oldLocationCounter;\n");
            result.append("}else {\n");
            result.append("return result;\n");
            result.append("}\n");
        }
        result.append("return null;\n");
        result.append("}\n");
        for (int i = 0; i < ruleAlternatives.size(); i++) {
            result.append(generateRuleMethod(i, ruleAlternatives.get(i), nodeTypes));
            result.append('\n');
        }
        return result.toString();
    }

    private static NodeType getNodeType(String name, Set<NodeType> nodeTypes) {
        for (NodeType nodeType : nodeTypes) {
            if (nodeType.name().equals(name)) {
                return nodeType;
            }
        }
        return null;
    }
}
