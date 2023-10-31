package expression.generic.implementation;

import expression.exceptions.DivisionByZeroException;

import java.math.BigInteger;
import java.util.Objects;

public class BigIntegerImpl implements CalculateTypes<BigInteger> {
    public BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    public BigInteger subtract(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    public BigInteger multiply(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    public BigInteger divide(BigInteger a, BigInteger b) {
        if (Objects.equals(b, BigInteger.valueOf(0))) {
            throw new DivisionByZeroException();
        }
        return a.divide(b);
    }

    public BigInteger negate(BigInteger a) {
        return a.multiply(BigInteger.valueOf(-1));
    }

    @Override
    public BigInteger toType(Integer a) {
        return new BigInteger(String.valueOf(a));
    }

    @Override
    public BigInteger stringToT(String s) {
        return new BigInteger(s);
    }
}
