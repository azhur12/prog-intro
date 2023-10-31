package expression.exceptions;

public class UnexpectedCharacterException extends ParsingException {
    public UnexpectedCharacterException(String message) {
        super(message);
    }
}
