package expression.exceptions;

public class UnexpectedElementException extends ParsingException{
    public UnexpectedElementException(String message) {
        super(message);
    }
}
