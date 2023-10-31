import java.io.IOException;
import java.io.InputStreamReader;

public class MyScanner implements AutoCloseable {
    private final InputStreamReader reader;
    private final char[] buffer;
    private final int bufferLen = 8192;
    private int countOfChars;
    private int currentIndex;
    private String cache;
    private boolean doubleLineSeparator;

    @Override
    public void close() {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    MyScanner(InputStreamReader reader) {
        this.reader = reader;
        this.buffer = new char[this.bufferLen];
        this.currentIndex = 0;
        this.countOfChars = 0;
    }

    private void updateBuffer() {
        this.currentIndex = 0;
        try {
            this.countOfChars = this.reader.read(buffer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean hasNextLine() {
        cache = nextLine();
        return cache != null;
    }

    public String nextLine() {
        if (cache != null) {
            String str = cache;
            cache = null;
            return str;
        }

        StringBuilder inputString = new StringBuilder();
        while (true) {
            if (currentIndex >= countOfChars) {
                this.updateBuffer();
            }
            if (countOfChars == -1) {
                return inputString.isEmpty() ? null : inputString.toString();
            }

            if (doubleLineSeparator && currentIndex < countOfChars && buffer[currentIndex] == '\n') {
                currentIndex += 1;
                doubleLineSeparator = false;
            }

            int startLine = -1;
            while (currentIndex < countOfChars) {
                if (startLine == -1) {
                    startLine = currentIndex;
                }

                if (buffer[currentIndex] == '\n' || buffer[currentIndex] == '\r') {
                    inputString.append(buffer, startLine, currentIndex - startLine);
                    doubleLineSeparator = buffer[currentIndex++] == '\r';
                    return inputString.toString();
                }
                currentIndex++;
            }

            if (startLine != -1) {
                inputString.append(buffer, startLine, currentIndex - startLine);
            }
        }
    }
}
