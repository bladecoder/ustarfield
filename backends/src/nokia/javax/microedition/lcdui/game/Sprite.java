package javax.microedition.lcdui.game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Sprite extends Layer {
    /** Causes the Sprite to appear reflected about its vertical center. */
    public static int TRANS_MIRROR=0;

    /** Causes the Sprite to appear reflected about its vertical center and then rotated clockwise by 180 degrees. */
    public static int TRANS_MIRROR_ROT180=1;

    /** Causes the Sprite to appear reflected about its vertical center and then rotated clockwise by 270 degrees. */
    public static int TRANS_MIRROR_ROT270=2;

    /** Causes the Sprite to appear reflected about its vertical center and then rotated clockwise by 90 degrees.*/
    public static int TRANS_MIRROR_ROT90=3;

    /** No transform is applied to the Sprite. */
    public static int TRANS_NONE=4;

    /** Causes the Sprite to appear rotated clockwise by 180 degrees.*/
    public static int TRANS_ROT180=5;

    /** Causes the Sprite to appear rotated clockwise by 270 degrees.*/
    public static int TRANS_ROT270=6;

    /** Causes the Sprite to appear rotated clockwise by 90 degrees. */
    public static int TRANS_ROT90=7;

    /** Sprite Image */
    private Image image;

    /** X coordinate of the Collision Rectangle */
    private int xCR;

    /** Y coordinate of the Collision Rectangle */
    private int yCR;

    /** Width of the Collision Rectangle */
    private int widthCR;

    /** Height of the Collision Rectangle */
    private int heightCR;

    /** Number of raw frames */
    private int frameCount;

    /** Current frame sequence */
    private int[] frameSequence;

    /** Current frame */
    private int idx;

    /** Current frame position in the image */
    private int xFrame,yFrame;

    /** X coordinate of the Reference Pixel */
    private int xRP;

    /** Y coordinate of the Reference Pixel */
    private int yRP;

    /** Creates a new non-animated Sprite using the provided Image.*/
    public Sprite(Image image) {
        this(image, image.getWidth(), image.getHeight());
    }

    /** Creates a new animated Sprite using frames contained in the provided Image.*/
    public Sprite(Image image, int frameWidth, int frameHeight)
    throws NullPointerException {
        if (image == null)
            throw new NullPointerException("The image can not be null");
        if (frameWidth < 1)
            throw new IllegalArgumentException("The frame width must be greater than 0");
        if (frameHeight < 1)
            throw new IllegalArgumentException("The frame height must be greater than 0");
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        if (frameWidth * (imageWidth / frameWidth) != imageWidth)
            throw new IllegalArgumentException("The image width must be an integer multiple of the frame Width");
        if (frameHeight * (imageHeight / frameHeight) != imageHeight)
            throw new IllegalArgumentException("The image height must be an integer multiple of the frame Height");

        this.image = image;
        setVisible(true);
        setPosition(0,0);
        width = frameWidth;
        height = frameHeight;
        int framesInARow = imageWidth / width;
        int framesInACol = imageHeight / height;
        frameCount = framesInARow * framesInACol;
        xFrame = 0;
        yFrame = 0;

        frameSequence = new int[frameCount];
        for (int i=0; i<frameCount; i++)
            frameSequence[i] = i;
        idx = 0;

        xRP = 0;
        yRP = 0;

        defineCollisionRectangle( 0, 0, width, height);
    }

    /** Creates a new Sprite from another Sprite.*/
    public Sprite(Sprite s) {
        this(s.image, s.width, s.height);
    }

    /** Checks for a collision between this Sprite and the specified Image with its upper left corner at the specified location. */
    public boolean collidesWith(Image image, int x, int y, boolean pixelLevel) {
        if (!isVisible())
            return false;
        return true;
    }

    /** Checks for a collision between this Sprite and the specified Sprite.
     *  MISSING: pixelLevel collision
     */
    public boolean collidesWith(Sprite s, boolean pixelLevel) {
        if (!isVisible())
            return false;

        int left1 = getX() + xCR;
        int top1 = getY() + yCR;
        int left2 = s.getX() + s.xCR;
        int top2 = s.getY() + s.yCR;

        int right1 = left1 + widthCR;
        int bottom1 = top1 + heightCR;
        int right2 = left2 + s.widthCR;
        int bottom2 = top2 + s.heightCR;

        if (bottom1 < top2) return false;
        if (top1 > bottom2) return false;

        if (right1 < left2) return false;
        if (left1 > right2) return false;

        return true;
    }

    /** Checks for a collision between this Sprite and the specified TiledLayer.
     */
    public boolean collidesWith(TiledLayer t, boolean pixelLevel) {
	int lx1 = getX() + xCR;
	int ly1 = getY() + yCR;
	int lx2 = getX() + xCR + widthCR - 1;
	int ly2 = getY() + yCR + heightCR - 1;
	
	/* check if the sprite is inside the tiled layer */
	if (lx2 < t.getX())
	    return false;
	if (ly2 < t.getY())
	    return false;
	if (lx1 > (t.getX() + t.getWidth()))
	    return false;
	if (ly1 > (t.getY() + t.getHeight()))
	    return false;

	int x1 = (lx1 - t.getX()) / t.getCellWidth();
	int y1 = (ly1 - t.getY()) / t.getCellHeight();
	int x2 = (lx2 - t.getX()) / t.getCellWidth();
	int y2 = (ly2 - t.getY()) / t.getCellHeight();
	int cell = 0;
	/*
	System.out.println("el x es "+x+" y xCR es "+xCR+" y widthCR es "+widthCR);
	System.out.println("el y es "+y+" y yCR es "+yCR+" y heightCR es "+heightCR);
	System.out.println("Los limites son "+x1+" "+x2+" "+y1+" "+y2);
	*/

	x1 = Math.max(x1, 0);
	y1 = Math.max(y1, 0);
	x2 = Math.min(x2, t.getColumns()-1);
	y2 = Math.min(y2, t.getRows()-1);

	for (int i=x1; i<=x2; i++) {
	    for (int j=y1; j<=y2; j++) {
		cell = t.getCell(i, j);
		if (cell != 0)
		    return true;
	    }
	}

	return false;
    }

    /** Defines the Sprite's bounding rectangle that is used for collision detection purposes. */
    public void defineCollisionRectangle(int x, int y, int width, int height)
    throws IllegalArgumentException {
        if (width < 0)
            throw new IllegalArgumentException("The width mus be positive");
        if (height < 0)
            throw new IllegalArgumentException("The height mus be positive");
        xCR = getX();
        yCR = getY();
        widthCR = width;
        heightCR = height;
    }

    /** Defines the reference pixel for this Sprite.*/
    public void defineReferencePixel(int x, int y) {
        xRP = getX();
        yRP = getY();
    }

    /** Gets the current index in the frame sequence.*/
    public int getFrame() {
        return idx;
    }

    /** Gets the number of elements in the frame sequence. */
    public int getFrameSequenceLength() {
        return frameSequence.length;
    }

    /** Gets the number of raw frames for this Sprite.*/
    public int getRawFrameCount() {
        return frameCount;
    }

    /** Gets the horizontal position of this Sprite's reference pixel in the painter's coordinate system.*/
    public int getRefPixelX() {
        return xRP;
    }

    /** Gets the vertical position of this Sprite's reference pixel in the painter's coordinate system.*/
    public int getRefPixelY() {
        return yRP;
    }

    /** Selects the next frame in the frame sequence.*/
    public void nextFrame() {
        idx = (idx + 1) % frameSequence.length;
        setFramePos();
    }

    /** Draws the Sprite.*/
    public void paint(Graphics g) {
        if(!isVisible()) return;

	int cX, cY, cW, cH;
	cX = g.getClipX();
	cY = g.getClipY();
	cW = g.getClipWidth();
	cH = g.getClipHeight();
        g.clipRect(getX(),getY(),width,height);
        g.drawImage(image, getX()-xFrame, getY()-yFrame,Graphics.TOP|Graphics.LEFT);
	g.setClip(cX, cY, cW, cH);
    }

    private void setFramePos() {
        xFrame = (frameSequence[idx] * width) % image.getWidth();
        yFrame = (frameSequence[idx] * width) / image.getWidth() * height;
    }

    /** Selects the previous frame in the frame sequence.*/
    public void prevFrame() {
        idx = (idx - 1) % frameSequence.length;
        setFramePos();
    }

    /** Selects the current frame in the frame sequence.*/
    public void setFrame(int sequenceIndex) {
        idx = sequenceIndex;
        setFramePos();
    }

    /** Set the frame sequence for this Sprite.*/
    public void setFrameSequence(int[] sequence) {
        frameSequence = sequence;
        idx = 0;
        setFramePos();
    }

    /** Changes the Image containing the Sprite's frames.*/
    public void setImage(Image img, int frameWidth, int frameHeight) {}

    /** Sets this Sprite's position such that its reference pixel is located at (x,y) in the painter's coordinate system.*/
    public void setRefPixelPosition(int x, int y) {
        xRP = getX();
        yRP = getY();
    }

    /** Sets the transform for this Sprite. */
    public void setTransform(int transform) {
    }
}

