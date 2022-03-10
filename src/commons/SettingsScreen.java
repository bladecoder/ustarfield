package commons;

import javax.microedition.lcdui.*;
import commons.i18n.ResourceBundle;

class SettingsScreen extends List implements CommandListener {

    private final GameMIDlet midlet;
    private final String soundSetting;
    private final String vibraSetting;
    
    SettingsScreen(GameMIDlet midlet) {
        super(ResourceBundle.getString(GameMIDlet.COMMON_TEXTS, "Settings"),
              List.IMPLICIT);
        
        this.midlet = midlet;
        
        // soundSetting: List index = 0
        soundSetting = ResourceBundle.getString(
                GameMIDlet.COMMON_TEXTS, "Sound");
        String onOff = onOffString(Settings.getUseSound());
        append((soundSetting + " " + onOff),null);
        
        // vibraSetting: List index = 1
        vibraSetting = ResourceBundle.getString(
                GameMIDlet.COMMON_TEXTS, "Vibration");
        onOff = onOffString(Settings.getUseVibration());
        append((vibraSetting + " " + onOff), null);
        addCommand(new Command(ResourceBundle.getString(
                    GameMIDlet.COMMON_TEXTS, "Back"), Command.BACK, 1));
        setCommandListener(this);
    }
    
    public void commandAction(Command command, Displayable d) {
        if (command == List.SELECT_COMMAND) {
            switch (getSelectedIndex()) {
                // soundSetting
            case 0:
                midlet.settingsScreenEdit(soundSetting,
                                          Settings.getUseSound());
                break;
                // vibraSetting
            case 1:
                midlet.settingsScreenEdit(vibraSetting,
                                          Settings.getUseVibration());
                break;
            }
        } else {
            // the application code only adds the Back command
            midlet.goMainMenu();
        }
    }
    
    void setUseSound() {
        // soundSetting: List index = 0
        String onOff = onOffString(Settings.getUseSound());
        set (0, (soundSetting + " " + onOff), null);
    }
    
    void setUseVibration() {
        // vibraSetting: List index = 1
        String onOff = onOffString(Settings.getUseVibration());
        set (1, (vibraSetting + " " + onOff), null);
    }
    
    String onOffString(boolean isOn) {
        return ResourceBundle.getString(GameMIDlet.COMMON_TEXTS, isOn ? "On" : "Off");
    }
}

