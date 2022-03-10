/*
 * Created on 06-oct-2003
 */
package starfield;

/**
 * @author rgarcia
 */
public class Timing {
    private static long currentTick = System.currentTimeMillis();
    
    /** Counts the number of ticks in pause */
    private static long pauseTicks = 0;
    
    private static long bebinPauseTick = 0;
    
    private static boolean pause = false;
    
    public static void setPause(boolean p) {
        pause = p;
        
        if(!pause) pauseTicks += System.currentTimeMillis() - bebinPauseTick;
        else bebinPauseTick = System.currentTimeMillis(); 
    }
    
    public static boolean isPaused() {
        return pause;
    }

    public static long getTick() {
        if(!pause)
            currentTick = System.currentTimeMillis() - pauseTicks;
        
        return currentTick;
    }
    
    public static void reset() {
        pauseTicks = 0;
        bebinPauseTick = 0;
        pause = false;
    }
}
