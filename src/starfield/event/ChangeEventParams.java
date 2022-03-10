package starfield.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;


public class ChangeEventParams implements EventParams {
    public String object;
    public String event[];

    public ChangeEventParams() {
        object = "";
    }

    
    public void read(DataInputStream dis) throws java.io.IOException {
        int numEvents;
        
        numEvents = dis.readInt();
        if(numEvents != 0) {
            event = new String[numEvents];
            for(int i = 0; i < numEvents; i++)
                event[i] = dis.readUTF();
        }

        object = dis.readUTF();
    }
    
    public void write(DataOutputStream dos)throws java.io.IOException  {
        if(event == null) dos.writeInt(0);
        else {
            dos.writeInt(event.length);
            for(int i=0; i < event.length; i++)
                dos.writeUTF(event[i]);
        }

        dos.writeUTF(object);
    }
}
