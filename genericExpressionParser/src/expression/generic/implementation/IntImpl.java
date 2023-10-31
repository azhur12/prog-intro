package expression.generic.implementation;

import expression.*;
import expression.exceptions.*;

import java.awt.*;

public class IntImpl implements CalculateTypes<Integer>{

    private final boolean needToCheck;

    public IntImpl(boolean needToCheck) {
        this.needToCheck = needToCheck;
    }
    public Integer add(Integer a, Integer b) {
        Operand add;
        if (needToCheck) {
            add = new CheckedAdd(new Const(a), new Const(b));
        } else {
            add = new Add(new Const(a), new Const(b));
        }
        return add.evaluate(0);
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        Operand subtract;
        if (needToCheck) {
            subtract = new CheckedSubtract(new Const(a) , new Const(b));
        } else {
            subtract = new Subtract(new Const(a) , new Const(b));
        }
        return subtract.evaluate(0);
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        Operand multiply;
        if (needToCheck) {
            multiply = new CheckedMultiply(new Const(a) , new Const(b));
        } else {
            multiply = new Multiply(new Const(a) , new Const(b));
        }
        return multiply.evaluate(0);
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        Operand divide;
        if (needToCheck) {
            divide = new CheckedDivide(new Const(a) , new Const(b));
        } else {
            if (b == 0) {
                throw new DivisionByZeroException();
            }
            divide = new Divide(new Const(a) , new Const(b));
        }
        return divide.evaluate(0);
    }

    @Override
    public Integer negate(Integer a) {
        Operand negate;
        if (needToCheck) {
            negate = new CheckedNegate(new Const(a));
        } else {
            negate = new Negate(new Const(a));
        }
        return negate.evaluate(0);
    }

    @Override
    public Integer toType(Integer a) {
        return a;
    }

    @Override
    public Integer stringToT(String s) {
        return Integer.parseInt(s);
    }


}
