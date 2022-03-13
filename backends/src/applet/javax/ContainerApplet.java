package javax;

import java.applet.Applet;
import javax.microedition.midlet.*;
import java.awt.event.*;

public class ContainerApplet extends Applet
    implements KeyListener {

    MIDlet midlet;

    public void init() {
        setLayout(new java.awt.BorderLayout());
        
        String midletClass = getParameter("midlet");
        
        if(midletClass == null) {
            System.out.println("ERROR: Missing 'midlet' applet parameter");
            System.exit(1);
        }
        
        ClassLoader loader = getClass().getClassLoader();
        
        try{
            MIDlet.applet = this;
            midlet = (MIDlet)loader.loadClass(midletClass).newInstance();
        } catch(Exception e) {
            System.out.println(e.toString());
            System.exit(1);
        }

        addKeyListener( this );
        javax.microedition.lcdui.Display.getDisplay(midlet).panel = this;
        javax.microedition.lcdui.Image.applet = this;
        javax.microedition.media.WavPlayer.applet = this;
        javax.microedition.lcdui.Canvas.width = getSize().width;
        javax.microedition.lcdui.Canvas.height = getSize().height;

        midlet.publicStartApp();
    }

    public void stop() {
        midlet.publicDestroyApp(false);
    }

    public void keyPressed( KeyEvent e ) { 
        System.out.println("ContainerApplet: KeyPressed");
    }
    
    public void keyReleased( KeyEvent e ) { 
    }
    
    public void keyTyped( KeyEvent e ) {
    }
}
