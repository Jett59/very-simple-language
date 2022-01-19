package app.cleancode.parser;

public enum NodeType {
    // Terminal symbols
    IDENTIFIER(true), INTEGER(true), EQUALS(true), SEMICOLON(true), COMMA(true),
    // Non terminal symbols
    ASSIGNMENT_EXPRESSION(false), ASSIGNMENT_EXPRESSION_LIST(false);

    public final boolean terminal;

    private NodeType(boolean terminal) {
        this.terminal = terminal;
    }
}
