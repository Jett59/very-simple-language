package app.cleancode.vsl.ast;

import app.cleancode.parser.NodeType;

public record Symbol(String identifier, String string) implements AstNode {

    @Override
    public NodeType getType() {
        return NodeType.SYMBOL;
    }

    @Override
    public String toString() {
        return identifier == null ? string : identifier;
    }

}
