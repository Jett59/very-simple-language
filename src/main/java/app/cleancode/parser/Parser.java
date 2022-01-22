package app.cleancode.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Parser {
    private final ParserDescription description;
    private final Map<NodeType, List<NodeDescription>> nodeDescriptions;

    public Parser(String grammar) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            description = mapper.readValue(grammar, ParserDescription.class);
            nodeDescriptions = description.getNodes().stream()
                    .collect(Collectors.groupingBy(NodeDescription::getType));
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
            Token matchToken = null;
            for (TokenDescription token : description.getTokens()) {
                Matcher matcher = Pattern.compile(token.getPattern()).matcher(temp);
                if (matcher.find() && matcher.start() == 0) {
                    String newMatch = matcher.group();
                    if (match == null || newMatch.length() > match.length()) {
                        match = newMatch;
                        matchToken = new Token(token.getNodeType(), token.getValue(match));
                    }
                }
            }
            if (match == null) {
                int line = (int) program.substring(0, program.indexOf(temp)).lines().count();
                throw new ParseException(line, "Unknown token");
            } else {
                tokens.add(matchToken);
                temp = temp.substring(match.length());
                temp = temp.replaceFirst("^" + description.getWhitespacePattern(), "");
            }
        }
        return tokens;
    }

    public Node parse(String program) throws ParseException {
        List<Token> programTokens = findTokens(program);
        Node result = null;
        for (NodeDescription nodeDescription : nodeDescriptions.get(description.getRootNode())) {
            Node match = nodeDescription.match(programTokens, nodeDescriptions);
            if (match != null && (result == null || match.matchedTokens > result.matchedTokens)) {
                result = match;
            }
        }
        if (result == null) {
            throw new ParseException(0, "Failed to parse program");
        } else if (result.matchedTokens < programTokens.size()) {
            System.out.println(result);
            throw new ParseException((int) program.lines().count() - 1,
                    "Trailing tokens at end of file");
        }
        return result;
    }

}
