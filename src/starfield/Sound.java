package starfield;

import java.util.Hashtable;
import javax.microedition.media.*;
import javax.microedition.media.control.*;
import commons.Settings;

import java.io.InputStream;

/**
 * Implements sound in the game
 * 
 * @author rgarcia
 */
class Sound {
    boolean hasWAV = false;
    boolean hasMIDI = false;
    boolean hasSeq = false;
    boolean hasSound = false;
    boolean hasMusic = false;

    private Hashtable sounds;
    
    private javax.microedition.media.Player p;
    private ToneControl tc;
/*
    //  Ringing tones
    private final static byte[] BULLET_SOUND_BYTES =
        {
            (byte) 0x02,
            (byte) 0x4A,
            (byte) 0x3A,
            (byte) 0x80,
            (byte) 0x40,
            (byte) 0x00,
            (byte) 0xF3,
            (byte) 0x48,
            (byte) 0x22,
            (byte) 0xC3,
            (byte) 0x4C,
            (byte) 0x35,
            (byte) 0x02,
            (byte) 0xAC,
            (byte) 0x22,
            (byte) 0xC0,
            (byte) 0x00 };
            
    private final static byte[] EXPLODE1_SOUND_BYTES =
        {
            (byte) 0x02,
            (byte) 0x4A,
            (byte) 0x3A,
            (byte) 0x80,
            (byte) 0x40,
            (byte) 0x01,
            (byte) 0x52,
            (byte) 0x48,
            (byte) 0x2D,
            (byte) 0x02,
            (byte) 0x71,
            (byte) 0x49,
            (byte) 0x18,
            (byte) 0x20,
            (byte) 0xD4,
            (byte) 0x12,
            (byte) 0x72,
            (byte) 0x00,
            (byte) 0x00 };

    private final static byte[] EXPLODE2_SOUND_BYTES =
        {
            (byte) 0x02,
            (byte) 0x4A,
            (byte) 0x3A,
            (byte) 0x80,
            (byte) 0x40,
            (byte) 0x00,
            (byte) 0xD2,
            (byte) 0xC8,
            (byte) 0x31,
            (byte) 0x03,
            (byte) 0x90,
            (byte) 0x23,
            (byte) 0x12,
            (byte) 0xB0,
            (byte) 0x00 };
*/
    /**
     * Constructor, detects the sound capabilities.
     * If there is not wav support, ring tones are loaded in the cache
     */
    Sound() throws Exception {
        // check for sound
        String ct[] = Manager.getSupportedContentTypes(null);
        for (int i = 0; i < ct.length; i++) {
            if (ct[i].equals("audio/x-wav")) {
                hasWAV = true;
                System.out.println("WAV support detected...");
            }

            if (ct[i].equals("audio/midi")) {
                hasMIDI = true;
                System.out.println("MIDI support detected...");
            }

            if (ct[i].equals("audio/x-tone-seq")) {
                hasSeq = true;
                System.out.println("Tone Sequence support detected...");
            }
        }

        sounds = new Hashtable();

        if (hasWAV || hasSeq)
            hasSound = true;

        if (hasMIDI)
            hasMusic = true;
/*        
        //hasWAV = false;    
        if(!hasWAV && hasSeq) {
            p =
                Manager.createPlayer(Manager.TONE_DEVICE_LOCATOR);
            p.realize();
        
            tc = (ToneControl) p.getControl("ToneControl");
            tc.setSequence(BULLET_SOUND_BYTES);

            sounds.put("laser",BULLET_SOUND_BYTES);
            sounds.put("bomb",EXPLODE1_SOUND_BYTES);
            sounds.put("bomb2",EXPLODE2_SOUND_BYTES);
        }
*/
    }

    /**
     * Gets the wav file from the cache and plays it
     * 
     * @param name The wav file to load (without .wav)
     * @throws Exception If some error occurs while playing
     */
    void playWAV(String name) throws Exception {
        try {
            javax.microedition.media.Player p =
                (javax.microedition.media.Player) sounds.get(name);

            // FIRST TIME DOESN'T PLAY SO WE CAN USE THE FIRST
            // EVENT TO LOAD THE FILE
            if (p == null) {

                InputStream is = getClass().getResourceAsStream(
                        "/" + name + ".wav");
                p = Manager.createPlayer(is, "audio/X-wav");

                if(p == null)
                    p = Manager.createPlayer("resource:/" + name + ".wav");
                
                p.prefetch();
                sounds.put(name, p);
            } else {
                p.stop();
                p.setMediaTime(0L);
                p.start();
            }
        } catch (Exception e) {
            throw new Exception(
                "Error playing sound " + name + " " + e.toString());
        }

    }

    /**
     * Gets the ringing tones from the cache and plays it
     * 
     * @param name The name of the tone in the cache
     * @throws Exception If some error occurs while playing
     */
    void playSeq(String name) throws Exception {
        byte b [] = (byte[])sounds.get(name);
        if(b == null) return;
        
        //tc.setSequence(b);
        p.stop();
        p.start();
    }

    /**
     * Plays the sound name if wavs or tones are supported and are enabled in the settings.
     * @param name Name of the sound, .wav file or tone ring name
     * @throws Exception If some error playing file occurs
     */
    void playSound(String name) throws Exception {
        if (!hasSound || !Settings.getUseSound())
            return;

        if (hasWAV)
            playWAV(name);
        else if (hasSeq)
            playSeq(name);
    }
}
