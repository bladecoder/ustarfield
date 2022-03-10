package starfield;

import starfield.event.*;

public class FreePath extends Path {

    int endPoint;
    int vx,vy,modv;
    boolean forward;
    
    public FreePath(DefinePathParams p, int dx, int dy, Path parent) {
        super(p, dx, dy, parent);
        endPoint = 1;

        vx = p.x[1] - p.x[0];
        vy = p.y[1] - p.y[0];

        modv = Math.abs(vx) + Math.abs(vy);
        modv *= 1000;
        forward = true;
    }
 
    void getNext() {

        if(modv == 0) { // COMMENT this on release
            System.out.println("--->MODV=0");
            return;
        }
        
        currentX = x0 + dt * vx * params.speed / modv;
        currentY = y0 + dt * vy * params.speed / modv;

        // check if is time of the next point
        if((vx > 0 && params.x[endPoint] - currentX <= 0) ||
           (vx < 0 && params.x[endPoint] - currentX > 0) ||
           (vy > 0 && params.y[endPoint] - currentY <= 0) ||
           (vy < 0 && params.y[endPoint] - currentY > 0) ) {

            if(forward && endPoint == params.nPoints - 1) {
                if(params.loop == DefinePathParams.NO_LOOP) return;
                else if(params.loop == DefinePathParams.PINGPONG) forward = false;
            } else if(!forward && endPoint == 0 && 
                    params.loop == DefinePathParams.PINGPONG) forward = true;
            
            x0 = currentX;
            y0 = currentY;
            
            if(forward) endPoint = (endPoint + 1) % params.nPoints;
            else endPoint--;
            
            vx = params.x[endPoint] - currentX;
            vy = params.y[endPoint] - currentY;
            t0 = Timing.getTick();
            modv = Math.abs(vx) + Math.abs(vy);
            modv *= 1000;
        }
    }
}
