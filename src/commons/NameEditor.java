package commons;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import commons.i18n.ResourceBundle;

/**
 * Input box to input the name shows in the hall of fame
 * 
 * @author rgarcia
 */
class NameEditor extends Form implements CommandListener {
    private int points;
    private GameMIDlet midlet;
    private TextField textField;

    /**
     * @param midlet The Game midlet
     */
    public NameEditor(GameMIDlet midlet) {
        super("Name");
        
        this.midlet = midlet;
        
        textField = new TextField("Input Text", null, 8, TextField.ANY);
        append(textField);
        addCommand(new Command(ResourceBundle.getString(
                     GameMIDlet.COMMON_TEXTS, "Ok"), Command.OK, 1));
                     
        setCommandListener(this);
    }
 
    /**
     * @return Inputted name
     */
    public String getName() {
        return textField.getString();
    }

    /**
     * @return Points
     */
    public int getPoints() {
        return points;
    }
    
    public void setPoints(int p) {
        this.points = p;
    }
    
    public void commandAction(Command command, Displayable d) {
          midlet.nameEditorClosed();
    }    
}
