package app.cleancode.parser;

import java.util.HashMap;
import java.util.Map;

public class Node {
    public final NodeType type;

    public final Map<String, Object> children = new HashMap<>();

    public final int matchedTokens;

    public Node(NodeType type, int matchedTokens) {
        this.type = type;
        this.matchedTokens = matchedTokens;
    }

    @Override
    public String toString() {
        return String.format("Node [\ntype=%s,\nchildren=%s,\nmatchedTokens=%d]", type, children,
                matchedTokens);
    }
}
