package app.cleancode.parser;

public enum NodeType {
    // Terminal symbols
    IDENTIFIER, INTEGER, EQUALS, SEMICOLON,
    // Non terminal symbols
    ASSIGNMENT_EXPRESSION, ASSIGNMENT_EXPRESSION_LIST
}
