package markup;

public interface AbstractText {
    void toMarkdown(StringBuilder sb);

    void toTex(StringBuilder sb);
}
