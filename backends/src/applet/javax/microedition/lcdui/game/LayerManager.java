package javax.microedition.lcdui.game;

import javax.microedition.lcdui.Graphics;
import java.util.Vector;

public class LayerManager {
    private Vector layers;
    private int xViewWindow;
    private int yViewWindow;
    private int widthViewWindow;
    private int heightViewWindow;

    /** Creates a new LayerManager.*/
    public LayerManager() {
	layers = new Vector();
	xViewWindow = 0;
	yViewWindow = 0;
	widthViewWindow = Integer.MAX_VALUE;
	heightViewWindow = Integer.MAX_VALUE;
    }

    /** Appends a Layer to this LayerManager.*/
    public void append(Layer l) {
	layers.addElement(l);
    }
    
    /** Gets the Layer with the specified index.*/
    public Layer getLayerAt(int index) {
	return (Layer)layers.elementAt(index);
    }
    
    /** Gets the number of Layers in this LayerManager.*/
    public int getSize() {
	return layers.size();
    }
    
    /** Inserts a new Layer in this LayerManager at the specified index.*/
    public void insert(Layer l, int index) {
	layers.insertElementAt(l, index);
    }
    
    /** Renders the LayerManager's current view window at the specified location.*/
    public void paint(Graphics g, int x, int y) {
	int lx1, lx2, ly1, ly2;
	int clipX, clipY, clipW, clipH;
        
        
	clipX = g.getClipX();
	clipY = g.getClipY();
	clipW = g.getClipWidth();
	clipH = g.getClipHeight();
	g.translate(x - xViewWindow, y - yViewWindow);
	g.setClip(xViewWindow, yViewWindow, widthViewWindow, heightViewWindow);
	/* walk through the layers from last to first */
	for(int i=layers.size() - 1; i > -1; i--) {
	    Layer layer = (Layer)layers.elementAt(i);
	    if (layer.isVisible()) {
		lx1 = layer.getX();
		ly1 = layer.getY();
		lx2 = lx1 + layer.getWidth();
		ly2 = ly1 + layer.getHeight();
		
		/* check if the layer is out of the view window */
		if (lx2 < xViewWindow)
		    continue;
		if (ly2 < yViewWindow)
		    continue;
		if (lx1 > (xViewWindow + widthViewWindow))
		    continue;
		if (ly1 > (yViewWindow + heightViewWindow))
		    continue;

		/* paint the layer */
		layer.paint(g);
	    }
	}	
	g.translate(-x + xViewWindow, -y + yViewWindow);
	g.setClip(clipX, clipY, clipW, clipH);
    }
    
    /** Removes the specified Layer from this LayerManager.*/
    public void remove(Layer l) {
	layers.removeElement(l);
    }
    
    /** Sets the view window on the LayerManager.*/
    public void setViewWindow(int x, int y, int width, int height) {
	xViewWindow = x;
	yViewWindow = y;
	widthViewWindow = width;
	heightViewWindow = height;
    }
        
}

