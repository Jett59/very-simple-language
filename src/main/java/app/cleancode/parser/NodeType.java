package app.cleancode.parser;

public enum NodeType {
    // Terminal symbols
    IDENTIFIER(true), INTEGER(true), EQUALS(true), SEMICOLON(true), COMMA(true), FUNCTION_SPECIFIER(
            true), LEFT_PAREN(true), RIGHT_PAREN(true), LEFT_BRACE(true), RIGHT_BRACE(true),
    // Non terminal symbols
    PROGRAM(false), PROGRAM_ELEMENT_LIST(false), PROGRAM_ELEMENT(false), FUNCTION_DEFINITION(
            false), FUNCTION_DECLARATION(false);

    public final boolean terminal;

    private NodeType(boolean terminal) {
        this.terminal = terminal;
    }
}
