package javax.microedition.lcdui;

public class Command {
    public static final int SCREEN = 1;
    public static final int BACK = 2;
    public static final int OK = 4;
    public static final int EXIT = 7;

    private String label;
    private int commandType;
    private int priority;

    public Command(String label, int commandType, int priority) {
        this.label = label;
        this.commandType = commandType;
        this.priority = priority;
    }

    public int getCommandType() {
        return commandType;
    }
    
    public String getLabel() {
        return label;
    }
}
