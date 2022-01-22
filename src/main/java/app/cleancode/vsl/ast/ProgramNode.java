package app.cleancode.vsl.ast;

import java.util.List;
import app.cleancode.parser.NodeType;

public class ProgramNode implements AstNode {
    public final List<AstNode> children;

    public ProgramNode(List<AstNode> children) {
        this.children = List.copyOf(children);
    }

    @Override
    public NodeType getType() {
        return NodeType.root;
    }

    @Override
    public String toString() {
        return String.format("Program:\n%s",
                String.join(",\n", children.stream().map(AstNode::toString).toList()));
    }

}
