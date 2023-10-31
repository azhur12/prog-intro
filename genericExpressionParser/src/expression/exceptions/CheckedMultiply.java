package expression.exceptions;

import expression.Multiply;
import expression.Operand;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand);
    }

    public int evaluate(int a, int b) {
        if ((a < 0 && b > 0) || (a > 0 && b < 0)) {
            int edge = Integer.MIN_VALUE;
            if (a < 0) {
                if (edge/b > a) {
                    throw new OverflowException();
                }
            } else {
                if ((b != -1) && (edge/b < a)) {
                    throw new OverflowException();
                }
            }
        } else {
            int edge = Integer.MAX_VALUE;
            if (a > 0) {
                if (edge/a < b) {
                    throw new OverflowException();
                }
            } else if (a != 0) {
                if (((a == -1) && (b == Integer.MIN_VALUE)) || ((a == Integer.MIN_VALUE) && (b == -1))) {
                    throw new OverflowException();
                }
                if (edge/a > b) {
                    throw new OverflowException();
                }
            }
        }

        return super.evaluate(a, b);
    }
}
