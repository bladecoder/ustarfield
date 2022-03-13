package javax.microedition.lcdui;

public abstract class Screen extends Displayable {

    public void setTitle(String s) {
        label.setText(s);
    }

    public String getTitle() {
        return label.getText();
    }
}

