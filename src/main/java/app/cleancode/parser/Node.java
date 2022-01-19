package app.cleancode.parser;

import java.util.HashMap;
import java.util.Map;

public class Node {
    public final NodeType type;

    public final Map<String, Object> children = new HashMap<>();

    public Node(NodeType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("Node [type=%s, children=%s]", type, children);
    }
}
