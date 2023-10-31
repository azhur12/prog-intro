package expression.exceptions;

import expression.Negate;
import expression.Operand;

public class CheckedNegate extends Negate {
    public CheckedNegate(Operand expression) {
        super(expression);
    }

    public int innerEvaluate(int a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
        return super.innerEvaluate(a);
    }
}
