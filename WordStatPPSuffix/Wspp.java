import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;

public class Wspp {
    public static void main(String[] args) {
        String inputFileName = args[0];
        String outputFileName = args[1];
        LinkedHashMap<String, ArrayList<Integer>> words = new LinkedHashMap<>();
        int position = 0;
        ArrayList<Integer> list = new ArrayList<>();
        try {
            MyScanner reader = new MyScanner(new InputStreamReader(new FileInputStream(inputFileName), StandardCharsets.UTF_8));
            try {
                String line = reader.nextLine();
                while (line != null) {
                    line = line + " ";
                    String readingWord;
                    boolean findingStartOfWord = true;
                    int pointer = 0;
                    for (int i = 0; i < line.length(); i++) {
                        char symbol = line.charAt(i);
                        boolean isStringsSymbol = Character.isLetter(symbol) || Character.getType(symbol) == Character.DASH_PUNCTUATION || symbol == '\'';
                        if (findingStartOfWord) {
                            if (isStringsSymbol) {
                                pointer = i;
                                findingStartOfWord = false;

                            }
                        } else {
                            if (!(isStringsSymbol)) {
                                readingWord = line.substring(pointer, i).toLowerCase();
                                position += 1;
                                list = words.getOrDefault(readingWord, new ArrayList<>());
                                list.add(position);
                                words.put(readingWord, list);
                                findingStartOfWord = true;
                            }
                        }
                    }
                    line = reader.nextLine();
                }

            } finally {
                reader.close();
            }
        } catch (IOException | InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        try {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), StandardCharsets.UTF_8))) {
                for (String word : words.keySet()) {
                    int len = words.get(word).size();

                    writer.write(word + " " + len + " ");
                    ArrayList<Integer> temporaryList = words.get(word);
                    for (int i = 0; i < len; i++) {
                        writer.write(temporaryList.get(i).toString());
                        if ((i + 1) != len) {
                            writer.write(" ");
                        }
                    }
                    writer.write("\n");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}