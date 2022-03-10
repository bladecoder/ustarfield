package gcc;

public class DeviceControl {
    public static boolean hasSoundCapability() { return true; }

    public static boolean hasVibrationCapability() { return true; }

    public static void setLights(int num, int level) {
        com.nokia.mid.ui.DeviceControl.setLights(num, level);
    }

    public static void flashLights(long duration) {
        com.nokia.mid.ui.DeviceControl.flashLights(duration);
    }

    public static void startVibra(int freq, long duration) {
        com.nokia.mid.ui.DeviceControl.startVibra(freq, duration);
    }

    public static void stopVibra() {
        com.nokia.mid.ui.DeviceControl.stopVibra();
    }
            
    public static String getLocale() {
        return System.getProperty("microedition.locale");
    }
}
