package app.cleancode.parser;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Parser {
    private final ParserDescription description;

    public Parser(String grammar) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            description = mapper.readValue(grammar, ParserDescription.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return String.format("Parser [description=%s]", description);
    }
}
