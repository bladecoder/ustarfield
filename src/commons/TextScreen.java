package commons;

import javax.microedition.lcdui.*;

class TextScreen extends Form implements CommandListener {
    private final GameMIDlet midlet;

    TextScreen(GameMIDlet midlet, String title, String text,
                      String closeLabel) {
            super(title);
            this.midlet = midlet;
            append(text);
            addCommand(new Command(closeLabel , Command.BACK, 1));
            setCommandListener(this);
    }
    
    public void commandAction(Command c, Displayable d) {
            // The application code only adds a 'close' command.
            midlet.goMainMenu();
    }
}

