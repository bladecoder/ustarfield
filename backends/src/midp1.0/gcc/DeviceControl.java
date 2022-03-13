package gcc;

public class DeviceControl {
    public static boolean hasSoundCapability() { return false; }

    public static boolean hasVibrationCapability() { return false; }

    public static void setLights(int num, int level) {
    }

    public static void flashLights(long duration) {
    }

    public static void startVibra(int freq, long duration) {
    }

    public static void stopVibra() {
    }

    public static String getLocale() {
        return System.getProperty("microedition.locale");
    }
}
