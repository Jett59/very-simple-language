package app.cleancode.vsl.compiler;

import java.util.List;
import java.util.Set;

public class CompileResult {
    public final Set<String> nodeTypes;
    public final List<TokenRule> tokenRules;
    public final List<Rule> rules;

    public CompileResult(Set<String> nodeTypes, List<TokenRule> tokenRules, List<Rule> rules) {
        this.nodeTypes = Set.copyOf(nodeTypes);
        this.tokenRules = List.copyOf(tokenRules);
        this.rules = List.copyOf(rules);
    }

    @Override
    public String toString() {
        return String.format("NodeTypes: %s\nToken rules: %s\nRules: %s", nodeTypes, tokenRules,
                rules);
    }
}
