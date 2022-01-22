package app.cleancode.vsl.compiler;

import java.util.List;
import java.util.Set;

public class CompileResult {
    public final Set<NodeType> nodeTypes;
    public final String whitespacePattern;
    public final List<TokenRule> tokenRules;
    public final String rootNode;
    public final List<Rule> rules;

    public CompileResult(Set<NodeType> nodeTypes, String whitespacePattern,
            List<TokenRule> tokenRules, String rootNode, List<Rule> rules) {
        this.nodeTypes = Set.copyOf(nodeTypes);
        this.whitespacePattern = whitespacePattern;
        this.tokenRules = List.copyOf(tokenRules);
        this.rootNode = rootNode;
        this.rules = List.copyOf(rules);
    }

    @Override
    public String toString() {
        return String.format("NodeTypes: %s\nToken rules: %s\nRules: %s", nodeTypes, tokenRules,
                rules);
    }

    public String getEnumSource() {
        return String.format(
                "package app.cleancode.parser;\n\npublic enum NodeType {\n%s;\nprivate boolean terminal;\n\nprivate NodeType(boolean terminal) {\nthis.terminal = terminal;\n}\n}",
                String.join(", ", nodeTypes.stream().map(NodeType::toString).sorted().toList()));
    }

    public String getJsonSource() {
        return String.format(
                "{\n\"whitespacePattern\": \"%s\",\n\"tokens\": [\n%s\n],\n\"rootNode\": \"%s\",\n\"nodes\": [\n%s\n]\n}",
                whitespacePattern,
                String.join(",\n", tokenRules.stream().map(TokenRule::toJsonString).toList()),
                rootNode, String.join(",\n", rules.stream().map(Rule::toJsonString).toList()));
    }
}
