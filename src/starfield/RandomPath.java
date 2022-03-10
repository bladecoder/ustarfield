package starfield;

import java.util.Random;
import starfield.event.*;

public class RandomPath extends Path {
    int modv;

    int vx, vy; // vector

    Random r;
    
    public RandomPath(DefinePathParams p, int dx, int dy, Path parent) {
        super(p, dx, dy, parent);

        r = new Random();
        
        vx = r.nextInt() % 10;
        vy = r.nextInt() % 10;

        modv = (Math.abs(vx) + Math.abs(vy)) * 1000;
    }
 
    void getNext() {
        if(dt > params.x[1]) {
            vx = r.nextInt() % 10;
            vy = r.nextInt() % 10;
            t0 = Timing.getTick();
            dt = (int) (tf - t0);
            x0 = currentX;
            y0 = currentY;
            
            // random path mustn't exit from screen
            // only when scrollable
            int w =  DefinePathParams.screenWidth-20;
            int h = DefinePathParams.screenHeight-20;
            if(x0 < 0 && vx < 0) vx = -vx;
            if(y0 < 0 && vy < 0) vy = -vy;
            if(x0 > w && vx > 0) vx = -vx;
            if(y0 > h && vy > 0) vy = -vy;
            modv = (Math.abs(vx) + Math.abs(vy)) * 1000;
        }
        
        if(modv == 0) return;

        currentX = x0 + dt * vx * params.speed / modv;
        currentY = y0 + dt * vy * params.speed / modv;
    }
}
