package app.cleancode.vsl.ast;

import java.util.List;
import app.cleancode.parser.NodeType;

public class MacroInvocationNode implements AstNode {
    public final String name;
    public final List<Symbol> arguments;

    public MacroInvocationNode(String name, List<Symbol> arguments) {
        this.name = name;
        this.arguments = List.copyOf(arguments);
    }

    @Override
    public NodeType getType() {
        return NodeType.MACRO_INVOCATION;
    }

    @Override
    public String toString() {
        return String.format("MacroInvocation of '%s' passing %s", name, arguments);
    }

}
