package app.cleancode.vsl.compiler;

import java.util.List;

public record Rule(String type, List<String> tokens) {
    public Rule(String type, List<String> tokens) {
        this.type = type;
        this.tokens = List.copyOf(tokens);
    }
}
