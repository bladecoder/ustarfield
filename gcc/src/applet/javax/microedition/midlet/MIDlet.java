package javax.microedition.midlet;

import java.util.*;
//import java.io.*;

public abstract class MIDlet
{
    private static Hashtable atts = new Hashtable();
    public static java.applet.Applet applet = null;

    static {
        String s;
        String sa[];
        
        /*
        
        try {
            InputStream is = 
                Class.forName("javax.microedition.midlet.MIDlet").getResourceAsStream("/META-INF/MANIFEST.MF");

            BufferedReader d = new BufferedReader(new InputStreamReader(is));

            while((s = d.readLine()) != null) {
                sa = s.split(":");
                if(sa.length < 2) continue;
                
                atts.put(sa[0].trim(), sa[1].trim());
            }

        } catch(Exception e) {
            System.out.println("Cannot get properties from MANIFEST file: " +
                    e.toString());
        }
        */
    }

    protected MIDlet() {
    }

    protected abstract void destroyApp(boolean flag);
    
    public void publicDestroyApp(boolean flag) {
        destroyApp(flag);
    }

    public String getAppProperty(String s) {
        String prop = (String)atts.get(s);
        
        if(prop == null) prop = applet.getParameter(s);
        
        return prop;
    }

    public void notifyDestroyed() {
        System.exit(0);
    }

    public void notifyPaused() {
    }

    protected abstract void pauseApp();

    public void resumeRequest() {
    }

    protected abstract void startApp();

    public void publicStartApp() {
        startApp();
    }
}
