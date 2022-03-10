package javax.microedition.lcdui;

public class Graphics {
    public static int 	SOLID=0;
    public static int 	VCENTER=2;
    public static int   LEFT=4;
    public static int   RIGHT=8;
    public static int   TOP=16;
    public static int   BOTTOM=32;    
    public static int 	BASELINE=64;

    public static int 	DOTTED=1;
    public static int 	HCENTER=1;

    public java.awt.Graphics g;

    public void	setClip(int x, int y, int width, int height) {
        g.setClip(x,y,width,height);
    }

    public void	drawImage(Image img, int x, int y, int anchor) {
        
        if((anchor&HCENTER) != 0) {
            java.awt.Rectangle r = g.getClipBounds();
            
            x = (r.width - img.getWidth()) / 2;
        }
        
        g.drawImage(img.i, x, y, null);
    }

    public void setColor(int color) {
        g.setColor(new java.awt.Color(color));
    }

    public void fillRect(int x, int y, int width, int height) {
        g.fillRect(x, y, width, height);
    }

    public void drawRect(int x, int y, int width, int height) {
        g.drawRect(x, y, width, height);
    }

    
    public void setFont(Font font) {
    }

    public void drawLine(int x0, int y0, int xf, int yf) {
        g.drawLine(x0, y0, xf, yf);
    }

    public void drawString(String str, int x, int y, int anchor) { 
        java.awt.FontMetrics fm = g.getFontMetrics(g.getFont());

        int h = fm.getHeight();
        int w = fm.stringWidth(str);
 
        if((anchor & BOTTOM) != 0) y = y - h;
        if((anchor & RIGHT) != 0) x = x - w;
        g.drawString(str, x, y);
    }

    public void translate(int x, int y) {
        g.translate(x, y);
    }
    
    public int getClipX() {
        return g.getClipBounds().x;
    }
    
    public int getClipY() {
        return g.getClipBounds().y;
    }
    
    public int getClipWidth() {
        return g.getClipBounds().width;
    }
    
    public int getClipHeight() {
        return g.getClipBounds().height;
    }

}
