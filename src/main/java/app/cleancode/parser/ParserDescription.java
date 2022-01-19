package app.cleancode.parser;

import java.util.List;

public class ParserDescription {
    private List<TokenDescription> tokens;
    private NodeType rootNode;
    private List<NodeDescription> nodes;

    public List<TokenDescription> getTokens() {
        return tokens;
    }

    public void setTokens(List<TokenDescription> tokens) {
        this.tokens = tokens;
    }

    public NodeType getRootNode() {
        return rootNode;
    }

    public void setRootNode(NodeType rootNode) {
        this.rootNode = rootNode;
    }

    public List<NodeDescription> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeDescription> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return String.format("ParserDescription [tokens=%s, rootNode=%s, nodes=%s]", tokens,
                rootNode, nodes);
    }
}
