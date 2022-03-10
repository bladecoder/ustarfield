package starfield.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;


public class DefineObjectParams implements EventParams {
    // loop types
    public static final int NO_LOOP = 0;
    public static final int LOOP = 1;
    public static final int PINGPONG = 2;
 
    public String  imgFile;
    public String  beginEvent[];
    public String  fireEvent[];
    public String  endEvent[];
    public int     width,height;
    public int     delay;
    public boolean bulletCollision;
    public int     loop;
    public int     energy;
    public int     damage;
    public int     points;
    public int     firedelay;

    public DefineObjectParams() {
        imgFile = "";
        width = height = 0;
        delay = 0;
        loop = NO_LOOP;
        energy = 100;
        damage = 100;
        points = 100;
        firedelay = 500;
        bulletCollision = true;
    }


    public void read(DataInputStream dis) throws java.io.IOException {
        int numEvents;
        
        imgFile = dis.readUTF();
        
        numEvents = dis.readInt();
        if(numEvents != 0) {
            beginEvent = new String[numEvents];
            for(int i = 0; i < numEvents; i++)
                beginEvent[i] = dis.readUTF();
        }

        numEvents = dis.readInt();
        if(numEvents != 0) {
            fireEvent = new String[numEvents];
            for(int i = 0; i < numEvents; i++)
                fireEvent[i] = dis.readUTF();
        }
       
        numEvents = dis.readInt();
        if(numEvents != 0) {
            endEvent = new String[numEvents];
            for(int i = 0; i < numEvents; i++)
                endEvent[i] = dis.readUTF();
        }
       
        width = dis.readInt();
        height = dis.readInt();
        delay = dis.readInt();
        loop = dis.readInt();
        energy = dis.readInt();
        damage = dis.readInt();
        points = dis.readInt();
        firedelay = dis.readInt();
        bulletCollision = dis.readBoolean();
    }

    public void write(DataOutputStream dos) throws java.io.IOException {
        dos.writeUTF(imgFile);

        if(beginEvent == null) dos.writeInt(0);
        else {
            dos.writeInt(beginEvent.length);
            for(int i=0; i < beginEvent.length; i++)
                dos.writeUTF(beginEvent[i]);
        }
        
        if(fireEvent == null) dos.writeInt(0);
        else {
            dos.writeInt(fireEvent.length);
            for(int i=0; i < fireEvent.length; i++)
                dos.writeUTF(fireEvent[i]);
        }
         
        if(endEvent == null) dos.writeInt(0);
        else {
            dos.writeInt(endEvent.length);
            for(int i=0; i < endEvent.length; i++)
                dos.writeUTF(endEvent[i]);
        }
     
        dos.writeInt(width);
        dos.writeInt(height);
        dos.writeInt(delay);
        dos.writeInt(loop);
        dos.writeInt(energy);
        dos.writeInt(damage);
        dos.writeInt(points);
        dos.writeInt(firedelay);
        dos.writeBoolean(bulletCollision);
    }
}
