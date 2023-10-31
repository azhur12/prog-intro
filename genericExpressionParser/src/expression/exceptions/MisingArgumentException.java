package expression.exceptions;

public class MisingArgumentException extends ParsingException{
    public MisingArgumentException(String message) {
        super(message);
    }
}
