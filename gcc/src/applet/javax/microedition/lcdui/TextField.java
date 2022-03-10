package javax.microedition.lcdui;

/**
 * J2ME TextField implementation
 * @author rgarcia
 *
 */
public class TextField extends Item {
    
    public static final int ANY = 0;
    
    java.awt.TextField tf;
    java.awt.Label lbl;

    public TextField(String label, String text, int maxSize, int constraints) {
        lbl = new java.awt.Label(label);                 
        tf = new java.awt.TextField(text, maxSize);
        c = new java.awt.Panel();
        ((java.awt.Panel)c).add(lbl);
        ((java.awt.Panel)c).add(tf);              
    }
    
    public String getString() {
        return tf.getText();
    }
}
