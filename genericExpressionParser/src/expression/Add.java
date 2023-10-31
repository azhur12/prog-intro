package expression;

public class Add extends BinaryOperation {

    public Add(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand, "+" , true, true, 1);
    }

    public int evaluate(int a, int b) {
        return a + b;
    }

}
