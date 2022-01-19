package app.cleancode.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeDescription {
    private NodeType type;
    private List<NodeType> children;
    private Map<Integer, String> childrenNames = new HashMap<>();

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

    public Map<Integer, String> getChildrenNames() {
        return childrenNames;
    }

    public void setChildrenNames(Map<Integer, String> childrenNames) {
        this.childrenNames = childrenNames;
    }

    @Override
    public String toString() {
        return String.format("NodeDescription [\ntype=%s,\nchildren=%s,\nchildrenNames=%s]", type,
                children, childrenNames);
    }
}
