package javax.microedition.lcdui.game;

import javax.microedition.lcdui.Graphics;

public abstract class Layer {
    /** Flag for visibility */
    private boolean visible;

    /** x coordinate */
    private int x;

    /** y coordinate */
    private int y;

    /** width of the layer */
    protected int width;

    /** height of the layer */
    protected int height;

    /** Gets the current height of this layer, in pixels.*/
    public int getHeight() {
	   return height;
    }

    /** Gets the current width of this layer, in pixels.*/
    public int getWidth() {
	   return width;
    }

    /** Gets the horizontal position of this Layer's upper-left corner in the painter's coordinate system.*/
    public int getX() {
	   return x;
    }

    /** Gets the vertical position of this Layer's upper-left corner in the painter's coordinate system.*/
    public int getY() {
	   return y;
    }

    /** Gets the visibility of this Layer.*/
    public boolean isVisible() {
	   return visible;
    }

    /** Moves this Layer by the specified horizontal and vertical distances.*/
    public void move(int dx, int dy) {
	   x += dx;
	   y += dy;
    }

    /** Paints this Layer if it is visible.*/
    public abstract  void paint(Graphics g);

    /** Sets this Layer's position such that its upper-left corner is located at (x,y) in the painter's coordinate system.*/
    public void setPosition(int x, int y) {
	   this.x = x;
	   this.y = y;
    }

    /** Sets the visibility of this Layer.*/
    public void setVisible(boolean visible) {
	   this.visible = visible;
    }
}

