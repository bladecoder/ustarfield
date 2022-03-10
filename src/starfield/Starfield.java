package starfield;

import java.util.Random;
import javax.microedition.lcdui.Graphics;

public class Starfield {
    int x[];
    int y[];
    int x_slow[];
    int y_slow[];

    int nStars;
    int speed;   // pixels/second
    int speedSlow;
    long tf, t0;
    int dt, d, dSlow;
    static final int color = 0xffffff; // white
    static final int colorSlow = 0x8080a0; // blue
    int width,height;

    public Starfield(int w, int h, int nStars) {
        width = w - 1;
        height = h - 1;
        setSpeed(15);
        setStars(nStars);
    }

    public Starfield(int w, int h) {
        this(w, h, 60);
    }

    void generateStarsPosition() {
        Random r = new Random();

        for(int c = 0; c < nStars; c++) {
            x[c] = r.nextInt() % width;
            y[c] = r.nextInt() % height;
            x_slow[c] = r.nextInt() % width;
            y_slow[c] = r.nextInt() % height;
        }
    }

    void update() {
        tf = Timing.getTick();

        dt = (int)(tf - t0);
        d = dt * speed / 1000;
        dSlow = d / 2;
    }

    void draw(Graphics g) {
        // draw slow stars
        g.setColor(colorSlow);
        for(int c = 0; c < nStars; c++)
            putPixel(g, x_slow[c], (y_slow[c] + dSlow) % height, colorSlow);

        // draw fast stars
        g.setColor(color);
        for(int c=0; c<nStars; c++)
            putPixel(g, x[c], (y[c] + d) % height, color);

    }

    int getStars() {
        return nStars;
    }

    int getSpeed() {
        return speed;
    }

    void setSpeed(int s) {
        speed = s;
        speedSlow = s / 2;
        t0 = Timing.getTick();
    }


    void setStars(int n) {
        nStars = n;

        x = new int [n];
        y = new int [n];
        x_slow = new int [n];
        y_slow = new int [n];

        generateStarsPosition();
    }

    void putPixel(Graphics g, int x, int y, int color) { // TODO OPTIMIZE this
        //g.setColor(color);

        g.drawLine(x, y, x, y);
    }
}
