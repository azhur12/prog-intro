package markup;

import java.util.List;

public class Emphasis extends AbstractElement {
    public Emphasis(List<AbstractText> list) {
        super(list, "*", "\\emph{", "}");
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
