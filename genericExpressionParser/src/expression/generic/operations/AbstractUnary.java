package expression.generic.operations;

import expression.generic.implementation.CalculateTypes;

public abstract class AbstractUnary<T extends Number> implements Operand<T>{

    private Operand <T> operand;
    protected CalculateTypes <T> mode;

    public AbstractUnary(Operand<T> operand, CalculateTypes<T> mode) {
        this.operand = operand;
        this.mode = mode;
    }

    public abstract T innerEvaluate(T a);

    @Override
    public T evaluate(T x, T y, T z) throws InvalidVariableException {
        return innerEvaluate(operand.evaluate(x, y, z));
    }
}
