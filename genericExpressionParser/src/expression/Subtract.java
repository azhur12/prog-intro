package expression;

public class Subtract extends BinaryOperation {

    public Subtract(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand, "-", true, false, 1);
    }

    public int evaluate(int a, int b) {
        return a - b;
    }
}
