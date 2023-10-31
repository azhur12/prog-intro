package markup;

import java.util.List;

public class Strikeout extends AbstractElement {
    public Strikeout(List<AbstractText> list) {
        super(list, "~", "\\textst{", "}");
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb);
    }

    @Override
    public void toTex(StringBuilder sb) {
        super.toTex(sb);
    }
}
