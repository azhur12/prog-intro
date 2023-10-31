package expression;

import java.util.Objects;

public class Variable implements Operand {
    protected String variable;
    protected int priority = 4;

    public Variable(String variable) {
        this.variable = variable;
    }

    @Override
    public int evaluate(int number) {
        return number;
    }


    public int evaluate(int x, int y, int z) {
        if (Objects.equals(variable, "x")) {
            return x;
        } else if (Objects.equals(variable, "y")) {
            return y;
        } else {
            return z;
        }
    }


    public String toString() {
        return variable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable1 = (Variable) o;
        return Objects.equals(variable, variable1.variable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variable);
    }


    public int getPriority() {
        return priority;
    }

    @Override
    public String toMiniString() {
        return variable;
    }
}
