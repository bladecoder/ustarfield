package javax.microedition.lcdui;

import java.awt.MediaTracker;
import java.applet.*;
import java.net.URL;

public class Image {
    static public Applet applet;
    
    public java.awt.Image i;

    public Image(int width, int height) {
        i = applet.createImage(width, height);
    }
    
    public Image(String name) throws java.net.MalformedURLException {
        MediaTracker tracker = new MediaTracker(applet);

        // Sharp Zaurus doesn't understand .PNGs so .GIFs are used
        // For using .GIFs the gif="true" applet param must be set
        if(applet.getParameter("gif") != null && 
                applet.getParameter("gif").equals("true")) {
            int idx = name.indexOf('.');

            name = name.substring(0,idx) + ".gif";
            //System.out.println("Using gif: " + name);
        }

        URL url = Image.class.getResource(name); 
        
        if(url == null) {
            System.out.println("ERROR, Image not found: " + name);
            System.exit(1);
        }

        i = java.awt.Toolkit.getDefaultToolkit().getImage(url); 

        tracker.addImage(i, 0);
        try {
            tracker.waitForID(0);
            if(tracker.isErrorID(0)) {
                System.out.println("EROR loading image " + name);
            }
        }catch(java.lang.InterruptedException e) {
              System.out.println("Interrupted exception");
        }
    }

    public static Image createImage(int width, int height) {
        Image img = new Image(width, height);

        return img;
    }

    public static Image createImage(String name) throws java.io.IOException {
        Image img = new Image(name);

        return img;
    }
    
    public int getHeight() {
        return i.getHeight(null);
    }

    public int getWidth() {
        return i.getWidth(null);
    }

     public Graphics getGraphics() {
         Graphics g = new Graphics();
         g.g = i.getGraphics();

         return g;
     }
}
