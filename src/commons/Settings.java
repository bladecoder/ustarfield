package commons;

import javax.microedition.rms.*;

// The 'Game Settings' are kept in the persistent record store,
// so they are remembered when the user exits the game
// and restarts it at a later time.
public class Settings {
    private final static int USE_VIBRATION = 1;
    private final static int USE_SOUND = 2;
    private final static int TRUE = 0; // bytes[TRUE] = 'T'
    private final static int FALSE = 1; // bytes[FALSE] = 'F'
    private final static byte[] bytes = { 'T', 'F' };
    private static RecordStore rs = null;
    
    private Settings() {
        // no one else may instantiate us
    }
    
    private static void openRecordStore()
    throws RecordStoreException {
        if (rs == null) {
            rs = RecordStore.openRecordStore("Settings", true);
        }
        if (rs.getNumRecords() == 0) {
            // save a default value for USE_VIBRATION in the record store
            rs.addRecord(bytes, TRUE, 1);
            // save a default value for USE_SOUND in the record store
            rs.addRecord(bytes, TRUE, 1);
        }
    }
    
    private static void closeRecordStore()
    throws RecordStoreException {
        if (rs != null) {
            rs.closeRecordStore();
            rs = null;
        }
    }
    
    public static boolean getUseVibration() {
        try {
            // read the saved value from the record store
            boolean bool = getValue(Settings.USE_VIBRATION);
            return bool;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean getUseSound() {
        try {
            // read the saved value from the record store
            boolean bool = getValue(Settings.USE_SOUND);
            return bool;
        } catch (Exception e) {
            return false;
        }
    }
    
    static void setUseVibration(boolean bool) {
        try {
            // save the value persistently in the record store
            setValue(Settings.USE_VIBRATION, bool);
        } catch (Exception e) {
            // We can not do anything to recover from this error,
            // let the game proceed without using saved settings.
        }
    }
    
    static void setUseSound(boolean bool) {
        try {
            // save the value persistently in the record store
            setValue(Settings.USE_SOUND, bool);
        } catch (Exception e) {
            // We can not do anything to recover from this error,
            // let the game proceed without using saved settings.
        }
    }
    
    private static void setValue(int id, boolean bool)
    throws RecordStoreException {
        openRecordStore();
        if ((id != USE_VIBRATION) && (id != USE_SOUND)) {
            throw new IllegalArgumentException(
                "Settings.set - invalid id: " + id);
        } else {
            if (bool) {
                rs.setRecord(id, bytes, TRUE, 1);
            } else {
                rs.setRecord(id, bytes, FALSE, 1);
            }
        }
        closeRecordStore();
    }

    private static boolean getValue(int id)
    throws RecordStoreException {
        openRecordStore();
        boolean bool = false; // default value
        if ((id != USE_VIBRATION) && (id != USE_SOUND)) {
            closeRecordStore();
            throw new IllegalArgumentException(
                "Settings.get - invalid id: " + id);
        } else {
            byte[] barray = rs.getRecord(id);
            if ((barray != null) && (barray.length == 1)) {
                bool = (barray[0] == bytes[TRUE]);
            }
        }
        closeRecordStore();
        return bool;
    }
}

