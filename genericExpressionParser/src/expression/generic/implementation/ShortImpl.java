package expression.generic.implementation;

import expression.exceptions.DivisionByZeroException;

public class ShortImpl implements CalculateTypes<Short>{
    @Override
    public Short add(Short a, Short b) {
        return (short) (a + b);
    }

    @Override
    public Short subtract(Short a, Short b) {
        return (short) (a - b);
    }

    @Override
    public Short multiply(Short a, Short b) {
        return (short) (a * b);
    }

    @Override
    public Short divide(Short a, Short b) {
        if (b == 0) {
            throw new DivisionByZeroException();
        }

        return (short) (a / b);
    }

    @Override
    public Short negate(Short a) {
        return (short) (-a);
    }

    @Override
    public Short toType(Integer a) {
        int g = a;
        return (short) g;
    }

    @Override
    public Short stringToT(String s) {
        return Short.parseShort(s);
    }
}
