package expression;

public class Negate extends UnaryOperation{

    public Negate (Operand expression) {
        super(expression, 3);
    }

    @Override
    public int innerEvaluate(int a) {
        return -1 * a;
    }

    @Override
    public String toMiniString() {
        if (expression.getPriority() >= 3) {
            return "-" + " " + super.expression.toMiniString();
        } else {
            return "-" + "(" + super.expression.toMiniString() + ")";
        }
    }
    @Override
    public String toString() {
        return "-" + "(" + super.expression.toString() + ")";
    }
}
