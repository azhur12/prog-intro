import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WsppCountPosition {
    public static void main(String[] args) {
        String inputFileName = args[0];
        String outputFileName = args[1];
        LinkedHashMap<String, ArrayList<String>> words = new LinkedHashMap<>();
        int numLine = 0;
        ArrayList<String> list = new ArrayList<>();
        try {
            MyScanner reader = new MyScanner(new InputStreamReader(new FileInputStream(inputFileName), StandardCharsets.UTF_8));
            try {
                String line = reader.nextLine();
                numLine++;
                int position = 0;
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
                                list.add(numLine + ":" + position);
                                words.put(readingWord, list);
                                findingStartOfWord = true;
                            }
                        }
                    }
                    line = reader.nextLine();
                    numLine++;
                    position = 0;
                }

            } finally {/////
                reader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found" + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Wrong expected type" + e.getMessage());
        }

        List<Map.Entry<String, ArrayList<String>>> entries = new ArrayList<>(words.entrySet());
        entries.sort(Comparator.comparingInt(a -> a.getValue().size()));
        try {
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName), StandardCharsets.UTF_8))) {
                for (Map.Entry<String, ArrayList<String>> pair : entries) {
                    int len = pair.getValue().size();

                    writer.write(pair.getKey() + " " + len + " ");
                    ArrayList<String> temporaryList = pair.getValue();
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
            System.out.println("Problems with File" + e.getMessage());
        }

    }
}