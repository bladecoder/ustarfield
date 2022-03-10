package commons;

import javax.microedition.lcdui.*;
import commons.i18n.ResourceBundle;

class MainMenu extends List implements CommandListener {
    private final GameMIDlet midlet;
    private boolean hasContinue = false;
    
    MainMenu(GameMIDlet midlet) {
        super(midlet.getAppProperty("MIDlet-Name"), List.IMPLICIT);
        this.midlet = midlet;

        // add default menu items
        append(ResourceBundle.getString(
                    GameMIDlet.COMMON_TEXTS, "New Game"), null);
        append(ResourceBundle.getString(
                    GameMIDlet.COMMON_TEXTS, "Best Scores"), null);

        if(gcc.DeviceControl.hasSoundCapability() ||
            gcc.DeviceControl.hasVibrationCapability()) {
            
            append(ResourceBundle.getString(
                    GameMIDlet.COMMON_TEXTS, "Settings"), null);
        }
        
        append(ResourceBundle.getString(
                    GameMIDlet.COMMON_TEXTS, "Instructions"), null);
        append(ResourceBundle.getString(
                    GameMIDlet.COMMON_TEXTS, "About"), null);
                    
        addCommand(new Command(ResourceBundle.getString(
                    GameMIDlet.COMMON_TEXTS, "Exit"), Command.EXIT, 1)); 
        
        setCommandListener(this);
    }

    
    void addContinue() {
        if(!hasContinue) {
            insert(0, ResourceBundle.getString(
                    GameMIDlet.COMMON_TEXTS, "Continue"), null);
            hasContinue = true;
        }
    }

    void deleteContinue() {
        if(hasContinue) {
            this.delete(0);
            hasContinue = false;
        }
    }

    void selectContinue() {
        if(hasContinue)
            this.setSelectedIndex(0,true);
    }

    public void commandAction(Command command, Displayable d) {
        if(command == List.SELECT_COMMAND) {
            String selected = getString(getSelectedIndex());

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
            }
        } else {
            midlet.mainMenuExit();
        }

    }
        
}
