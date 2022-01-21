package app.cleancode.vsl.ast;

import app.cleancode.parser.NodeType;

public record RuleAttribute(int childNumber, String childName) implements AstNode {

    @Override
    public NodeType getType() {
        return NodeType.RULE_ATTRIBUTE;
    }

}
