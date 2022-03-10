package starfield;

import javax.microedition.lcdui.game.GameCanvas;
import starfield.event.DefinePathParams;

public class KeyboardPath extends Path {
    static int vx, vy;

    int lastvx, lastvy;

    int modv;

    public KeyboardPath(DefinePathParams p, int dx, int dy, Path parent) {
        super(p, dx, dy, parent);

        vx = 0;
        vy = 0;
        lastvx = 0;
        lastvy = 0;
    }
   
    void getNext() {

        if(vx != lastvx || vy != lastvy) {
            lastvx = vx;
            lastvy = vy;
            t0 = Timing.getTick();
            x0 = currentX;
            y0 = currentY;
        }

        if(vx == 0 && vy == 0) return;

        dt = (int)(tf - t0);

        //System.out.println("dt:" + dt + " speed:" + params.speed);
        currentX = x0 + dt * vx * params.speed / 1000;
        currentY = y0 + dt * vy * params.speed / 1000;

       
    }

    public static void setKeys(int state) {
        if ((state & GameCanvas.LEFT_PRESSED) != 0) {
            vx = -1;
        } else if ((state & GameCanvas.RIGHT_PRESSED) != 0) {
            vx = 1;
        } else vx = 0;
        
        if ((state & GameCanvas.UP_PRESSED) != 0) {
            vy = -1;
        } else if ((state & GameCanvas.DOWN_PRESSED) != 0) {
            vy = 1;
        } else vy = 0;

    }
}
