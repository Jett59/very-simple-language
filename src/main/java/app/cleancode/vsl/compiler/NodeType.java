package app.cleancode.vsl.compiler;

public record NodeType(String name, boolean terminal) {

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof NodeType) {
            if (((NodeType) other).name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return String.format("%s(%b)", name, terminal);
    }

}
