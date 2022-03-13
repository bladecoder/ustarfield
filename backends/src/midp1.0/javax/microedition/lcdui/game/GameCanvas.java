package javax.microedition.lcdui.game;

import javax.microedition.lcdui.*;

public class GameCanvas extends Canvas {
    /** The bit representing the DOWN key. */
    public static int 	DOWN_PRESSED = 1 << Canvas.DOWN;

    /** The bit representing the FIRE key. */
    public static int 	FIRE_PRESSED = 1 << Canvas.FIRE;

    /** The bit representing the GAME_A key (may not be supported on all devices). */
    public static int 	GAME_A_PRESSED = 1 << Canvas.GAME_A;

    /** The bit representing the GAME_B key (may not be supported on all devices). */
    public static int 	GAME_B_PRESSED = 1 << Canvas.GAME_B;

    /** The bit representing the GAME_C key (may not be supported on all devices). */
    public static int 	GAME_C_PRESSED = 1 << Canvas.GAME_C;

    /** The bit representing the GAME_D key (may not be supported on all devices). */
    public static int 	GAME_D_PRESSED = 1 << Canvas.GAME_D;

    /** The bit representing the LEFT key. */
    public static int 	LEFT_PRESSED = 1 << Canvas.LEFT;

    /** The bit representing the RIGHT key. */
    public static int 	RIGHT_PRESSED = 1 << Canvas.RIGHT;

    /** The bit representing the UP key. */
    public static int 	UP_PRESSED = 1 << Canvas.UP;


    /** Screen buffer implementation */
    private Image buf;
    private Graphics g;
    
    /** Screen dimensions */
    private int width, height;

    /** key pressed mask */
    int keyMask;

    /** Creates a new instance of a GameCanvas. */
    protected 	GameCanvas(boolean suppressKeyEvents) {
        width = getWidth();
        height = getHeight();
        buf = Image.createImage(width, height);
        g = buf.getGraphics();
        g.setClip(0, 0, width, height);
    }

    /** Flushes the off-screen buffer to the display.*/
    public void flushGraphics() {
        repaint();
        serviceRepaints();
    }

    /** Flushes the specified region of the off-screen buffer to the display.
     *  NOTE: Dificult to implement due to drawRegion() doesn't exists in MIDP1.0.
     *  MAYBE with repaint(x, y, width, heigth); (?)
     */
    public void flushGraphics(int x, int y, int width, int height) {
        repaint( x, y, width, height);
        serviceRepaints();
    }

    /** Obtains the Graphics object for rendering a GameCanvas.*/
    protected  Graphics getGraphics() {
	return g;
    }

    /** Gets the states of the physical game keys.*/
    public int getKeyStates() {
	return keyMask;
    }

    /** Paints this GameCanvas.*/
    protected void paint(Graphics g) {
        g.drawImage(buf, 0, 0, Graphics.TOP | Graphics.LEFT);
    }


    protected void keyPressed(int keyCode) {
        //System.out.println("keypressed");
        keyMask |= (1 << getGameAction(keyCode));
    }
    
    protected void keyReleased(int keyCode) {
        //System.out.println("keyreleased");
        keyMask &= (~(1 << getGameAction(keyCode)));
    }
    
    // ugly fix, breaks midp2.0 compatibility
    public void clearKeys() {
        keyMask = 0;
    }


}

