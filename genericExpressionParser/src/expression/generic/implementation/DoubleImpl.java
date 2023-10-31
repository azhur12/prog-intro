package expression.generic.implementation;

import expression.exceptions.DivisionByZeroException;

public class DoubleImpl implements CalculateTypes<Double>{

    @Override
    public Double add(Double a, Double b) {
        return a + b;
    }

    @Override
    public Double subtract(Double a, Double b) {
        return a - b;
    }

    @Override
    public Double multiply(Double a, Double b) {
        return a * b;
    }

    @Override
    public Double divide(Double a, Double b) {
        return a / b;
    }

    @Override
    public Double negate(Double a) {
        return -a;
    }

    @Override
    public Double toType(Integer a) {
        return a.doubleValue();
    }

    @Override
    public Double stringToT(String s) {
        return Double.parseDouble(s);
    }
}
