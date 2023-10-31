package expression;


public class Clear extends BinaryOperation {

    public Clear(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand, "clear", true, false, 0);
    }

    @Override
    public int evaluate(int a, int b) {
        return a & ~(1 << b);
    }
}
