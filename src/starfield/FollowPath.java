package starfield;

import starfield.event.*;

public class FollowPath extends Path {
    public FollowPath(DefinePathParams params, int dx, int dy, Path parent) {
        super(params, dx, dy, parent);
    }
 
    void getNext() {
        currentX = parent.currentX + dx;
        currentY = parent.currentY + dy;
    }
}
