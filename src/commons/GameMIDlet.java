package commons;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import commons.i18n.ResourceBundle;

// GameMIDlet manages which screen is currently displayed.
// The MIDlet screen classes never directly call Display.setCurrent()
// themselves. The UI screens always make callbacks to GameMIDlet
// whenever the displayed screen should be changed. This centralizes
// management of the display resource into a single place
// (class GameMIDlet) in the application code.
public abstract class GameMIDlet extends MIDlet {
    public static final String COMMON_TEXTS = "commons.i18n.CommonTexts";
    public static final String GAME_TEXTS = "i18n.GameTexts";

    private MainMenu mainMenu = null;
    protected GameManager gameManager = null;
    private SettingsScreen settingsScreen = null;
    private NameEditor nameEditor = null;
    public HallOfFame hof = null;

    public GameMIDlet() {
        try {
            hof = new HallOfFame();
        } catch (Exception e) {
            //e.printStackTrace();
            //System.out.println("mostrando mensaje de error...");
            showErrorMsg(e.toString());
        }
    }

    public void startApp() {
        gameManager = createGameManager();
        Display.getDisplay(this).setCurrent(gameManager.getCanvas());
    }
 
    public void pauseApp() {
        if (gameManager != null) {
            gameManager.pause();
        }
    }

    public void destroyApp(boolean unconditional) {
        if (gameManager != null) {
            gameManager.stop();
        }
    }
    
    public abstract GameManager createGameManager();

    // User Interface screen callbacks
    // GameManager callback
    public void showErrorMsg(String alertMsg) {
        if (alertMsg == null) {
            alertMsg = "Error in program.";
        }
        
        Alert alert = new Alert("ERROR", alertMsg, null, AlertType.ERROR);
        alert.setTimeout(Alert.FOREVER);
        
        mainMenuDeleteContinue();
        Display.getDisplay(this).setCurrent(alert, getMainMenuDisplayable());
    }

    /**
     * We need a public version of showMenu.
     * 
     * @param isGameOver
     */
    public void showMenu(boolean isGameOver) {
        showMenuProtected(isGameOver);
    }
    
    protected void showMenuProtected(boolean isGameOver) {
        if (mainMenu == null) {
            mainMenu = new MainMenu(this);
        }
        
        if (isGameOver) {
            mainMenu.deleteContinue();
        } else {
            //gameManager.pause();
            mainMenu.selectContinue();
        }

        Display.getDisplay(this).setCurrent(mainMenu);
    }

    public void showNameEditor(int points) {
        if (nameEditor == null) {
            nameEditor = new NameEditor(this);
        }

        nameEditor.setPoints(points);
        Display.getDisplay(this).setCurrent(nameEditor);
    }

    // MainMenu callbacks
    public void mainMenuContinue() {
        Display.getDisplay(this).setCurrent(gameManager.getCanvas());
        gameManager.resume();
    }

    public void mainMenuNewGame() {
        gameManager.start();
        Display.getDisplay(this).setCurrent(gameManager.getCanvas());
        mainMenuAddContinue();
    }

    public void showScores() {
        showScoresProtected();
    }
    
    protected void showScoresProtected() {
        String title = ResourceBundle.getString(COMMON_TEXTS, "Best Scores");

        String back = ResourceBundle.getString(COMMON_TEXTS, "Back");

        TextScreen screen = new TextScreen(this, title, hof.toString(), back);
        Display.getDisplay(this).setCurrent(screen);
    }

    public void showSettings() {
        if (settingsScreen == null) {
            settingsScreen = new SettingsScreen(this);
        }

        Display.getDisplay(this).setCurrent(settingsScreen);
    }

    public void showInstructions() {
        String title = ResourceBundle.getString(COMMON_TEXTS, "Instructions");

        String back = ResourceBundle.getString(COMMON_TEXTS, "Back");
        String text = ResourceBundle.getString(GAME_TEXTS, "Instructions");

        TextScreen screen = new TextScreen(this, title, text, back);
        Display.getDisplay(this).setCurrent(screen);
    }

    public void showAbout() {
        String author = "Rafael Garcia";
        String webpage = "http://bladecoder.net/hof";
        String name = getAppProperty("MIDlet-Name");
        String version =
            ResourceBundle.getString(COMMON_TEXTS, "Version")
                + " "
                + getAppProperty("MIDlet-Version");
        String vendor = getAppProperty("MIDlet-Vendor");
        String about =
            name
                + " "
                + version
                + "\n"
                + author
                + "\n(c) "
                + vendor
                + "\n"
                + webpage;
        String title = ResourceBundle.getString(COMMON_TEXTS, "About");
        String back = ResourceBundle.getString(COMMON_TEXTS, "Back");

        TextScreen aboutScreen = new TextScreen(this, title, about, back);
        Display.getDisplay(this).setCurrent(aboutScreen);
    }

    public void mainMenuExit() {
        destroyApp(false);
        notifyDestroyed();
    }
    
    protected void mainMenuAddContinue() {
        mainMenu.addContinue();
    }
    
    protected void mainMenuDeleteContinue() {
        mainMenu.deleteContinue();
    }    
    
    protected Displayable getMainMenuDisplayable() {
        return mainMenu;
    }

    public void goMainMenu() {
        Display.getDisplay(this).setCurrent(getMainMenuDisplayable());
    }

    // SettingEditor callbacks
    public void settingEditorSave(String name, boolean isOn) {
        // update game setting and settings screen
        if (name.equals(ResourceBundle.getString(COMMON_TEXTS, "Vibration"))) {
            Settings.setUseVibration(isOn);
            settingsScreen.setUseVibration();
        } else if (
            name.equals(ResourceBundle.getString(COMMON_TEXTS, "Sound"))) {
            Settings.setUseSound(isOn);
            settingsScreen.setUseSound();
        }

        Alert confirm =
            new Alert(
                null,
                name + " " + settingsScreen.onOffString(isOn),
                null,
                AlertType.CONFIRMATION);
        // no title
        confirm.setTimeout(4000); // show Alert for 4 seconds
        Display.getDisplay(this).setCurrent(confirm, settingsScreen);
    }
   
    // SettingsScreen callbacks
    public void settingsScreenEdit(String name, boolean isOn) {
        Display.getDisplay(this).setCurrent(
            new SettingsEditor(this, name, isOn));
    }

    // TextScreen callbacks (i.e. About + Instructions)
    void nameEditorClosed() {
        try {
            hof.addScore(nameEditor.getName(), nameEditor.getPoints());
            Display.getDisplay(this).setCurrent(gameManager.getCanvas());
        } catch (Exception e) {
            showErrorMsg(e.getMessage());
        }
    }
}
