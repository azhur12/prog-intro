package md2html;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Md2Html {
    public static void main(String[] args) {
        try {

            String InputFile = args[0];
            String OutputFile = args[1];

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(InputFile), StandardCharsets.UTF_8));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OutputFile), StandardCharsets.UTF_8));
            try {
                String line = reader.readLine();
                while (line != null) {
                    StringBuilder sb = new StringBuilder();
                    while (line.isEmpty()) {
                        line = reader.readLine();
                    }
                    while (line != null && !line.isEmpty()) {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                        line = reader.readLine();
                    }
                    if (sb.length() > 0) {
                        sb.setLength(sb.length() - System.lineSeparator().length());
                    }
                    Parser result = new Parser(sb.toString());
                    writer.write(result.get());
                    writer.newLine();
                }
            } finally {
                reader.close();
                writer.close();
            }
        } catch (FileNotFoundException e) {
            System.out.print("Can't find file: " + e.getMessage());
        } catch (IOException e) {
            System.out.print("IOException: " + e.getMessage());
        }
    }
}
