package expression.exceptions;

import expression.Divide;
import expression.Operand;

public class CheckedDivide extends Divide {
    public CheckedDivide(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand);
    }

    public int evaluate(int a, int b) {
        if ((b == 0) || ((b == -1) && (a == Integer.MIN_VALUE))) {
            throw new DivisionByZeroException();
        }
        return super.evaluate(a, b);
    }
}
