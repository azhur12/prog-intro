package expression;

public abstract class UnaryOperation implements Operand {
    protected Operand expression;
    protected int priority;


    public UnaryOperation(Operand expression, int priority) {
        this.expression = expression;
        this.priority = priority;
    }
    abstract public int innerEvaluate(int a);
    @Override
    public int evaluate(int x) {
        return innerEvaluate(expression.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return innerEvaluate(expression.evaluate(x, y, z));
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String toMiniString() {
        return null;
    }
}
