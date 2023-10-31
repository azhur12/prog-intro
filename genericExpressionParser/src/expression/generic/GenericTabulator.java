package expression.generic;

import expression.exceptions.EvaluatingException;
import expression.exceptions.ParsingException;
import expression.generic.implementation.*;
import expression.generic.operations.Operand;

import java.util.Map;

public class GenericTabulator  implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode,
                                 String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        return tabulating(types.get(mode), expression, x1, x2, y1, y2, z1, z2);

    }

    private final Map<String, CalculateTypes<? extends Number>> types = Map.of(
            "i", new IntImpl(true),
            "d", new DoubleImpl(),
            "bi", new BigIntegerImpl(),
            "u", new IntImpl(false),
            "f", new FloatImpl(),
            "s", new ShortImpl()
    );

    private <T extends Number> Object[][][] tabulating(CalculateTypes<T> mode, String expression, int x1, int x2,
                                                       int y1, int y2, int z1, int z2) throws ParsingException {
        Object[][][] result = new Object[x2-x1 + 1][y2-y1 + 1][z2-z1 + 1];
        ExpressionParser<T> parser = new ExpressionParser<>();
        Operand<T> expr = parser.parse(expression, mode);
        for (int i = 0; i <= x2 - x1; i++) {
            for (int j = 0; j <= y2 - y1; j++) {
                for (int k = 0; k <= z2 - z1; k++) {
                    try {
                        result[i][j][k] = expr.evaluate(mode.toType(x1 + i), mode.toType(y1 + j), mode.toType(z1 + k));
                    } catch (EvaluatingException e) {
                        result[i][j][k] = null;
                    }
                }
            }
        }
        return result;

    }
}
