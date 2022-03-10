package javax.microedition.lcdui;

public abstract class Item {
    String label;
    java.awt.Component c;
    
    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
