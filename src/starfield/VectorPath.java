package starfield;

import starfield.event.*;

public class VectorPath extends Path {
    int modv;

    int vx, vy; // vector
    
    public VectorPath(DefinePathParams p, int dx, int dy, Path parent) {
        super(p, dx, dy, parent);

        vx = p.x[1];
        vy = p.y[1];

        // mod(v) = sqrt(x[1]^2 + y[1]^2)
        // aproximated to: mod(v) = |x[1]| + |y[1]|
        modv = Math.abs(vx) + Math.abs(vy);
        modv *= 1000; // modifier to convert milliseconds to seconds
    }
 
    void getNext() {

        if(modv == 0) { // COMMENT this on release
            System.out.println("--->MODV=0");
            return;
        }
        
        currentX = x0 + dt * vx * params.speed / modv;
        currentY = y0 + dt * vy * params.speed / modv;
    }
}
