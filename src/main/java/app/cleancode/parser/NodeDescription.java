package app.cleancode.parser;

import java.util.List;

public class NodeDescription {
    private NodeType type;
    private List<NodeType> children;

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public List<NodeType> getChildren() {
        return children;
    }

    public void setChildren(List<NodeType> children) {
        this.children = children;
    }
}
