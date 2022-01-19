package app.cleancode.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        return String.format("Parser [\ndescription=%s]", description);
    }

    private List<Token> findTokens(String program) throws ParseException {
        List<Token> tokens = new ArrayList<>();
        String temp = program.replaceFirst("^" + description.getWhitespacePattern(), "");
        while (temp.length() > 0) {
            String match = null;
            for (TokenDescription token : description.getTokens()) {
                Matcher matcher = Pattern.compile(token.getPattern()).matcher(temp);
                if (matcher.find() && matcher.start() == 0) {
                    match = matcher.group();
                    tokens.add(new Token(token.getNodeType(), token.getValue(match)));
                    break;
                }
            }
            System.out.println(temp);
            if (match == null) {
                int line = (int) program.substring(0, program.indexOf(temp)).lines().count();
                throw new ParseException(line, "Unknown token");
            } else {
                temp = temp.substring(match.length());
                temp = temp.replaceFirst("^" + description.getWhitespacePattern(), "");
            }
        }
        return tokens;
    }

    public Node parse(String program) throws ParseException {
        List<Token> programTokens = findTokens(program);
        System.out.println(programTokens);
        return null;
    }

}
