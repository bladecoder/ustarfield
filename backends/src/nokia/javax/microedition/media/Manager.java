package javax.microedition.media;

import java.io.IOException;
import java.io.InputStream;
import com.nokia.mid.sound.Sound;

public class Manager {
    public static final String TONE_DEVICE_LOCATOR = "device://tone";

    static String contentTypes[] = {"audio/x-wav","audio/x-tone-seq"};
    static String protocols[] = {"resource"};

    public static String[] getSupportedContentTypes(String protocol) {
        String s[] = new String[1];
        s[0] = contentTypes[0];
        return s;

        /*
        int formats[] = Sound.getSupportedFormats();
        if(formats.length == 0) return new String[0];
        
        if(formats.length == 1) {
            String s[] = new String[1];
            if(formats[0] == Sound.FORMAT_WAV) s[0] = contentTypes[0];
            else if(formats[0] == Sound.FORMAT_TONE) s[0] = contentTypes[1];

            return s;
        }

        return contentTypes;
        */
    }

    public static String[] getSupportedProtocols(String content_type) {
        return protocols;
    }

    public static Player createPlayer(String locator)
                           throws IOException, MediaException { 
        if(locator.equals(TONE_DEVICE_LOCATOR)) return new SeqPlayer();

        return new WavPlayer(locator);
    }

    public static Player createPlayer(InputStream stream,
                                  String type)
                           throws IOException,
                                  MediaException {
        return null;
    }

    public static void playTone(int note,
                            int duration,
                            int volume)
                     throws MediaException {
    }


}
