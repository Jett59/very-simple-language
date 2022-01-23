package app.cleancode.vsl.compiler;

import app.cleancode.vsl.ast.ValueType;

public record TokenRule(String type, String pattern, ValueType valueType) {

}
