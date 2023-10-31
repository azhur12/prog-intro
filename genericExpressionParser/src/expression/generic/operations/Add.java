package expression.generic.operations;

import expression.generic.implementation.CalculateTypes;

public class Add<T extends Number> extends AbstractOperation<T> {

    public Add(Operand<T> leftOperand, Operand<T> rightOperand, CalculateTypes<T> mode) {
        super(leftOperand, rightOperand, mode);
    }

    @Override
    protected T innerEvaluate(T leftOperand, T rightOperand) {
        return mode.add(leftOperand, rightOperand);
    }
}
