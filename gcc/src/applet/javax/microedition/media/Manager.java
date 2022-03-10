package javax.microedition.media;

import java.io.IOException;
import java.io.InputStream;

public class Manager {
    public static final String TONE_DEVICE_LOCATOR = "device://tone";
    static String contentTypes[] = {"audio/x-wav"};
    static String protocols[] = {"resource"};

    public static String[] getSupportedContentTypes(String protocol) {
        return contentTypes;
    }

    public static String[] getSupportedProtocols(String content_type) {
        return protocols;
    }

    public static Player createPlayer(String locator)
                       throws IOException, MediaException {
        Player p = new WavPlayer(locator);

        p.start();
        p.stop();

        return  p;
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
