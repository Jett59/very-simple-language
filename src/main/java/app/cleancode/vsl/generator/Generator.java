package app.cleancode.vsl.generator;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import app.cleancode.parser.TokenDescription.ValueType;
import app.cleancode.vsl.compiler.CompileResult;
import app.cleancode.vsl.compiler.NodeType;
import app.cleancode.vsl.compiler.Rule;
import app.cleancode.vsl.compiler.TokenRule;

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
        result.append(String.format("if (tokens.size() <= locationCounter.location + %d) {\n",
                rule.tokens().size()));
        result.append("return null;\n");
        result.append("}\n");
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

    private static String generateRuleMethod(String ruleName, List<Rule> ruleAlternatives,
            Set<NodeType> nodeTypes) {
        StringBuilder result = new StringBuilder();
        result.append(String.format(
                "private Node parse%s(List<Node> tokens, LocationCounter locationCounter) {\n",
                ruleName));
        result.append("final int oldLocationCounter = locationCounter.location;\n");
        result.append("Node result = null;\n");
        result.append("int matchedTokens = 0;\n");
        result.append("Node temp;\n");
        for (int i = 0; i < ruleAlternatives.size(); i++) {
            result.append(String.format(
                    "if ((temp = parse%s%d(tokens, locationCounter)) == null) {\n", ruleName, i));
            result.append("locationCounter.location = oldLocationCounter;\n");
            result.append(
                    "}else if (matchedTokens < locationCounter.location - oldLocationCounter) {\n");
            result.append("result = temp;\n");
            result.append("matchedTokens = locationCounter.location - oldLocationCounter;\n");
            result.append("}\n");
        }
        result.append("locationCounter.location = oldLocationCounter + matchedTokens;\n");
        result.append("return result;\n");
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

    private static String generateTokenCheck(String tokenName, ValueType valueType) {
        StringBuilder result = new StringBuilder();
        result.append(String.format("Matcher %sMatcher = %sPattern.matcher(temp);\n", tokenName,
                tokenName));
        result.append(String.format("if (%sMatcher.find() && %sMatcher.start() == 0) {\n",
                tokenName, tokenName));
        result.append(String.format("String newMatchString = %sMatcher.group();\n", tokenName));
        result.append(
                "if (matchString == null || matchString.length() < newMatchString.length()) {\n");
        switch (valueType) {
            case NONE: {
                result.append(String.format("matchToken = new Node(NodeType.%s);\n", tokenName));
                break;
            }
            case NUMBER: {
                result.append(String.format(
                        "matchToken = new Node(NodeType.%s, Double.parseDouble(newMatchString));\n",
                        tokenName));
                break;
            }
            case STRING: {
                result.append(String.format("matchToken = new Node(NodeType.%s, newMatchString);\n",
                        tokenName));
                break;
            }
        }
        result.append("matchString = newMatchString;\n");
        result.append("}\n");
        result.append("}\n");
        return result.toString();
    }

    private static String generateLexMethod(List<TokenRule> tokenRules, String whitespacePattern) {
        StringBuilder result = new StringBuilder();
        result.append("private List<Node> lex(String input) {\n");
        for (TokenRule token : tokenRules) {
            result.append(String.format("Pattern %sPattern = Pattern.compile(%s);\n", token.type(),
                    token.pattern()));
        }
        result.append("List<Node> result = new ArrayList<>();\n");
        result.append("String temp = input;\n");
        result.append("while(temp.length() > 0) {\n");
        result.append("String matchString = null;\n");
        result.append("Node matchToken = null;\n");
        result.append(
                String.format("temp = temp.replaceFirst(\"^%s\", \"\");\n", whitespacePattern));
        result.append("if (temp.length() == 0) {\n");
        result.append("break;\n");
        result.append("}\n");
        for (TokenRule token : tokenRules) {
            result.append(generateTokenCheck(token.type(), token.valueType()));
        }
        result.append("if (matchToken != null) {\n");
        result.append("result.add(matchToken);\n");
        result.append("temp = temp.substring(matchString.length());\n");
        result.append("}else {\n");
        result.append("throw new IllegalArgumentException(\"Unknown token\");\n");
        result.append("}\n");
        result.append("}\n");
        result.append("return result;\n");
        result.append("}\n");
        return result.toString();
    }

    private static String locationTypeClass =
            "private static class LocationCounter {\nint location = 0;\n}\n";

    private static String nodeClass =
            "public static record Node(NodeType type, Object value, Map<String, Node> children) {\n"
                    + "public Node(NodeType type, Object value) {\n"
                    + "this(type, value, new HashMap<>());\n}\n"
                    + "public Node(NodeType type) {\nthis(type, null);\n}\n}\n";

    public static String generateParserClass(CompileResult compileResult,
            Optional<String> packageName) {
        StringBuilder result = new StringBuilder();
        if (packageName.isPresent()) {
            result.append(String.format("package %s;\n", packageName.get()));
        }
        result.append("import java.util.List;\n");
        result.append("import java.util.ArrayList;\n");
        result.append("import java.util.regex.Pattern;\n");
        result.append("import java.util.regex.Matcher;\n");
        result.append("import java.util.Map;\n");
        result.append("import java.util.HashMap;\n");
        result.append("public class Parser {\n");
        result.append(locationTypeClass);
        result.append(nodeClass);
        result.append(compileResult.getEnumSource());
        result.append(generateLexMethod(compileResult.tokenRules, compileResult.whitespacePattern));
        compileResult.rules.stream().collect(Collectors.groupingBy(Rule::type))
                .forEach((name, alternatives) -> {
                    result.append(generateRuleMethod(name, alternatives, compileResult.nodeTypes));
                });
        result.append("public Node parse(String input) {\n");
        result.append(String.format("return parse%s(lex(input), new LocationCounter());\n",
                compileResult.rootNode));
        result.append("}\n");
        result.append("}\n");
        return result.toString();
    }
}
