package app.cleancode.parser;

public enum NodeType {
IDENTIFIER(true), INTEGER(true), MACRO_DEFINITION(false), MACRO_INVOCATION(false), NUMBER_SPECIFIER(true), PARAMETER_LIST(false), PROGRAM_ELEMENT(false), PROGRAM_ELEMENT_LIST(false), RULE(false), RULE_ATTRIBUTE(false), RULE_ATTRIBUTE_LIST(false), RULE_LIST(false), STRING(true), STRING_SPECIFIER(true), SYMBOL(false), SYMBOL_LIST(false), UNNAMED222c22(true), UNNAMED223a22(true), UNNAMED223b22(true), UNNAMED223d22(true), UNNAMED225c5c2822(true), UNNAMED225c5c2922(true), UNNAMED225c5c7b22(true), UNNAMED225c5c7d22(true), UNNAMED226d6163726f22(true), root(false), whitespace(true);
public boolean terminal;

private NodeType(boolean terminal) {
this.terminal = terminal;
}
}