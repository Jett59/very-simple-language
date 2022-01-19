package app.cleancode.parser;

public class ParseException extends Exception {
    // Serial version thing
    private static final long serialVersionUID = -937611440812189052L;

    public final int line;

    public ParseException(int line, String message) {
        super(message);
        this.line = line;
    }

}
