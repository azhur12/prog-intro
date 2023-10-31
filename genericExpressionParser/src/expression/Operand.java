package expression;

public interface Operand extends Expression , TripleExpression {
    String toString();
    boolean equals(Object o);
    int hashCode();
    int getPriority();
    String toMiniString();


}
