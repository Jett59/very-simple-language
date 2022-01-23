package app.cleancode;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import app.cleancode.parser.Parser;
import app.cleancode.parser.Parser.Node;
import app.cleancode.vsl.ast.AstNode;
import app.cleancode.vsl.ast.ProgramNode;
import app.cleancode.vsl.compiler.CompileResult;
import app.cleancode.vsl.compiler.VslCompiler;
import app.cleancode.vsl.generator.Generator;
import app.cleancode.vsl.macroExpansion.MacroExpander;
import app.cleancode.vsl.postParse.PostParser;

public class Entrypoint {
    public static void main(String[] args) throws Exception {
        String inputFileName = "grammar.vsl";
        Parser parser = new Parser();
        List<String> programLines = Files.readAllLines(Paths.get(inputFileName));
        Node parseTree = null;
        try {
            parseTree = parser.parse(String.join("\n", programLines));
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } catch (StackOverflowError e) {
            e.printStackTrace();
            System.err.println("stack overflow");
        }
        AstNode ast = PostParser.postParse(parseTree);
        ast = MacroExpander.expand(ast);
        CompileResult compileResult = VslCompiler.compile((ProgramNode) ast);
        // Remove file extension from input file and add 'target/' to the beginning
        String outputDirectory =
                Paths.get("target", inputFileName.substring(0, inputFileName.lastIndexOf('.')))
                        .toString();
        try {
            new File(outputDirectory).mkdirs();
            Files.write(Paths.get(outputDirectory, "Parser.java"),
                    Generator.generateParserClass(compileResult, Optional.empty())
                            .getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("Failed to write output files", e);
        }
    }
}
