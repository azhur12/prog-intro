package expression.generic.implementation;

public class FloatImpl implements CalculateTypes<Float>{
    @Override
    public Float add(Float a, Float b) {
        return a + b;
    }

    @Override
    public Float subtract(Float a, Float b) {
        return a - b;
    }

    @Override
    public Float multiply(Float a, Float b) {
        return a * b;
    }

    @Override
    public Float divide(Float a, Float b) {
        return a / b;
    }

    @Override
    public Float negate(Float a) {
        return -a;
    }

    @Override
    public Float toType(Integer a) {
        return (float) a;
    }

    @Override
    public Float stringToT(String s) {
        return Float.parseFloat(s);
    }
}
