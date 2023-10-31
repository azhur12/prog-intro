package expression.generic.operations;

import expression.exceptions.ParsingException;

public class InvalidVariableException extends ParsingException {
    public InvalidVariableException(String message) {
        super(message);
    }
}
