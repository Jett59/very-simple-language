package app.cleancode.parser;

public class TokenDescription {
    public static enum ValueType {
        NONE, STRING, NUMBER
    }

    private ValueType valueType = ValueType.NONE;
    private NodeType nodeType;
    private String pattern;

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return String.format("TokenDescription [\nvalueType=%s,\nnodeType=%s,\npattern=\"%s\"]",
                valueType, nodeType, pattern);
    }
}
