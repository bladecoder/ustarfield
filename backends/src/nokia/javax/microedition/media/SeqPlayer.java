package javax.microedition.media;

import javax.microedition.media.control.*;
import com.nokia.mid.sound.Sound;

class SeqPlayer implements Player, ToneControl {
    Sound sound;
    
    SeqPlayer() {
    }
    
    public void realize() throws MediaException {}
    public void prefetch() throws MediaException {}
    
    public void start() throws MediaException {
        if(sound == null) throw new MediaException();
        
        sound.play(1);
    }
    
    public void stop() throws MediaException {
        if(sound == null) throw new MediaException();

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
        return "audio/x-tone-seq";
    }
    
    public void setLoopCount(int count) {}
    public void addPlayerListener(PlayerListener playerListener) {}
    public void removePlayerListener(PlayerListener playerListener) {}
    
    public Control[] getControls() {
        return new Control[0];
    }
    
    public Control getControl(String controlType) {
        return this;
    }


    public void setSequence(byte[] sequence) {
        sound = new Sound(sequence, Sound.FORMAT_TONE);
        sound.setGain(255);
    }
}
