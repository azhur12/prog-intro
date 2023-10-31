package expression;

public class Divide extends BinaryOperation {

    public Divide(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand, "/", false, false, 2);
    }

    public int evaluate(int a, int b) {
        return a / b;
    }
}
