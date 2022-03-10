package starfield;

import commons.*;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;

public class StarfieldMIDlet extends GameMIDlet {
    Engine engine = null;

    public GameManager createGameManager() {
        try {
            engine = new Engine(this);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorMsg(e.getMessage());
        }

        return engine;
    }

    protected void showScoresProtected() {
        engine.setHof();
        Display.getDisplay(this).setCurrent(gameManager.getCanvas());
    }

    protected void showMenuProtected(boolean isGameOver) {
        engine.setMenu(isGameOver);

        Display.getDisplay(this).setCurrent(gameManager.getCanvas());
    }
    
    protected void mainMenuAddContinue() {
        engine.getMenu().addContinue();
    }
    
    protected void mainMenuDeleteContinue() {
            engine.getMenu().deleteContinue();
    }    
    
    protected Displayable getMainMenuDisplayable() {       
        return engine.screen;
    }    

}
