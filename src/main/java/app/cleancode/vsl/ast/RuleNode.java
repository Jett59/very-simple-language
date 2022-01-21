package app.cleancode.vsl.ast;

import java.util.List;
import app.cleancode.parser.NodeType;

public class RuleNode implements AstNode {
    public final String name;
    public final List<Symbol> components;
    public final List<RuleAttribute> attributes;

    public RuleNode(String name, List<Symbol> components, List<RuleAttribute> attributes) {
        this.name = name;
        this.components = List.copyOf(components);
        this.attributes = List.copyOf(attributes);
    }

    @Override
    public NodeType getType() {
        return NodeType.RULE;
    }

    @Override
    public String toString() {
        return String.format("Rule: '%s' composed of %s with attributes %s", name, components,
                attributes);
    }

}
