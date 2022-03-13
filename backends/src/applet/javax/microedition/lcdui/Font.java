package javax.microedition.lcdui;

/* NOTE: USE awt FONTS */
public class Font {
    public static final int STYLE_PLAIN = 0;
    public static final int STYLE_BOLD = 1;
    /*...*/
    public static final int SIZE_SMALL = 8;
    public static final int SIZE_MEDIUM = 0;
    public static final int SIZE_LARGE = 16;
    /*...*/
    public static final int FACE_SYSTEM = 0;

    java.awt.Font font;
    
    public static Font getFont(int face, int style, int size) {       
        Font f = new Font();
        //f.font = ;
        
        return f;
    }

    public int getHeight() {
        return 12;
    }

   public int charsWidth(char[] ch, int offset, int length) {
       return 12 * length;
   }
 
}
