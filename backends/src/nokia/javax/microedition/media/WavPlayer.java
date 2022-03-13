package javax.microedition.media;

import com.nokia.mid.sound.Sound;
import java.io.*;

class WavPlayer implements Player {
    Sound sound;

    WavPlayer(String locator) {
        int idx = locator.indexOf(':');

        String s = locator.substring(idx + 1);

        // read file
        InputStream is = getClass().getResourceAsStream(s);   
        
        if(is == null) {
            System.out.println("ERROR: loading sound file " + s);
            System.exit(1);
        }
 
        // maximum is 8kb
        byte data[] = new byte[8000];
        byte dest[] = null;
        
        try {
            int l = is.read(data);

            dest = new byte[l];
            System.arraycopy(data, 0, dest, 0, l);
        } catch(Exception e) {
            System.out.println("ERROR: reading sound file " + s);
            System.exit(1);
        }
        
        data = null;
            
        

        // create player
        sound = new Sound(dest, Sound.FORMAT_WAV);
        sound.setGain(255);
    }
    
    public void realize() throws MediaException {}
    public void prefetch() throws MediaException {}
    
    public void start() throws MediaException {
        sound.play(1);
    }
    
    public void stop() throws MediaException {
        sound.stop();
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
