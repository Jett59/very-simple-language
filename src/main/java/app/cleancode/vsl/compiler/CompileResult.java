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
                "public static enum NodeType {\n%s;\npublic boolean terminal;\n\nprivate NodeType(boolean terminal) {\nthis.terminal = terminal;\n}\n}\n",
                String.join(", ", nodeTypes.stream().map(NodeType::toString).sorted().toList()));
    }
}
