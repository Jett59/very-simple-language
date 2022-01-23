package app.cleancode.vsl.postParse;

import java.util.ArrayList;
import java.util.List;
import app.cleancode.parser.Parser.Node;
import app.cleancode.vsl.ast.AstNode;
import app.cleancode.vsl.ast.MacroDefinitionNode;
import app.cleancode.vsl.ast.MacroInvocationNode;
import app.cleancode.vsl.ast.ProgramNode;
import app.cleancode.vsl.ast.RuleAttribute;
import app.cleancode.vsl.ast.RuleNode;
import app.cleancode.vsl.ast.Symbol;
import app.cleancode.vsl.ast.ValueType;

public class PostParser {
    public static AstNode postParse(Node node) throws PostParseException {
        switch (node.type()) {
            case root: {
                return new ProgramNode(postParseList((Node) node.children().get("elements")));
            }
            case PROGRAM_ELEMENT: {
                return postParse((Node) node.children().get("element"));
            }
            case MACRO_DEFINITION: {
                return new MacroDefinitionNode((String) node.children().get("name").value(),
                        postParseList((Node) node.children().get("parameters")).stream()
                                .map(subNode -> (Symbol) subNode).map(symbol -> symbol.identifier())
                                .toList(),
                        postParseList((Node) node.children().get("body")));
            }
            case RULE: {
                return new RuleNode((String) node.children().get("symbol").value(),
                        postParseList((Node) node.children().get("components")).stream()
                                .map(subNode -> (Symbol) subNode).toList(),
                        !node.children().containsKey("attributes") ? List.of()
                                : postParseList((Node) node.children().get("attributes")).stream()
                                        .map(subNode -> (RuleAttribute) subNode).toList());
            }
            case RULE_ATTRIBUTE: {
                if (node.children().containsKey("childNumber")) {
                    return new RuleAttribute(
                            ((Number) node.children().get("childNumber").value()).intValue(),
                            (String) node.children().get("childName").value(), null);
                } else {
                    return new RuleAttribute(0, null, ValueType.valueOf(
                            ((String) node.children().get("valueType").value()).toUpperCase()));
                }
            }
            case SYMBOL: {
                if (node.children().containsKey("symbol")) {
                    return new Symbol((String) node.children().get("symbol").value(), null);
                } else {
                    return new Symbol(null, (String) node.children().get("String").value());
                }
            }
            case MACRO_INVOCATION: {
                return new MacroInvocationNode((String) node.children().get("name").value(),
                        postParseList((Node) node.children().get("arguments")).stream()
                                .map(subNode -> (Symbol) subNode).toList());
            }
            default:
                if (node.type().terminal) {
                    throw new PostParseException(
                            "Unexpected terminal symbol in parse tree: " + node);
                } else {
                    System.err.println("Warning: ignoring unknown node " + node.type());
                    return null;
                }
        }
    }

    private static List<AstNode> postParseList(Node node) {
        List<Node> nodes = new ArrayList<>();
        Node temp = node;
        while (temp != null) {
            if (temp.children().containsKey("element")) {
                nodes.add((Node) temp.children().get("element"));
            }
            if (temp.children().containsKey("list")) {
                temp = (Node) temp.children().get("list");
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
