package app.cleancode.vsl.ast;

import app.cleancode.parser.Parser.NodeType;

public record Symbol(String identifier, String string) implements AstNode {

    @Override
    public NodeType getType() {
        return NodeType.SYMBOL;
    }

    @Override
    public String toString() {
        return identifier == null ? string : identifier;
    }

    public boolean hasIdentifier() {
        return identifier != null;
    }

    public boolean hasString() {
        return string != null;
    }

}
