package app.cleancode;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import app.cleancode.parser.ParseException;
import app.cleancode.parser.Parser;

public class Entrypoint {
    public static void main(String[] args) throws Exception {
        Parser parser;
        try (InputStream inputStream = Entrypoint.class.getResourceAsStream("/grammar.json");
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            parser = new Parser(new String(bufferedInputStream.readAllBytes()));
        }
        List<String> programLines = Files.readAllLines(Paths.get("test.vsl"));
        try {
            parser.parse(String.join("\n", programLines));
        } catch (ParseException e) {
            System.err.printf("test.vsl:%d: Error: %s\n%s\n", e.line, e.getMessage(),
                    programLines.get(e.line));
            System.exit(1);
        }
    }
}
