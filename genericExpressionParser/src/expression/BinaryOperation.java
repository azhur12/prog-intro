package expression;

import expression.exceptions.OverflowException;

import java.util.Objects;

public abstract class BinaryOperation implements Operand {
    protected Operand leftOperand;
    protected Operand rightOperand;
    protected String operator;
    protected boolean rightAssoc;
    protected boolean assoc;
    protected int priority;

    public BinaryOperation(Operand leftOperand, Operand rightOperand, String operator, boolean rightAssoc, boolean assoc, int priority) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operator = operator;
        this.rightAssoc = rightAssoc;
        this.assoc = assoc;
        this.priority = priority;
    }

    public String toString() {
        return "(" + leftOperand.toString() + " " + operator + " " + rightOperand.toString() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryOperation that = (BinaryOperation) o;
        return (this.leftOperand.equals(that.leftOperand) &&
                this.rightOperand.equals(that.rightOperand) &&
                this.operator.equals(that.operator));
    }

    @Override
    public int hashCode() {
        return Objects.hash(leftOperand,rightOperand,operator);
    }

    @Override
    public String toMiniString() {
        boolean isLeftBraceFree = isLeftBraceFree(leftOperand);
        boolean isRightBraceFree = isRightBraceFree(rightOperand);
        String result;
        if (isLeftBraceFree) {
            result = leftOperand.toMiniString() + " ";
        } else {
            result = "(" + leftOperand.toMiniString() + ")" + " ";
        }

        if (isRightBraceFree) {
            result = result + operator + " " + rightOperand.toMiniString();
        } else {
            result = result + operator + " " + "(" + rightOperand.toMiniString() + ")";
        }

        return result;
    }

    public int getPriority() {
        return priority;
    }
    abstract public int evaluate(int a, int b) throws OverflowException;

    public int evaluate(int x) {
        return evaluate(leftOperand.evaluate(x),rightOperand.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return evaluate(leftOperand.evaluate(x,y,z),rightOperand.evaluate(x,y,z));
    }



    private boolean isLeftBraceFree(Operand leftOperand) {
        return leftOperand.getPriority() >= this.priority;
    }

    private boolean isRightBraceFree(Operand rightOperand) {
        if (this.priority < rightOperand.getPriority()) {
            return true;
        } else if (this.priority == rightOperand.getPriority()){
            if (this.assoc) {
                return ((BinaryOperation) rightOperand).rightAssoc;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
