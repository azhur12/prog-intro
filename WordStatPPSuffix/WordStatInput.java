import java.io.*;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;

public class WordStatInput {
    public static void main(String[] args) {
        String inputFileName = args[0];
        String outputFileName = args[1];
        LinkedHashMap<String, Integer> words = new LinkedHashMap<String, Integer>();

        try {
            MyScanner reader = new MyScanner(new InputStreamReader(new FileInputStream(inputFileName), "utf-8"));
            try {
                String line = reader.nextLine();
                while (line != null) {
                    line = line + " ";
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
                            if (!(Character.isLetter(symbol) || Character.getType(symbol) == Character.DASH_PUNCTUATION || symbol == '\'')) {
                                readingWord = line.substring(pointer, i).toLowerCase();
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

            } finally {
                reader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println(e.getMessage());
        }
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), "utf8"));
            try {
                for (String word : words.keySet()) {
                    writer.write(word + " " + words.get(word) + "\n");
                }
            } finally {
                writer.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}