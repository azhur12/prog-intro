package expression.generic.operations;

import expression.generic.implementation.CalculateTypes;

public class Subtract<T extends Number> extends AbstractOperation<T> {

    public Subtract(Operand<T> leftOperand, Operand<T> rightOperand, CalculateTypes<T> mode) {
        super(leftOperand, rightOperand, mode);
    }

    @Override
    protected T innerEvaluate(T leftOperand, T rightOperand) {
        return mode.subtract(leftOperand, rightOperand);
    }
}
