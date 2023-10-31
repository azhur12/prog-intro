package expression.exceptions;

import expression.Const;
import expression.Operand;

import java.math.BigInteger;


public class Main {
    public static void main(String[] args) throws ParsingException, OverflowException {
        ExpressionParser triple = new ExpressionParser();
        Operand result = new CheckedAdd(new Const(30), new Const(2));
        Integer value = add(30, 2);
        System.out.println();
    }

    public static Integer add(Integer a, Integer b) {
        Operand add = new CheckedAdd(new Const(a), new Const(b));
        return add.evaluate(0);
    }
}
