package app.cleancode.vsl.postParse;

import java.util.ArrayList;
import java.util.List;
import app.cleancode.parser.Node;
import app.cleancode.vsl.ast.AstNode;
import app.cleancode.vsl.ast.MacroDefinitionNode;
import app.cleancode.vsl.ast.MacroInvocationNode;
import app.cleancode.vsl.ast.ProgramNode;
import app.cleancode.vsl.ast.RuleNode;
import app.cleancode.vsl.ast.Symbol;

public class PostParser {
    public static AstNode postParse(Node node) throws PostParseException {
        switch (node.type) {
            case PROGRAM: {
                return new ProgramNode(postParseList((Node) node.children.get("elements")));
            }
            case PROGRAM_ELEMENT: {
                return postParse((Node) node.children.get("element"));
            }
            case MACRO_DEFINITION: {
                return new MacroDefinitionNode((String) node.children.get("name"),
                        postParseList((Node) node.children.get("parameters")).stream()
                                .map(subNode -> (Symbol) subNode).map(symbol -> symbol.identifier())
                                .toList(),
                        postParseList((Node) node.children.get("body")));
            }
            case RULE: {
                return new RuleNode((String) node.children.get("symbol"),
                        postParseList((Node) node.children.get("components")).stream()
                                .map(subNode -> (Symbol) subNode).toList());
            }
            case SYMBOL: {
                return new Symbol((String) node.children.get("symbol"),
                        (String) node.children.get("string"));
            }
            case MACRO_INVOCATION: {
                return new MacroInvocationNode((String) node.children.get("name"),
                        postParseList((Node) node.children.get("arguments")).stream()
                                .map(subNode -> (Symbol) subNode).toList());
            }
            default:
                if (node.type.terminal) {
                    throw new PostParseException(
                            "Unexpected terminal symbol in parse tree: " + node);
                } else {
                    System.err.println("Warning: ignoring unknown node " + node.type);
                    return null;
                }
        }
    }

    private static List<AstNode> postParseList(Node node) {
        List<Node> nodes = new ArrayList<>();
        Node temp = node;
        while (temp != null) {
            if (temp.children.containsKey("element")) {
                nodes.add((Node) temp.children.get("element"));
            }
            if (temp.children.containsKey("list")) {
                temp = (Node) temp.children.get("list");
            } else {
                temp = null;
            }
        }
        return nodes.stream().map(subNode -> {
            try {
                return postParse(subNode);
            } catch (PostParseException e) {
                e.printStackTrace();
                return null;
            }
        }).toList();
    }
}