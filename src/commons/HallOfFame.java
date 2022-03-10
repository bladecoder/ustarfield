package commons;
import javax.microedition.rms.*;

public class HallOfFame {
    public static final int NUM_ENTRIES = 4;

    int hofScores[];
    String hofNames[];
    private RecordStore rs = null;

    HallOfFame() throws Exception {
        hofScores = new int[NUM_ENTRIES];
        hofNames = new String[NUM_ENTRIES];

        try {
            byte bytes[];

            rs = RecordStore.openRecordStore("hof", true);
            if (rs.getNumRecords() != NUM_ENTRIES * 2) {
                for (int i = 0; i < NUM_ENTRIES; i++) {
                    hofNames[i] = "";
                    bytes = hofNames[i].getBytes();
                    rs.addRecord(bytes, 0, bytes.length);
                    bytes = String.valueOf(hofScores[i]).getBytes();
                    rs.addRecord(bytes, 0, bytes.length);
                }
            }

        } catch (RecordStoreException e) {
            throw new Exception(e.getMessage());
        }

        load();
    }

    void load() throws Exception {
        byte b[];
        
        //System.out.println("Records: " + rs.getNumRecords());
        
        for (int i = 1; i < rs.getNumRecords(); i += 2) {
            b = rs.getRecord(i);
            if(b == null) hofNames[(i - 1) / 2] = "";
            else hofNames[(i - 1) / 2] = new String(b);
            
            b = rs.getRecord(i + 1);
            if(b == null) hofScores[(i - 1) / 2] = 0;
            else hofScores[(i - 1) / 2] = Integer.parseInt(new String(b));
        }
    }

    void save() throws Exception {
        byte bytes[];

        for (int i = 1; i <= NUM_ENTRIES; i++) {
            bytes = hofNames[i - 1].getBytes();
            rs.setRecord(i * 2 - 1, bytes, 0, bytes.length);
            bytes = String.valueOf(hofScores[i - 1]).getBytes();
            rs.setRecord(i * 2, bytes, 0, bytes.length);
        }
    }

    public int getMaxScore() {
        return hofScores[0];
    }

    public int getMinScore() {
        return hofScores[NUM_ENTRIES - 1];
    }

    public void addScore(String name, int score) throws Exception {
        int pos;

        // find score position
        for (pos = 0; pos < NUM_ENTRIES; pos++) {
            if (hofNames[pos] == null)
                break;
            if (hofScores[pos] < score)
                break;
        }

        // shift more little scores
        for (int i = NUM_ENTRIES - 1; i > pos; i--) {
            hofNames[i] = hofNames[i - 1];
            hofScores[i] = hofScores[i - 1];
        }

        hofNames[pos] = name.toUpperCase() ;
        hofScores[pos] = score;

        //System.out.println(toString());

        save();
    }

    public String toString() {
        StringBuffer s = new StringBuffer();

        for (int i = 0; i < NUM_ENTRIES; i++) {
            if (hofNames[i].equals(""))
                break;

            s.append(' ');
            s .append(hofNames[i]);
            
            // append spaces to align names
            for(int j = hofNames[i].length(); j < 15; j++) s.append(' ');
                       
            // append spaces to align numbers
            for(int j = String.valueOf(hofScores[i]).length(); j < 7; j++) s.append(' ');
            
            s.append(hofScores[i]);
            s.append('\n');
        }

        if (hofNames[0].equals(""))
            s.append("  NO SCORES YET");

        return s.toString();
    }
    
    public String getName(int i) {
        return hofNames[i];
    }
    
    public int getScore(int i) {
        return hofScores[i];
    }    
}
