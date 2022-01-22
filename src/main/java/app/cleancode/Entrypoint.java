package app.cleancode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import app.cleancode.parser.Node;
import app.cleancode.parser.ParseException;
import app.cleancode.parser.Parser;
import app.cleancode.vsl.ast.AstNode;
import app.cleancode.vsl.ast.ProgramNode;
import app.cleancode.vsl.compiler.CompileResult;
import app.cleancode.vsl.compiler.Rule;
import app.cleancode.vsl.compiler.VslCompiler;
import app.cleancode.vsl.generator.Generator;
import app.cleancode.vsl.macroExpansion.MacroExpander;
import app.cleancode.vsl.postParse.PostParser;

public class Entrypoint {
    public static void main(String[] args) throws Exception {
        String inputFileName = "grammar.vsl";
        Parser parser;
        try (InputStream inputStream = Entrypoint.class.getResourceAsStream("/grammar.json");
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
            parser = new Parser(new String(bufferedInputStream.readAllBytes()));
        }
        List<String> programLines = Files.readAllLines(Paths.get(inputFileName));
        Node parseTree = null;
        try {
            parseTree = parser.parse(String.join("\n", programLines));
        } catch (ParseException e) {
            System.err.printf("test.vsl:%d: Error: %s\n%s\n", e.line, e.getMessage(),
                    programLines.get(e.line));
            System.exit(1);
        } catch (StackOverflowError e) {
            System.err.println("Stack overflow error");
        } catch (Exception e) {
            e.printStackTrace();
        }
        AstNode ast = PostParser.postParse(parseTree);
        ast = MacroExpander.expand(ast);
        CompileResult compileResult = VslCompiler.compile((ProgramNode) ast);
        for (Rule rule : compileResult.rules) {
            System.out.println(Generator.generateRuleMethod(rule, compileResult.nodeTypes));
        }
        // Remove file extension from input file and add 'target/' to the beginning
        String outputDirectory =
                Paths.get("target", inputFileName.substring(0, inputFileName.lastIndexOf('.')))
                        .toString();
        try {
            new File(outputDirectory).mkdirs();
            Files.write(Paths.get(outputDirectory, "NodeType.java"),
                    compileResult.getEnumSource().getBytes(StandardCharsets.UTF_8));
            Files.write(Paths.get(outputDirectory, "grammar.json"),
                    compileResult.getJsonSource().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Failed to write output files", e);
        }
    }
}
