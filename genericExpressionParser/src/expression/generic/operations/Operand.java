package expression.generic.operations;

public interface Operand<T extends Number> {
    T evaluate(T x, T y, T z) throws InvalidVariableException;
}
