package md2html;

import java.util.Map;
import java.util.Objects;

public class Parser {
    private final Map<String, String> marks = Map.of(
            "*", "em",
            "_", "em",
            "__", "strong",
            "**", "strong",
            "--", "s",
            "`", "code",
            "<", "&lt;",
            ">", "&gt;",
            "&", "&amp;",
            "++", "u");

    private Map<String, Integer> FixedCounterOfMarks = new java.util.HashMap<>(Map.of(
            "*", 0,
            "_", 0,
            "__", 0,
            "**", 0,
            "--", 0,
            "`", 0,
            "++", 0));

    private Map<String, Integer> CounterOfMarks = new java.util.HashMap<>(Map.of(
            "*", 0,
            "_", 0,
            "__", 0,
            "**", 0,
            "--", 0,
            "`", 0,
            "++", 0));
    private final String paragraph;
    private StringBuilder result;
    private int headerlevel;
    private int index = 0;


    public Parser(String paragraph) {
        this.paragraph = paragraph + "$";
        this.result = new StringBuilder();
        this.headerlevel = detectHeaderLevel(this.paragraph);
        parsingParagraph(this.headerlevel);
    }

    private int detectHeaderLevel(String paragraph) {
        int headerlevel = 0;
        int iter = 0;
        char symbol = paragraph.charAt(iter);
        while (symbol == '#') {
            headerlevel++;
            iter++;
            symbol = paragraph.charAt(iter);
        }
        if (symbol == ' ') {
            return headerlevel;
        } else {
            return 0;
        }
    }

    private void parsingParagraph(int headerlevel) {
        if (headerlevel > 0) {
            result.append("<h").append(headerlevel).append(">");
            index = headerlevel + 1;
        } else {
            result.append("<p>");
            index = headerlevel;
        }
        for (int i = 0; i + 1 < paragraph.length(); i++) {
            String symbol = Character.toString(paragraph.charAt(i));
            String nextsymbol = Character.toString(paragraph.charAt(i + 1));

            if (symbol.equals("\\")) {
                i++;
            } else if (FixedCounterOfMarks.containsKey(symbol + nextsymbol)) {
                FixedCounterOfMarks.put(symbol + nextsymbol, FixedCounterOfMarks.get(symbol + nextsymbol) + 1);
                i++;
            } else if (FixedCounterOfMarks.containsKey(symbol)) {
                FixedCounterOfMarks.put(symbol, FixedCounterOfMarks.get(symbol) + 1);
            }
        }

        while (index + 1 < paragraph.length()) {
            String symbol = Character.toString(paragraph.charAt(index));
            String nextsymbol = Character.toString(paragraph.charAt(index + 1));

            if (symbol.equals("\\")) {
                result.append(nextsymbol);
                index += 2;
            } else if (FixedCounterOfMarks.containsKey((symbol + nextsymbol))) { //Обработка двусимвольного выделителя
                converting(symbol + nextsymbol);
            } else if (FixedCounterOfMarks.containsKey(symbol)) { //Обаботка односимвольного выделителя, который может быть одиночным
                converting(symbol);
            } else if (marks.containsKey(symbol)) { //Обработка &, <, >
                result.append(marks.get(symbol));
                index++;
            } else {
                result.append(symbol);
                index++;
            }
            if (index + 1 == paragraph.length()) {
                if (headerlevel > 0) {
                    result.append("</h").append(headerlevel).append(">");
                } else {
                    result.append("</p>");
                }
            }
        }
    }

    public String get() {
        return result.toString();
    }

    private void converting(String element) {
        int lengthOfElement = element.length();
        CounterOfMarks.put(element, CounterOfMarks.get(element) + 1);
        if (CounterOfMarks.get(element) % 2 == 1) {
            if (Objects.equals(FixedCounterOfMarks.get(element), CounterOfMarks.get(element))) {
                result.append(element);
                index += lengthOfElement;
            } else {
                result.append("<").append(marks.get(element)).append(">");
                index += lengthOfElement;
            }
        } else {
            result.append("</").append(marks.get(element)).append(">");
            index += lengthOfElement;
        }
    }
}
