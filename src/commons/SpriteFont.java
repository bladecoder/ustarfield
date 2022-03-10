/*
 * Created on 06-oct-2003
 */
package commons;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

/**
 * @author rgarcia
 */
public class SpriteFont extends Sprite {
    
    /** Anchor types */
    public final static int   HCENTER=1;
    public final static int   VCENTER=2;
    public final static int   LEFT=4;
    public final static int   RIGHT=8;
    public final static int   TOP=16;
    public final static int   BOTTOM=32;

    /**
     * @param image
     * @param frameWidth
     * @param frameHeight
     * @throws NullPointerException
     */
    public SpriteFont(Image image, int frameWidth, int frameHeight)
        throws NullPointerException {
        super(image, frameWidth, frameHeight);
        
    }
    
    public void paint(Graphics g, String text, int x, int y, int anchor) {
        int posx = x;
        int posy = y;
        int stringWidth;
        
        if((anchor&HCENTER) != 0) {
            stringWidth = text.length() * getWidth();
            posx -= stringWidth / 2;
        } else if((anchor & RIGHT) != 0) {
            stringWidth = text.length() * getWidth();
            posx -= stringWidth;
        }
                        
        if((anchor & VCENTER) != 0) {
            posy -= getHeight() / 2;
        } else if((anchor & BOTTOM) != 0) {
            posy -= getHeight();
        }                
               
        char c;
        
        for(int i = 0; i < text.length(); i++) {
            c = text.charAt(i);
            if(c >= '0' && c <= 'Z') {
                setFrame(c - '0');
                setPosition(posx, posy);
                paint(g);
            }
            
            posx += getWidth();
        }       
    }
}
