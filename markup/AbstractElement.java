package markup;

import java.util.List;

public abstract class AbstractElement implements AbstractText {
    // :NOTE: почему protected
    // :NOTE: имена не по канону
    protected final List<AbstractText> list;
    protected final String MarkBorder;
    protected final String StartTex;
    protected final String EndTex;

    public AbstractElement(List<AbstractText> list, String MarkBorder, String StartTex, String EndTex) {
        this.list = list;
        this.MarkBorder = MarkBorder;
        this.StartTex = StartTex;
        this.EndTex = EndTex;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(MarkBorder);
        for (AbstractText text : list) {
            text.toMarkdown(sb);
        }
        sb.append(MarkBorder);
    }

    @Override
    public void toTex(StringBuilder sb) {
        sb.append(StartTex);
        for (AbstractText text : list) {
            text.toTex(sb);
        }
        sb.append(EndTex);
    }
}