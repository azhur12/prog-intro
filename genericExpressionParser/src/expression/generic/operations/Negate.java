package expression.generic.operations;

import expression.generic.implementation.CalculateTypes;

public class Negate<T extends Number> extends AbstractUnary<T> {

    public Negate(Operand<T> operand, CalculateTypes<T> mode) {
        super(operand, mode);
    }

    @Override
    public T innerEvaluate(T a) {
        return mode.negate(a);
    }
}
