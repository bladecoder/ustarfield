package starfield;

import starfield.event.*;
import java.util.Random;

public abstract class Path {

    static int scrollSpeed = 15;

    DefinePathParams params;
    Path parent;

    /** current position */
    int currentX, currentY;

    /** anchor position */
    int dx, dy;

    /** initial position */
    int x0, y0;

    long t0, tf, t0s;
    int dt, dts;

    public static Path pathFactory(Event e, int dx, int dy, Path parent) {
        DefinePathParams  params = (DefinePathParams)e.params;
        Path p = null;

        switch(params.type) {
            case DefinePathParams.STATIC:
                p = new StaticPath(params, dx, dy, parent);
            break;
            
            case DefinePathParams.KEYBOARD:
                p = new KeyboardPath(params, dx, dy, parent);
            break;
            
            case DefinePathParams.VECTOR:
                p = new VectorPath(params, dx, dy, parent);
            break;

            case DefinePathParams.TARGETPLAYER:
                p = new TargetPlayerPath(params, dx, dy, parent);
            break;

            case DefinePathParams.RANDOM:
                p = new RandomPath(params, dx, dy, parent);
            break;
            
            case DefinePathParams.FREE:
                p = new FreePath(params, dx, dy, parent);
            break;
            
            case DefinePathParams.FOLLOW:
                p = new FollowPath(params, dx, dy, parent);
            break;
        }

        return p;
    }

 

    /** 
     * Path constructor
     * parent - path parent
     */
    public Path(DefinePathParams params, int dx, int dy, Path parent) {
        Random r = new Random();

        this.params = params;
        this.dx = dx;
        this.dy = dy;
        this.parent = parent;

        x0 = params.x[0];
        y0 = params.y[0];

        if(x0 > 10000) {
            x0 = r.nextInt() % (x0 - 10000);
            if(x0 < 0) x0 = -x0;
        }

        if(y0 > 10000) {
            y0 = r.nextInt() % (y0 - 10000);
            if(y0 < 0) y0 = -y0;
        }
        
        
        x0 = currentX = x0 + dx + (parent!=null?parent.currentX:0);
        y0 = currentY = y0 + dy + (parent!=null?parent.currentY:0);
        t0s = t0 = Timing.getTick();
    }

    void update() {
        tf = Timing.getTick();
        dt = (int)(tf - t0);
        dts = (int)(tf - t0s);
        
        getNext();

        if(params.scrollable) { // add scrollspeed to Y position
            currentY = currentY + (dts * scrollSpeed) / 1000;
        }
    }
    
    void invertPoints() {}
    
    abstract void getNext();

    public static void setScrollSpeed(int s) { scrollSpeed = s; }
}
