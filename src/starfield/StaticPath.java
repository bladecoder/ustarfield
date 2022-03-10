package starfield;

import starfield.event.*;

public class StaticPath extends Path {
    public StaticPath(DefinePathParams p, int dx, int dy, Path parent) {
        super(p, dx, dy, parent);
    }
    
    void getNext() {
        currentX = x0;
        currentY = y0;
    }
}
