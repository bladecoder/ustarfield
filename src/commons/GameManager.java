package commons;

import javax.microedition.lcdui.Canvas;

public interface GameManager extends Runnable {

    public Canvas getCanvas();
    public void pause();
    public void resume();
    public void stop();
    public void start();
}
