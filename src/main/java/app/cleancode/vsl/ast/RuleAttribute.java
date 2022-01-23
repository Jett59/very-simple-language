package app.cleancode.vsl.ast;

import app.cleancode.parser.Parser.NodeType;

public record RuleAttribute(int childNumber, String childName, ValueType valueType)
        implements AstNode {

    @Override
    public NodeType getType() {
        return NodeType.RULE_ATTRIBUTE;
    }

}
