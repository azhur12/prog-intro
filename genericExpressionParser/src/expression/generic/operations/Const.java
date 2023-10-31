package expression.generic.operations;

public class Const<T extends Number> implements Operand<T> {
    private T value;

    public Const(T value) {
        this.value = value;
    }
    @Override
    public T evaluate(T x, T y, T z) {
        return value;
    }
}
