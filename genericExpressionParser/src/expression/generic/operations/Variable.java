package expression.generic.operations;

import expression.generic.implementation.CalculateTypes;

import java.util.Objects;

public class Variable<T extends Number> implements Operand<T> {

    private String var;
    private CalculateTypes<T> mode;

    public Variable(String var, CalculateTypes<T> mode) {
        this.var = var;
        this.mode = mode;
    }

    @Override
    public T evaluate(T x, T y, T z) throws InvalidVariableException {
        if (Objects.equals(var, "x")) {
            return x;
        } else if (Objects.equals(var, "y")) {
            return y;
        } else if (Objects.equals(var, "z")) {
            return z;
        } else {
            throw new InvalidVariableException("You can use only x, y, z as variables");
        }
    }
}
