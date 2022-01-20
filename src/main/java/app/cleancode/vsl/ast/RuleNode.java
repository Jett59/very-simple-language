package app.cleancode.vsl.ast;

import java.util.List;
import app.cleancode.parser.NodeType;

public class RuleNode implements AstNode {
    public final String name;
    public final List<Symbol> components;

    public RuleNode(String name, List<Symbol> components) {
        this.name = name;
        this.components = List.copyOf(components);
    }

    @Override
    public NodeType getType() {
        return NodeType.RULE;
    }

    @Override
    public String toString() {
        return String.format("Rule: '%s' requiring %s", name, components);
    }

}
