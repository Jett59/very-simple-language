package app.cleancode;

import java.io.BufferedInputStream;
import java.io.InputStream;
import app.cleancode.parser.Parser;

public class Entrypoint {
    public static void main(String[] args) throws Exception {
        Parser parser;
        try (InputStream inputStream = Entrypoint.class.getResourceAsStream("/grammar.json");
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            parser = new Parser(new String(bufferedInputStream.readAllBytes()));
        }
        System.out.println(parser);
    }
}
