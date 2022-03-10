package starfield.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;


public class DefinePathParams implements EventParams {

    // Path types
    public static final int STATIC = 0;
    public static final int VECTOR = 1;
    public static final int LINE = 2;
    public static final int CIRCLE = 3;
    public static final int ELLIPSE = 4;
    public static final int FREE = 5;
    public static final int KEYBOARD = 6;
    public static final int SINHOR = 7;
    public static final int SINVERT = 8;
    public static final int TARGETPLAYER = 9;
    public static final int RANDOM = 10;
    public static final int FOLLOW = 11;

    // Loop types
    public static final int NO_LOOP = 0;
    public static final int LOOP = 1;
    public static final int PINGPONG = 2;

    // Anchors
    public static final int TOP = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 4;
    public static final int RIGHT = 8;
    public static final int VCENTER = 16;
    public static final int HCENTER = 32;

    // Path points are saved with a 100x100 screen size.
    // Real screen dimensions are necessary to scale points.
    public static int screenWidth, screenHeight;

    public int     type;
    public int     speed;
    public int     nPoints;
    public int     x[];
    public int     y[];
    public int     loop;
    public boolean scrollable;
    public boolean percent[];
    public int anchor;

    public DefinePathParams() {
        type = 0;
        speed = 4;
        nPoints = 0;
        loop = 0;
        scrollable = false;
        anchor = 0;
    }

    public void read(DataInputStream dis) throws java.io.IOException {
        type = dis.readInt();
        speed = dis.readInt();
        loop = dis.readInt();
        scrollable = dis.readBoolean();

        nPoints = dis.readInt();

        x = new int[nPoints];
        y = new int[nPoints];
        percent = new boolean[nPoints];
        //System.out.println("path npoints: " + nPoints);


        for(int i=0; i < nPoints; i++) {
            percent[i] = dis.readBoolean();
            x[i] = dis.readInt();
            y[i] = dis.readInt();

            if(percent[i]) { 
                x[i] = x[i] * screenWidth / 100;
                y[i] = y[i] * screenHeight / 100;
            }

            //System.out.println(x[i] + " , " + y[i]);
        }

        percent = null; // free percent array

        anchor = dis.readInt();
    }

    public void write(DataOutputStream dos) throws java.io.IOException {
        dos.writeInt(type);
        dos.writeInt(speed);
        dos.writeInt(loop);
        dos.writeBoolean(scrollable);

        dos.writeInt(nPoints);

        for(int i=0; i < nPoints; i++) {
            dos.writeBoolean(percent[i]);
            dos.writeInt(x[i]);
            dos.writeInt(y[i]);
        }

        dos.writeInt(anchor);
    }
}
