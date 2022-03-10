/*
 * Created on 24-ene-2004
 */
package starfield;

import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import commons.GameMIDlet;
import commons.SpriteFont;
import commons.i18n.ResourceBundle;

import javax.microedition.lcdui.Canvas;

/**
 * A graphic menu implementation.
 * 
 * @author rgarcia
 */
public class GraphicsMenu {
    private boolean hasContinue = false;

    private final int DELAY = 500;

    private long lastTickFrame = Timing.getTick();;

    private SpriteFont font;
    
    private Vector items = new Vector();
    private GameMIDlet midlet;
    private Sprite selector;
    private int selPos;
    private Image img;
    
    public GraphicsMenu(GameMIDlet m, SpriteFont f, Sprite s, Image i) {
        font = f;
        midlet = m;
        selector = s;
        img = i;
        
        setSelect(0);
        
        // add default menu items
        items.addElement(ResourceBundle.getString(GameMIDlet.COMMON_TEXTS, "New Game"));
        items.addElement(ResourceBundle.getString(GameMIDlet.COMMON_TEXTS, "Best Scores"));
        
        if(gcc.DeviceControl.hasSoundCapability() ||
            gcc.DeviceControl.hasVibrationCapability()) {
            
            items.addElement(ResourceBundle.getString(GameMIDlet.COMMON_TEXTS, "Settings"));
        }
        
        items.addElement(ResourceBundle.getString(GameMIDlet.COMMON_TEXTS, "Instructions"));
        items.addElement(ResourceBundle.getString(GameMIDlet.COMMON_TEXTS, "About"));
                    
        items.addElement(ResourceBundle.getString(GameMIDlet.COMMON_TEXTS, "Exit"));         
    }
    
    void paint(Graphics g) {
        updateAnim();
        selector.paint(g);
        for(int i=0; i < items.size(); i++) {
            g.drawImage(img, Screen.width/2, 5, Graphics.TOP | Graphics.HCENTER);
            font.paint(g, ((String)items.elementAt(i)).toUpperCase(), 
                                            font.getWidth()*2, (font.getHeight()+ font.getHeight() * 8 / 10) * (i + 2) ,
                                            SpriteFont.TOP | SpriteFont.LEFT);
        }
    }
    
    public void deleteContinue() {
        if(hasContinue) {
             items.removeElementAt(0);
             hasContinue = false;
         }       
    }
    
    public void selectContinue() {
        if(hasContinue) setSelect(0);       
    }
    
    public void addContinue() {
        if(!hasContinue) {
            items.insertElementAt(ResourceBundle.getString(
                    GameMIDlet.COMMON_TEXTS, "Continue"), 0);
            hasContinue = true;
        }        
    }
    
    private void setSelect(int i) {
        selPos = i;
        int y = (font.getHeight()+font.getHeight() * 8 / 10) * (selPos + 2);
        y += (font.getHeight() - selector.getHeight()) / 2;
        selector.setPosition(2, y);
    }
    
    private void updateAnim() {
        long actualTick = Timing.getTick();

        if((int)(actualTick - lastTickFrame) >= DELAY) {
            selector.nextFrame(); 
            
            lastTickFrame = actualTick;
        }
    }
    
    public void keyPressed(int keycode) {
        if(keycode == Canvas.UP) {
            if(selPos != 0)  setSelect(selPos - 1);           
        } else if(keycode == Canvas.DOWN) {
            if(selPos < items.size() - 1)  setSelect(selPos + 1);
        } else {
            String selected = (String)items.elementAt(selPos);

            if(selected.equals(ResourceBundle.getString(
                            GameMIDlet.COMMON_TEXTS, "Continue"))) {
                midlet.mainMenuContinue();
            } else if(selected.equals(ResourceBundle.getString(
                            GameMIDlet.COMMON_TEXTS, "New Game"))) {
                midlet.mainMenuNewGame();
            } else if(selected.equals(ResourceBundle.getString(
                            GameMIDlet.COMMON_TEXTS, "Settings"))) {
                midlet.showSettings();
            } else if(selected.equals(ResourceBundle.getString(
                            GameMIDlet.COMMON_TEXTS, "Best Scores"))) {
                midlet.showScores();

            } else if(selected.equals(ResourceBundle.getString(
                            GameMIDlet.COMMON_TEXTS, "About"))) {
                midlet.showAbout();
            } else if(selected.equals(ResourceBundle.getString(
                            GameMIDlet.COMMON_TEXTS, "Instructions"))) {
                midlet.showInstructions();
            } else if(selected.equals(ResourceBundle.getString(
                            GameMIDlet.COMMON_TEXTS, "Exit"))) {
                                midlet.mainMenuExit();
            }            
        }  
    }    
}
