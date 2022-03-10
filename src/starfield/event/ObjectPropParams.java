package starfield.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;


public class ObjectPropParams implements EventParams {
    public String object;
    public int value;

    public ObjectPropParams() {
        object = "";
        value = 0;
    }

    
    public void read(DataInputStream dis) throws java.io.IOException {
        object = dis.readUTF();
        value = dis.readInt();
    }
    
    public void write(DataOutputStream dos)throws java.io.IOException  {
        dos.writeUTF(object);
        dos.writeInt(value);
    }
}
