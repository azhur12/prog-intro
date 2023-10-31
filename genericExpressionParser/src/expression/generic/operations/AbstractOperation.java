package expression.generic.operations;

import expression.generic.implementation.CalculateTypes;

public abstract class AbstractOperation<T extends Number> implements Operand<T>{
    private Operand <T> leftOperand;
    private Operand <T> rightOperand;
    protected CalculateTypes<T> mode;

    public AbstractOperation(Operand<T> leftOperand, Operand<T> rightOperand, CalculateTypes<T> mode) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.mode = mode;
    }

    protected abstract T innerEvaluate(T leftOperand, T rightOperand);

    @Override
    public T evaluate(T x, T y, T z) throws InvalidVariableException {
        return innerEvaluate(leftOperand.evaluate(x, y, z), rightOperand.evaluate(x, y, z));
    }
}
