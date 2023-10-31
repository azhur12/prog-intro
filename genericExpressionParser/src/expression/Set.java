package expression;


public class Set extends BinaryOperation {

    public Set(Operand leftOperand, Operand rightOperand) {
        super(leftOperand, rightOperand, "set" , true, false, 0);
    }

    @Override
    public int evaluate(int a, int b) {
        return a | (1 << b);
    }
}
