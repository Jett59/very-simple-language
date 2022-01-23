package app.cleancode.vsl.compiler;

import java.util.List;
import java.util.Map;

public record Rule(String type, List<String> tokens, Map<Integer, String> childNames) {
    public Rule(String type, List<String> tokens, Map<Integer, String> childNames) {
        this.type = type;
        this.tokens = List.copyOf(tokens);
        this.childNames = Map.copyOf(childNames);
    }
}
