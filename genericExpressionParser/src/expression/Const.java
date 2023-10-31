package expression;

import java.util.Objects;

public class Const implements Operand {
    protected int value;
    protected int priority = 4;
    public Const(int value) {
        this.value = value;
    }

    @Override
    public int evaluate(int number) {
        return value;
    }


    public int evaluate(int x, int y, int z) {
        return value;
    }



    public String toString() {
        return value + "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Const aConst = (Const) o;
        return value == aConst.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public int getPriority() {
        return priority;
    }
    @Override
    public String toMiniString() {
        return value + "";
    }
}
