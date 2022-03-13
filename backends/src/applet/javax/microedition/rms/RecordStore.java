package javax.microedition.rms;

import java.util.*;

public class RecordStore {
    static Hashtable records = new Hashtable();

    Vector rs = null;

    public int addRecord(byte[] data, int offset, int numBytes)
              throws RecordStoreNotOpenException,
                     RecordStoreException,
                     RecordStoreFullException {
        byte data2[] = new byte[numBytes];
        System.arraycopy(data, offset, data2, 0, numBytes);
        rs.addElement(data2);
        return rs.size();
    }

    public void closeRecordStore()
                      throws RecordStoreNotOpenException,
                             RecordStoreException {
        rs = null;
    }


    public int getNumRecords()
        throws RecordStoreNotOpenException {
        if(rs == null) throw new RecordStoreNotOpenException();
        return rs.size();
    }

    public byte[] getRecord(int recordId)
                 throws RecordStoreNotOpenException,
                        InvalidRecordIDException,
                        RecordStoreException {
        return (byte[])rs.elementAt(recordId-1);
    }


    public static RecordStore openRecordStore(String recordStoreName,
                                          boolean createIfNecessary)
                                   throws RecordStoreException,
                                          RecordStoreFullException,
                                          RecordStoreNotFoundException {
        
        RecordStore store = new RecordStore();
        store.rs = (Vector)records.get(recordStoreName);
        if(store.rs == null) {
            if (createIfNecessary) {
                store.rs = new Vector();
                records.put(recordStoreName, store.rs);
            } else {
                throw new RecordStoreNotFoundException();
            }
        }
        
        return store;
    }

    public void setRecord(int recordId, byte[] newData, int offset,
                      int numBytes)
               throws RecordStoreNotOpenException,
                      InvalidRecordIDException,
                      RecordStoreException,
                      RecordStoreFullException {

        byte data2[] = new byte[numBytes];
        System.arraycopy(newData, offset, data2, 0, numBytes);

        if(rs == null) throw new RecordStoreNotOpenException();

        rs.setElementAt(data2, recordId-1);

    }
}
