package expression;

public class Multiply extends BinaryOperation {

    public Multiply(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand, "*", true, true, 2);
    }

    public int evaluate(int a, int b) {
        return a * b;
    }
}
