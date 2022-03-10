package starfield.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Event {  
    public int wait;
    public int type;
    public String name;
    public boolean defined;

    public EventParams params;
    

    // Event TYPES
    public static final int DEFINEOBJECT = 0;
    public static final int DEFINEPATH = 1;
    public static final int ENEMY = 2;
    public static final int PLAYER = 3;
    public static final int PLAYERBULLET = 4;
    public static final int END = 5;
    public static final int BACKGROUND = 6;
    public static final int LOAD = 7;
    public static final int SPEED = 8;
    public static final int SOUND = 9;
    public static final int DESTROY = 10;
    public static final int TEXT = 11;
    public static final int FIRE = 12;
    public static final int ENERGY = 13;
    public static final int LIFE = 14;
    public static final int PAUSE = 15;
    public static final int CONTINUE = 16;

    public Event() {
        wait = 0;
        name = "";
        defined = false;
    }
    
    public Event(int type) {
        wait = 0;
        name = "noname"; // FIX: autogenerate
        defined = false;
        this.type = type;

        createParams();
    }

    public Event(Event e, String newName) {
        type = e.type;
        wait = e.wait;
        name = newName;
        defined = e.defined;
        params = e.params;
    }

    void createParams() {
        switch(type) {
            case DEFINEOBJECT: 
                defined = true;
                params = new DefineObjectParams();
                break;
            case DEFINEPATH: 
                defined = true;
                params = new DefinePathParams();
                break;
            case ENEMY: 
            case PLAYER: 
            case PLAYERBULLET: 
            case BACKGROUND: 
                params = new CreateObjectParams();
                break;
            case SPEED:
            case DESTROY:
            case ENERGY:
            case LIFE:
                params = new ObjectPropParams();
                break;
            case FIRE:
                params = new ChangeEventParams();
                break;
        }
    }
 
    public void read(DataInputStream dis) throws java.io.IOException {
        name = dis.readUTF();
        type = dis.readInt();
        wait = dis.readInt();
        defined = dis.readBoolean();
        createParams();
        if(params != null) params.read(dis);
    }

    public void write(DataOutputStream dos) throws java.io.IOException {
        dos.writeUTF(name);
        dos.writeInt(type);
        dos.writeInt(wait);
        dos.writeBoolean(defined);
        if(params != null) params.write(dos);
    }
   
}
