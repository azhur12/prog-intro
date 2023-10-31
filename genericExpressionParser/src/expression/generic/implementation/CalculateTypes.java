package expression.generic.implementation;

public interface CalculateTypes<T extends Number> {
    T add(T a, T b);
    T subtract(T a, T b);
    T multiply(T a, T b);
    T divide(T a, T b);
    T negate(T a);

    T toType(Integer a);
    T stringToT(String s);
}
