package expression;

public class Element {
    public ElementType type;
    public String value;

    public Element(ElementType type, String value) {
        this.type = type;
        this.value = value;
    }

    public Element(ElementType type, Character value) {
        this.type = type;
        this.value = value + "";
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
