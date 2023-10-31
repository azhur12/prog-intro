package expression.exceptions;

import expression.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isWhitespace;

public class ExpressionParser implements TripleParser {
    private List<Element> list;
    private int pos;

    public TripleExpression parse(String exp) throws ParsingException, EvaluatingException {
        this.list = makeList(exp);
        if (!checkingBrackets(list)) {
            throw new ParsingException("Incorrect balance of brackets");
        }
        this.pos = -1;
        return zeroPriorTerm(list);
    }

    private static List<Element> makeList(String expText) throws ParsingException, EvaluatingException {
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
                    if (expText.length() == 1) {
                        throw new UnexpectedElementException("Unexpected element - '-'");
                    }
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
                    elements.add(creatingElement("set", pos, ElementType.SET, expText));
                    pos+=3;
                    continue;
                case 'c':
                    if (expText.charAt(pos + 1) == 'l') {
                        elements.add(creatingElement("clear", pos, ElementType.CLEAR, expText));
                        pos += 5;
                        continue;
                    } else if (expText.charAt(pos + 1) == 'o') {
                        elements.add(creatingElement("count", pos, ElementType.COUNT, expText));
                        pos += 5;

                        if (isDigit(expText.charAt(pos)) || isVariable(expText.charAt(pos))) {
                            throw new UnexpectedCharacterException(expText.charAt(pos) + " " +
                                    "goes right after COUNT" + " " + "position: " + pos);
                        }
                        continue;
                    } else {
                        throw new UnexpectedCharacterException("Unexpected character: " +
                                expText.charAt(pos + 1) + " " + "position: " + (pos + 1));
                    }
                default:
                    if (isDigit(ch)) {
                        StringBuilder sb = new StringBuilder();
                        pos = makeNumber(expText, elements, pos, ch, sb);
                    } else {
                        if (!isWhitespace(ch)) {
                            throw new UnexpectedCharacterException("Unexpected character: " +
                                    ch + " " + "position: " + pos);
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

    private static int makeNumber(String expText, ArrayList<Element> lexemes, int pos, char c, StringBuilder sb) throws ParsingException {
        do {
            sb.append(c);
            pos++;
            if (pos >= expText.length()) {
                break;
            }
            c = expText.charAt(pos);
        } while (isDigit(c));
        if (c != 's' && c != 'c') {
            lexemes.add(new Element(ElementType.CONST, sb.toString()));
        } else {
            throw new ParsingException("Number and SET/CLEAR together");
        }
        return pos;
    }

    private Operand zeroPriorTerm(List<Element> list) throws ParsingException, EvaluatingException {
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
    private Operand firstPriorityTerm(List<Element> list) throws ParsingException, EvaluatingException {
        Operand operand = secondPriorTerm(list);
        while (true) {
            pos++;
            if (isIndexOut()) {
                return operand;
            }
            Element element = list.get(pos);
            switch (element.type) {
                case PLUS -> operand = new CheckedAdd(operand, secondPriorTerm(list));
                case MINUS -> operand = new CheckedSubtract(operand, secondPriorTerm(list));
                case SET, CLEAR, RIGHT_BRACKET -> {
                    pos--;
                    return operand;
                }
            }
        }
    }

    private Operand secondPriorTerm(List<Element> list) throws ParsingException, EvaluatingException {
        Operand operand = thirdPriorTerm(list);
        while (true) {
            pos++;
            if (isIndexOut()) {
                return operand;
            }
            Element element = list.get(pos);
            switch (element.type) {
                case MULTI -> operand = new CheckedMultiply(operand, thirdPriorTerm(list));
                case DIVIDE -> operand = new CheckedDivide(operand, thirdPriorTerm(list));
                case SET, CLEAR, PLUS, MINUS, RIGHT_BRACKET -> {
                    pos--;
                    return operand;
                } default -> throw new UnexpectedElementException("Unexpected element" + " position: " + pos);
            }
        }
    }

    private Operand thirdPriorTerm(List<Element> list) throws ParsingException, EvaluatingException {
        pos++;
        if (pos == list.size()) {
            throw new MisingArgumentException("Missing argument" + " position: " + pos);
        }
        Element element = list.get(pos);
        switch (element.type) {
            case CONST -> {
                return new Const(Integer.parseInt(element.value));
            }
            case COUNT -> {
                return new Count(thirdPriorTerm(list));
            }
            case UNARY_MINUS -> {
                return new CheckedNegate(thirdPriorTerm(list));
            }
            case VARIABLE -> {
                return new Variable(element.value);
            }
            case LEFT_BRACKET -> {
                Operand operand = zeroPriorTerm(list);
                pos++;
                return operand;
            }
            default -> throw new UnexpectedElementException("Unexpected element" + " " +
                    element.value + " position: " + pos);
        }
    }
    private static boolean isDigit(char ch) {
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

    private boolean checkingBrackets(List<Element> list) {
        int balance = 0;
        for (Element element : list) {
            if (balance < 0) {
                return false;
            }
            if (element.type == ElementType.LEFT_BRACKET) {
                balance++;
            } else if (element.type == ElementType.RIGHT_BRACKET) {
                balance--;
            }
        }
        return balance == 0;
    }

    private static boolean isVariable(char ch) {
        return ch == 'x' || ch == 'y' || ch == 'z';
    }


    private static Element creatingElement(String codeOfOperation, int position, ElementType type, String text) throws ParsingException {
        int tempIndex = position;
        for (int i = 0; i < codeOfOperation.length(); i++) {
            if (text.charAt(tempIndex) != codeOfOperation.charAt(i)) {
                throw new UnexpectedCharacterException("Unexpected character: " + text.charAt(tempIndex));
            }
            tempIndex++;
        }
        return new Element(type, codeOfOperation);
    }
}

