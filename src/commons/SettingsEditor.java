package commons;

import javax.microedition.lcdui.*;
import commons.i18n.ResourceBundle;

// Class SettingEditor is a screen for simple editing of
// binary (on / off) type settings.
class SettingsEditor extends List implements CommandListener {
    
    private final GameMIDlet midlet;
    
    SettingsEditor(GameMIDlet midlet, 
                  String settingName, boolean isSettingOn) {
        super(settingName, List.IMPLICIT);
        this.midlet = midlet;
        
        append(ResourceBundle.getString(
                    GameMIDlet.COMMON_TEXTS, "On"), null); // index 0
        append(ResourceBundle.getString(
                    GameMIDlet.COMMON_TEXTS, "Off"), null); // index 1
        
        if (isSettingOn) {
            setSelectedIndex(0, true); // select on
        } else {
            setSelectedIndex(1, true); // select off
        }
        
        addCommand(new Command(ResourceBundle.getString(
                    GameMIDlet.COMMON_TEXTS, "Back"), Command.BACK, 1));
        setCommandListener(this);
    }
    
    public void commandAction(Command command, Displayable d) {
        if (command == List.SELECT_COMMAND) {
            boolean isSettingOn = (getSelectedIndex() == 0);
            midlet.settingEditorSave(this.getTitle(), isSettingOn);
        } else {
            // the application code only adds one command: Back
            midlet.showSettings();
        }
    }
}

