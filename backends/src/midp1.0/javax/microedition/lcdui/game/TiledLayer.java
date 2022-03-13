package javax.microedition.lcdui.game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class TiledLayer extends Layer {
    protected int[][] tileSet;
    protected int columns;
    protected int rows;
    protected Image image;
    protected int tileWidth;
    protected int tileHeight;

    /** Creates a new TiledLayer. */
    public TiledLayer(int columns, int rows, Image image, int tileWidth, int tileHeight) {
	this.tileWidth = tileWidth;
	this.tileHeight = tileHeight;
	this.columns = columns;
	this.rows = rows;
	this.image = image;
	tileSet = new int[rows][columns];
	for (int y=0; y<rows; y++)
	    for (int x=0; x<columns; x++)
		tileSet[y][x] = 0;
	setVisible(true);
        setPosition(0,0);
	width = tileWidth * columns;
	height = tileHeight * rows;
    }

    /** Creates a new animated tile and returns the index that refers to the new animated tile. */
    public int createAnimatedTile(int staticTileIndex) {
	System.out.println("This method is not implemented");
	return 0;
    }

    /** Fills a region cells with the specific tile. */
    public void fillCells(int col, int row, int numCols, int numRows, int tileIndex) {
	System.out.println("This method is not implemented");
    }

    /** Gets the tile referenced by an animated tile. */
    public int getAnimatedTile(int animatedTileIndex) {
	System.out.println("This method is not implemented");
	return 0;
    }

    /** Gets the contents of a cell. */
    public int getCell(int col, int row) {
	return tileSet[row][col];
    }

    /** Gets the height of a single cell, in pixels. */
    public int getCellHeight() {
	return tileHeight;
    }

    /** Gets the width of a single cell, in pixels. */
    public int getCellWidth() {
	return tileWidth;
    }

    /** Gets the number of columns in the TiledLayer grid. */
    public int getColumns() {
	return columns;
    }

    /** Gets the number of rows in the TiledLayer grid. */
    public int  getRows() {
	return rows;
    }

    /** Draws the TiledLayer. */
    public void paint(Graphics g) {
	int clipX, clipY, clipW, clipH;
	int index, xTile, yTile;
	/* save the clip */
	clipX = g.getClipX();
	clipY = g.getClipY();
	clipW = g.getClipWidth();
	clipH = g.getClipHeight();

	/* we don't want to draw all the tiles, just the ones which are inside
	   the clip */
	int x1 = clipX / tileWidth;
	int x2 = Math.min((clipX + clipW) / tileWidth, columns-1);
	int y1 = clipY / tileHeight;
	int y2 = Math.min((clipY + clipH) / tileHeight, rows-1);
	for (int y=y1; y<=y2; y++)
	    for (int x=x1; x<=x2; x++) {
		index = tileSet[y][x];
		if (index > 0) {
		    /* restore the clip */
		    g.setClip(clipX, clipY, clipW, clipH);

		    /* compute the exact position */
		    xTile = ((index - 1) * tileWidth) % image.getWidth();
		    yTile = ((index - 1) * tileWidth) 
			/ image.getWidth() * tileHeight;

		    g.clipRect(x * tileWidth, y * tileHeight,
			      tileWidth, tileHeight);

		    g.drawImage(image,
				x * tileWidth - xTile,
				y * tileHeight - yTile,
				Graphics.TOP | Graphics.LEFT);
		}
	    }
	/* restore the clip */
	g.setClip(clipX, clipY, clipW, clipH);
    }

    /** Associates an animated tile with the specified static tile. */
    public void setAnimatedTile(int animatedTileIndex, int staticTileIndex) {
	System.out.println("This method is not implemented");
    }

    /** Sets the contents of a cell. */
    public void setCell(int col, int row, int tileIndex) {
	tileSet[row][col] = tileIndex;
    }

    /** Change the static tile set. */
    public void setStaticTileSet(Image image, int tileWidth, int tileHeight) {
	System.out.println("This method is not implemented");
    }
}

