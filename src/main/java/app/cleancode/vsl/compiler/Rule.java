package app.cleancode.vsl.compiler;

import java.util.List;
import java.util.Map;

public record Rule(String type, List<String> tokens, Map<Integer, String> childNames) {
    public Rule(String type, List<String> tokens, Map<Integer, String> childNames) {
        this.type = type;
        this.tokens = List.copyOf(tokens);
        this.childNames = Map.copyOf(childNames);
    }

    public String toJsonString() {
        StringBuilder childNamesString = new StringBuilder();
        childNames.forEach((num, name) -> {
            if (!childNamesString.isEmpty()) {
                childNamesString.append(",\n");
            }
            childNamesString.append(String.format("\"%d\": \"%s\"", num, name));
        });
        return String.format(
                "{\n\"type\": \"%s\",\n\"children\": [\n%s\n],\n\"childrenNames\": {\n%s\n}\n}",
                type,
                String.join(",\n", tokens.stream().map(s -> "\"".concat(s.concat("\""))).toList()),
                childNamesString.toString());
    }
}
