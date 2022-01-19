package app.cleancode.parser;

import java.util.ArrayList;
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

    public Node match(List<Token> tokens, Map<NodeType, List<NodeDescription>> nodeDescriptions) {
        if (tokens.size() < children.size()) {
            return null;
        }
        boolean matched = true;
        List<Object> matchedChildren = new ArrayList<>();
        int matchedTokens = 0;
        for (int i = 0; i < children.size(); i++) {
            if (tokens.size() <= matchedTokens) {
                matched = false;
                break;
            }
            NodeType expected = children.get(i);
            if (expected.terminal) {
                System.out.printf(
                        "Checking for terminal symbol %s\nMatched: %d, actually there: %s\n",
                        expected, matchedTokens, tokens.get(matchedTokens));
                if (!tokens.get(matchedTokens).type().equals(expected)) {
                    matched = false;
                    break;
                } else {
                    matchedChildren.add(tokens.get(i).value());
                    matchedTokens++;
                }
            } else {
                Node subNode = null;
                for (NodeDescription nodeDescription : nodeDescriptions.get(expected)) {
                    Node matchingNode = nodeDescription
                            .match(tokens.subList(matchedTokens, tokens.size()), nodeDescriptions);
                    if (matchingNode != null && (subNode == null
                            || matchingNode.matchedTokens > subNode.matchedTokens)) {
                        subNode = matchingNode;
                    }
                }
                if (subNode != null) {
                    matchedChildren.add(subNode);
                    matchedTokens += subNode.matchedTokens;
                } else {
                    matched = false;
                }
            }
        }
        if (matched) {
            Node match = new Node(type, matchedTokens);
            childrenNames.forEach((i, name) -> {
                if (matchedChildren.get(i - 1) != null) {
                    match.children.put(name, matchedChildren.get(i - 1));
                }
            });
            return match;
        } else {
            return null;
        }
    }
}
