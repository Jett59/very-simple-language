package app.cleancode.parser;

public enum NodeType {
    // Terminal symbols
    IDENTIFIER(true), INTEGER(true), STRING(true), EQUALS(true), SEMICOLON(true), COLON(
            true), COMMA(true), MACRO_SPECIFIER(true), LEFT_PAREN(true), RIGHT_PAREN(
                    true), LEFT_BRACE(true), RIGHT_BRACE(
                            true), STRING_SPECIFIER(true), NUMBER_SPECIFIER(true),
    // Non terminal symbols
    PROGRAM(false), PROGRAM_ELEMENT_LIST(false), PROGRAM_ELEMENT(false), MACRO_DEFINITION(
            false), PARAMETER_LIST(false), RULE_LIST(false), RULE(false), MACRO_INVOCATION(
                    false), SYMBOL_LIST(false), SYMBOL(
                            false), RULE_ATTRIBUTE_LIST(false), RULE_ATTRIBUTE(false);

    public final boolean terminal;

    private NodeType(boolean terminal) {
        this.terminal = terminal;
    }
}
