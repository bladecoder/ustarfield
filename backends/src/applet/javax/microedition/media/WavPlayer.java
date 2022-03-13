package javax.microedition.media;

import java.applet.*;

public class WavPlayer implements Player {
    static public Applet applet;

    AudioClip clip;

    WavPlayer(String locator) {
        String s[] = locator.split(":");

        java.net.URL url = WavPlayer.class.getResource(s[1]);

        if(url ==  null) {
            System.out.println("ERROR, sound file not found: " + s[1]);
            return;
            //System.exit(1);
        }
        
        clip = applet.getAudioClip(url);
    }
    
    public void realize() throws MediaException {}
    public void prefetch() throws MediaException {}
    
    public void start() throws MediaException {
        if(clip!=null)
            clip.play();
    }
    
    public void stop() throws MediaException {
        //clip.stop();
    }
    
    public void deallocate() {}
    public void close() {}
    
    public long setMediaTime(long now) throws MediaException {
        return 0L;
    }
    
    public long getMediaTime() {
        return 0L;
    }
    
    public int getState() {
        return 0;
    }
    
    public long getDuration() {
        return 0L;
    }
    
    public String getContentType() {
        return "audio/x-wav";
    }
    
    public void setLoopCount(int count) {}
    public void addPlayerListener(PlayerListener playerListener) {}
    public void removePlayerListener(PlayerListener playerListener) {}
    
    public Control[] getControls() {
        return new Control[0];
    }
    
    public Control getControl(String controlType) {
        return null;
    }
}
