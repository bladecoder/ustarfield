package starfield;

import starfield.event.*;

public class TargetPlayerPath extends VectorPath {
    static GameObject player;

    public static void setPlayer(GameObject p) { player = p; }

    public TargetPlayerPath(DefinePathParams p, int dx, int dy, Path parent) {
        super(p, dx, dy, parent);

        vx = player.path.currentX - x0;
        vy = player.path.currentY - y0;

        // mod(v) = sqrt(x[1]^2 + y[1]^2)
        // aproximated to: mod(v) = |x[1]| + |y[1]|
        modv = Math.abs(vx) + Math.abs(vy);
        modv *= 1000; // modifier to convert milliseconds to seconds
    }
}
