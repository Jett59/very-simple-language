package app.cleancode.vsl.ast;

import java.util.List;
import app.cleancode.parser.NodeType;

public class MacroDefinitionNode implements AstNode {
    public final String name;
    public final List<String> parameters;
    public final List<AstNode> statements;

    public MacroDefinitionNode(String name, List<String> parameters, List<AstNode> statements) {
        this.name = name;
        this.parameters = List.copyOf(parameters);
        this.statements = List.copyOf(statements);
    }

    @Override
    public NodeType getType() {
        return NodeType.MACRO_DEFINITION;
    }

    @Override
    public String toString() {
        return String.format("MacroDefinition of '%s' taking %s {\n%s\n}", name, parameters,
                String.join(",\n", statements.stream().map(AstNode::toString).toList()));
    }

}
