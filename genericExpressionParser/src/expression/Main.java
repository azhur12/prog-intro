package expression;

import expression.parser.ExpressionParser;

public class Main {
    public static void main(String[] args) {
        ExpressionParser triple = new ExpressionParser();
        Operand result = (Operand) triple.parse("1 + 2 * (10 - x)");
        System.out.println(result.evaluate(5));
    }
}
