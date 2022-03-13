package javax.microedition.lcdui;

public class Alert extends Screen {
    public static final int FOREVER = -2;
    private AlertType type;
    private String string;
    
    public Alert(String title) {
        setTitle(title);
    }

    public Alert(String title,
             String alertText,
             Image alertImage,
             AlertType alertType) {
                 
        setTitle(title);
        setString(alertText);
        setType(alertType);
    }

    public void setTimeout(int time) {
    }
    
    /**
     * @return type
     */
    public AlertType getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(AlertType type) {
        this.type = type;
    }

    /**
     * @return
     */
    public String getString() {
        return string;
    }

    /**
     * @param string
     */
    public void setString(String string) {
        this.string = string;
    }

}
