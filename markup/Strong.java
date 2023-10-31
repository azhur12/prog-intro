package markup;

import java.util.List;

public class Strong extends AbstractElement {
    public Strong(List<AbstractText> list) {
        super(list, "__", "\\textbf{", "}");
    }

    // :NOTE: Зачем эти методы?
    @Override
    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb);
    }

    @Override
    public void toTex(StringBuilder sb) {
        super.toTex(sb);
    }
}
