package markup;

import java.util.List;

public class Paragraph implements AbstractText {
    protected static List<AbstractText> list;

    public Paragraph(List<AbstractText> list) {
        Paragraph.list = list;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        for (AbstractText text : list) {
            text.toMarkdown(sb);
        }
    }

    @Override
    public void toTex(StringBuilder sb) {
        for (AbstractText text : list) {
            text.toTex(sb);
        }
    }
}
