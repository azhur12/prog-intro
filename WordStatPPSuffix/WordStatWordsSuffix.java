import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.TreeMap;

public class WordStatWordsSuffix {
    public static void main(String[] args) {
        String inputFileName = args[0];
        String outputFileName = args[1];
        TreeMap<String, Integer> words = new TreeMap<String, Integer>();

        try (MyScanner reader = new MyScanner(new InputStreamReader(new FileInputStream(inputFileName), StandardCharsets.UTF_8))) {
            String line = reader.nextLine();
            while (line != null) {
                line = line + " "; // :NOTE: костыль
                String readingWord;
                boolean flag = true;
                int pointer = 0;
                for (int i = 0; i < line.length(); i++) {
                    char symbol = line.charAt(i);
                    if (flag) {
                        if (Character.isLetter(symbol) || Character.getType(symbol) == Character.DASH_PUNCTUATION || symbol == '\'') {
                            pointer = i;
                            flag = false;
                        }
                    } else {
                        if (!(Character.isLetter(symbol) || Character.getType(symbol) == Character.DASH_PUNCTUATION ||
                                symbol == '\'')) { // :NOTE: copy paste
                            readingWord = line.substring(pointer, i).toLowerCase();
                            if (readingWord.length() > 3) {
                                readingWord = readingWord.substring(readingWord.length() - 3);
                            }
                            if (words.containsKey(readingWord)) {
                                words.put(readingWord, words.get(readingWord) + 1);
                            } else {
                                words.put(readingWord, 1);
                            }
                            flag = true;
                        }
                    }
                }
                line = reader.nextLine();
            }

        } catch (IOException | InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), StandardCharsets.UTF_8))) {
            for (String word : words.keySet()) {
                writer.write(word + " " + words.get(word) + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}