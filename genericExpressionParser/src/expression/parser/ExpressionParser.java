package expression.parser;

import expression.*;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser implements TripleParser {
    private List<Element> list;
    private int pos;
    private String exp;

    public TripleExpression parse(String exp) {
        this.list = makeList(exp);
        this.pos = -1;
        this.exp = exp;
        return zeroPriorTerm(list);
    }

    public static List<Element> makeList(String expText) {
        ArrayList<Element> elements = new ArrayList<>();
        int pos = 0;
        while (pos < expText.length()) {
            char ch = expText.charAt(pos);
            switch (ch) {
                case '(':
                    elements.add(new Element(ElementType.LEFT_BRACKET, ch));
                    pos++;
                    continue;
                case ')':
                    elements.add(new Element(ElementType.RIGHT_BRACKET, ch));
                    pos++;
                    continue;
                case '+':
                    elements.add(new Element(ElementType.PLUS, ch));
                    pos++;
                    continue;
                case '-':
                    int lastIndex = elements.size() - 1;
                    if (isDigit(expText.charAt(pos + 1)) && ((lastIndex == -1) || (elements.get(lastIndex).type != ElementType.CONST &&
                            elements.get(lastIndex).type != ElementType.VARIABLE &&
                            elements.get(lastIndex).type != ElementType.RIGHT_BRACKET))) {
                        pos++;
                        StringBuilder sb = new StringBuilder("-");
                        ch = expText.charAt(pos);
                        pos = makeNumber(expText, elements, pos, ch, sb);
                        pos--;
                    } else {
                        elements.add(new Element(ElementType.MINUS, ch));
                    }
                    pos++;
                    continue;
                case '*':
                    elements.add(new Element(ElementType.MULTI, ch));
                    pos++;
                    continue;
                case '/':
                    elements.add(new Element(ElementType.DIVIDE, ch));
                    pos++;
                    continue;
                case 'x' , 'y', 'z':
                    elements.add(new Element(ElementType.VARIABLE, ch));
                    pos++;
                    continue;
                case 's':
                    int tempIndex1 = pos;
                    for (int i = 0; i < 3; i++) {
                        if (expText.charAt(tempIndex1) != "set".charAt(i)) {
                            throw new RuntimeException("Unexpected character: " + ch);
                        }
                        tempIndex1++;
                    }
                    elements.add(new Element(ElementType.SET, "set"));
                    pos+=3;
                    continue;
                case 'c':
                    if (expText.charAt(pos + 1) == 'l') {
                        int tempIndex2 = pos;
                        for (int i = 0; i < 5; i++) {
                            if (expText.charAt(tempIndex2) != "clear".charAt(i)) {
                                throw new RuntimeException("Unexpected character: " + ch);
                            }
                            tempIndex2++;
                        }
                        elements.add(new Element(ElementType.CLEAR, "clear"));
                        pos += 5;
                        continue;
                    } else if (expText.charAt(pos + 1) == 'o') {
                        int tempIndex2 = pos;
                        for (int i = 0; i < 5; i++) {
                            if (expText.charAt(tempIndex2) != "count".charAt(i)) {
                                throw new RuntimeException("Unexpected character: " + expText.charAt(tempIndex2));
                            }
                            tempIndex2++;
                        }
                        elements.add(new Element(ElementType.COUNT, "count"));
                        pos += 5;
                        continue;
                    } else {
                        throw new RuntimeException("Unexpected character: " + expText.charAt(pos + 1));
                    }
                default:
                    if (isDigit(ch)) {
                        StringBuilder sb = new StringBuilder();
                        pos = makeNumber(expText, elements, pos, ch, sb);
                    } else {
                        if (!Character.isWhitespace(ch)) {
                            throw new RuntimeException("Unexpected character: " + ch);
                        }
                        pos++;
                    }
            }
        }

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).type == ElementType.MINUS) {
                if (i == 0 || (elements.get(i - 1).type != ElementType.CONST &&
                        elements.get(i - 1).type != ElementType.RIGHT_BRACKET &&
                        elements.get(i - 1).type != ElementType.VARIABLE)) {
                    elements.get(i).setType(ElementType.UNARY_MINUS);
                }
            }
        }

        return elements;
    }

    private static int makeNumber(String expText, ArrayList<Element> lexemes, int pos, char c, StringBuilder sb) {
        do {
            sb.append(c);
            pos++;
            if (pos >= expText.length()) {
                break;
            }
            c = expText.charAt(pos);
        } while (isDigit(c));
        lexemes.add(new Element(ElementType.CONST, sb.toString()));
        return pos;
    }

    private Operand zeroPriorTerm(List<Element> list) {
        Operand operand = firstPriorityTerm(list);
        while (true) {
            pos++;
            if (isIndexOut()) {
                return operand;
            }
            Element element = list.get(pos);
            switch (element.type) {
                case SET -> operand = new Set(operand, firstPriorityTerm(list));
                case CLEAR -> operand = new Clear(operand, firstPriorityTerm(list));
                case RIGHT_BRACKET -> {
                    pos--;
                    return operand;
                }
            }
        }
    }
    private Operand firstPriorityTerm(List<Element> list) {
        Operand operand = secondPriorTerm(list);
        while (true) {
            pos++;
            if (isIndexOut()) {
                return operand;
            }
            Element element = list.get(pos);
            switch (element.type) {
                case PLUS -> operand = new Add(operand, secondPriorTerm(list));
                case MINUS -> operand = new Subtract(operand, secondPriorTerm(list));
                case SET, CLEAR, RIGHT_BRACKET -> {
                    pos--;
                    return operand;
                }
            }
        }
    }

    private Operand secondPriorTerm(List<Element> list) {
        Operand operand = thirdPriorTerm(list);
        while (true) {
            pos++;
            if (isIndexOut()) {
                return operand;
            }
            Element element = list.get(pos);
            switch (element.type) {
                case MULTI -> operand = new Multiply(operand, thirdPriorTerm(list));
                case DIVIDE -> operand = new Divide(operand, thirdPriorTerm(list));
                case SET, CLEAR, PLUS, MINUS, RIGHT_BRACKET -> {
                    pos--;
                    return operand;
                }
            }
        }
    }

    private Operand thirdPriorTerm(List<Element> list) {
        pos++;
        Element element = list.get(pos);
        switch (element.type) {
            case CONST -> {
                return new Const(Integer.parseInt(element.value));
            }
            case COUNT -> {
                return new Count(thirdPriorTerm(list));
            }
            case UNARY_MINUS -> {
                return new Negate(thirdPriorTerm(list));
            }
            case VARIABLE -> {
                return new Variable(element.value);
            }
            case LEFT_BRACKET -> {
                Operand operand = zeroPriorTerm(list);
                pos++;
                return operand;
            }
            default -> throw new RuntimeException("Unexpected element" + element.value + '\n' + exp);
        }
    }
    public static boolean isDigit(char ch) {
        return ch >= '0' &&
                ch <= '9';
    }

    private boolean isIndexOut(){
        if (pos >= list.size())  {
            pos = list.size() - 1;
            return true;
        } else {
            return false;
        }
    }
}

