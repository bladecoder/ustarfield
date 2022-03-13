package javax.microedition.lcdui;

public class Canvas extends Displayable implements java.awt.event.KeyListener{
    public static final int 	DOWN=6;
    public static final int 	FIRE=8;
    public static final int 	GAME_A=9;
    public static final int 	GAME_B=10;
    public static final int 	GAME_C=11;
    public static final int 	GAME_D=12;
    public static final int 	KEY_NUM0=48;
    public static final int 	KEY_NUM1=49;
    public static final int 	KEY_NUM2=50;
    public static final int 	KEY_NUM3=51;
    public static final int 	KEY_NUM4=52;
    public static final int 	KEY_NUM5=53;
    public static final int 	KEY_NUM6=54;
    public static final int 	KEY_NUM7=55;
    public static final int 	KEY_NUM8=56;
    public static final int 	KEY_NUM9=57;
    public static final int 	KEY_POUND=35;
    public static final int 	KEY_STAR=42;
    public static final int 	LEFT=2;
    public static final int 	RIGHT=5;
    public static final int 	UP=1;

    static public int width;
    static public int height;

    public Canvas() {
        component = new java.awt.Canvas();
        component.addKeyListener(this);
    }

    public int getGameAction(int keyCode) {
        if(keyCode == java.awt.event.KeyEvent.VK_UP || 
                keyCode == java.awt.event.KeyEvent.VK_NUMPAD8 ) return UP;
        if(keyCode == java.awt.event.KeyEvent.VK_DOWN || 
                keyCode == java.awt.event.KeyEvent.VK_NUMPAD2 ) return DOWN;
        if(keyCode == java.awt.event.KeyEvent.VK_LEFT ||
                keyCode == java.awt.event.KeyEvent.VK_NUMPAD4 ) return LEFT;
        if(keyCode == java.awt.event.KeyEvent.VK_RIGHT ||
                keyCode == java.awt.event.KeyEvent.VK_NUMPAD6 ) return RIGHT;
        if(keyCode == java.awt.event.KeyEvent.VK_ENTER ||
                keyCode == java.awt.event.KeyEvent.VK_SPACE ) return FIRE;
        if(keyCode == java.awt.event.KeyEvent.VK_0 ||
                keyCode == java.awt.event.KeyEvent.VK_NUMPAD0 ) return KEY_NUM0;
        if(keyCode == java.awt.event.KeyEvent.VK_1 ||
                keyCode == java.awt.event.KeyEvent.VK_NUMPAD1 ) return KEY_NUM1;
        if(keyCode == java.awt.event.KeyEvent.VK_2 ||
                keyCode == java.awt.event.KeyEvent.VK_NUMPAD2 ) return KEY_NUM2;
        if(keyCode == java.awt.event.KeyEvent.VK_3 ||
                keyCode == java.awt.event.KeyEvent.VK_NUMPAD3 ) return KEY_NUM3;
        if(keyCode == java.awt.event.KeyEvent.VK_4 ||
                keyCode == java.awt.event.KeyEvent.VK_NUMPAD4 ) return KEY_NUM4;
        if(keyCode == java.awt.event.KeyEvent.VK_5 || 
                keyCode == java.awt.event.KeyEvent.VK_NUMPAD5 ) return KEY_NUM5;
        if(keyCode == java.awt.event.KeyEvent.VK_6 ||
                keyCode == java.awt.event.KeyEvent.VK_NUMPAD6 ) return KEY_NUM6;
        if(keyCode == java.awt.event.KeyEvent.VK_7 ||
                keyCode == java.awt.event.KeyEvent.VK_NUMPAD7 ) return KEY_NUM7;
        if(keyCode == java.awt.event.KeyEvent.VK_8 ||
                keyCode == java.awt.event.KeyEvent.VK_NUMPAD8 ) return KEY_NUM8;
        if(keyCode == java.awt.event.KeyEvent.VK_9 ||
                keyCode == java.awt.event.KeyEvent.VK_NUMPAD9 ) return KEY_NUM9;
        if(keyCode == java.awt.event.KeyEvent.VK_A ) return GAME_A;
        if(keyCode == java.awt.event.KeyEvent.VK_B ) return GAME_B;
        if(keyCode == java.awt.event.KeyEvent.VK_C ) return GAME_C;
        if(keyCode == java.awt.event.KeyEvent.VK_D ) return GAME_D;
            
        return keyCode;
    }

    public int getHeight() {
        return height;
        //return component.getBounds().height;
    }

    public int	getKeyCode(int gameAction) {
        return gameAction;
    }
    
    public String getKeyName(int keyCode) {
        switch(keyCode) {
            default: return null;
        }
    }
 
    public int getWidth() {
        return width;
        //return component.getBounds().width;
    }

    protected void keyPressed(int keyCode) {
    }

    protected void keyReleased(int keyCode) {
    }

    
    public void repaint() {
        Graphics g = new Graphics();
        g.g = component.getGraphics();

        if(g.g == null) return;

        paint(g);
    }
    
    public void repaint(int x, int y, int width, int height) {
        //panel.repaint(x,y,width,height);
    }

    public void serviceRepaints() {
    }
    
    protected void paint(Graphics g) {
    }


    public void keyTyped(java.awt.event.KeyEvent e) {
    }

    public void keyPressed(java.awt.event.KeyEvent e) {
        //System.out.println("Canvas: KeyPressed");
        keyPressed(e.getKeyCode());
    }

    public void keyReleased(java.awt.event.KeyEvent e) {
        keyReleased(e.getKeyCode());
    }
}
