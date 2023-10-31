package expression;

public class Count extends UnaryOperation {

    public Count(Operand expression) {
        super(expression, 3);
    }

    @Override
    public int innerEvaluate(int a) {
        String binaryNum = Integer.toBinaryString(a);
        int counter = 0;
        for (int i = 0; i < binaryNum.length(); i++) {
            if (binaryNum.charAt(i) == '1') {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public String toString() {
        return "count" + "(" + expression.toString() + ")";
    }

    @Override
    public String toMiniString() {
        if (expression.getPriority() >= 3) {
            return "count" + " " + super.expression.toMiniString();
        } else {
            return "count" + "(" + super.expression.toMiniString() + ")";
        }
    }
}
