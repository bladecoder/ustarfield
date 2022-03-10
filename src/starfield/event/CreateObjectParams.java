package starfield.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;


public class CreateObjectParams implements EventParams {
    public String object;
    public String path;
    public String parent;

    public CreateObjectParams() {
        object = "";
        path = "";
        parent = "";
    }

    
    public void read(DataInputStream dis) throws java.io.IOException {
        object = dis.readUTF();
        path = dis.readUTF();
        parent = dis.readUTF();
    }
    
    public void write(DataOutputStream dos)throws java.io.IOException  {
        dos.writeUTF(object);
        dos.writeUTF(path);
        dos.writeUTF(parent);
    }
}
