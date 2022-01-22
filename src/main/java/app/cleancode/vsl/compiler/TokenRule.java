package app.cleancode.vsl.compiler;

import app.cleancode.parser.TokenDescription.ValueType;

public record TokenRule(String type, String pattern, ValueType valueType) {

    public String toJsonString() {
        return String.format("{\n\"valueType\": \"%s\",\n\"nodeType\": \"%s\",\n\"pattern\": %s\n}",
                valueType, type, pattern);
    }

}
