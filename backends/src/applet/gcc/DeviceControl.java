package gcc;

public class DeviceControl {
    public static boolean hasSoundCapability() { return true; }

    public static boolean hasVibrationCapability() { return true; }

    public static void setLights(int num, int level) {
    }

    public static void flashLights(long duration) {
    }

    public static void startVibra(int freq, long duration) {
    }

    public static void stopVibra() {
    }

    /** Returns locale string.
     * SEE: how todo in applets
     */
    public static String getLocale() {
        return null;
    }
}
