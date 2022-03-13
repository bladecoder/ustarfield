package starfield;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.util.Hashtable;
import java.util.Enumeration;
import java.io.*;
import starfield.event.*;
import commons.*;
import commons.i18n.ResourceBundle;

public class Engine implements EventProcessor, GameManager {

    // Game States
    public final static int TITLE = 0;
    public final static int PLAY = 1;
    public final static int OVER = 2;
    //public final static int DEMO = 3;
    //public final static int PAUSE = 4;
    public final static int WIN = 5;
    //public final static int MENU = 6;
    public final static int HOF = 7;
    public final static int MENU = 8;

    int state;
    Player player;
    GameMIDlet midlet;

    /** If != "" draw text in the middle of screen */
    String centerText = "";

    private int level;
    private int calibration;
    private boolean level_started;
    private boolean done;
    private boolean autofire = false;

    //private int numLevels;

    Screen screen;
    EventHandler eh;
    private Sound sound;

    Hashtable enemies;
    Hashtable playerBullets;
    Hashtable background;
    private Hashtable foreground;
    private static final String levelFile = "/level.evt";

    public Engine(GameMIDlet midlet) throws Exception {
        int n;
        long then;

        this.midlet = midlet;
        screen = new Screen(this);

        reset();

        state = TITLE;

        then = System.currentTimeMillis();
        n = 0;

        for (int i = 0; i < 30000; i++) {
            n++;
        }

        if (n > 0) {
            calibration = (int) (System.currentTimeMillis() - then);
        }

        Thread runner = new Thread(this);

        runner.start();
    }

    void makeCollisions() throws Exception {
        String key1, key2;
        GameObject enemy, bullet;

        // collisions between enemies and player bullets
        for (Enumeration e = enemies.keys(); e.hasMoreElements();) {
            key1 = (String) e.nextElement();
            enemy = (GameObject) enemies.get(key1);

            if (!enemy.bulletCollision)
                continue;

            for (Enumeration e2 = playerBullets.keys();
                e2.hasMoreElements();
                ) {
                key2 = (String) e2.nextElement();
                bullet = (GameObject) playerBullets.get(key2);

                if (enemy.collidesWith((Sprite) bullet, true)) {
                    enemy.energy -= bullet.damage;
                    bullet.energy -= enemy.damage;

                    if (bullet.energy <= 0) {
                        bullet.end();
                        playerBullets.remove(key2);
                    }

                    if (enemy.energy <= 0) {
                        player.points += enemy.points;
                        enemy.end();
                        enemies.remove(key1);
                        break;
                    }
                }
            }
        }

        // collisions between enemies and player
        if (player.isExploded) return;
        for (Enumeration e = enemies.keys(); e.hasMoreElements();) {
            key1 = (String) e.nextElement();
            enemy = (GameObject) enemies.get(key1);

            if (player.collidesWith((Sprite) enemy, true)) {
                enemy.energy -= player.damage;
                player.energy -= enemy.damage;

                if(Settings.getUseVibration());
                    gcc.DeviceControl.startVibra(100, 300L);

                if (enemy.energy <= 0) {
                    player.points += enemy.points;
                    enemy.end();
                    enemies.remove(key1);
                }

                if (player.energy <= 0) {
                    player.lifes--;
                    player.end();

                    if (player.lifes < 0) {
                        state = Engine.OVER;
                        //player = null;
                    } else {
                        player.setVisible(false);
                        player.energy = 100; //TODO FIX:player.params.energy
                    }

                    return;
                }
            }
        }

    }

    void destroyOutOfScreen(Hashtable h, int x, int y, int x2, int y2) {
        GameObject o;
        String key;
        
        for (Enumeration e = h.keys(); e.hasMoreElements();) {
            key = (String) e.nextElement();
            o = (GameObject) h.get(key);

            if (o.getY() < y
                || o.getY() > y2
                || o.getX() < x
                || o.getX() > x2) {
                h.remove(key);
            }
        }
    }

    void destroyEndedBackground() {
        GameObject o;
        String key;

        for (Enumeration e = background.keys(); e.hasMoreElements();) {
            key = (String) e.nextElement();
            o = (GameObject) background.get(key);

            if (o.getFrame() == o.getFrameSequenceLength() - 1
                && o.loop == 0) {
                background.remove(key);
            }
        }
    }

    private void reset() throws Exception {
        level = 0;

        startLevel();
    }

    private DataInputStream getLevel(String level) throws Exception {
        DataInputStream dis = null;

        InputStream is = getClass().getResourceAsStream(level);

        if (is == null) {
            throw new Exception("ERROR: loading level file " + level);
        }

        dis = new DataInputStream(is);

        return dis;
    }

    private void restartLevel() throws Exception {
        synchronized (this) {
            level_started = true;
        }

        //screen.clearKeys();

        try {
            eh = new EventHandler(getLevel(levelFile), this);
        } catch (IOException e) {
            throw new Exception("ERROR: loading level file " + levelFile);
        }

        setScrollSpeed(20);
        Timing.reset();

        enemies = new Hashtable();
        playerBullets = new Hashtable();
        background = new Hashtable();
        foreground = new Hashtable();
        sound = new Sound();
        player = null;

        done = false;
        //autofire = false;
    }

    private void startLevel() throws Exception {
        restartLevel();
    }

/*
    private void nextLevel() throws Exception {
        level++;

        startLevel();
    }
*/
    public void setHof() {
        state = HOF;
    }
    
    void setMenu(boolean isGameOver) {        
        if (isGameOver) {
              screen.menu.deleteContinue();
          } else {
              //gameManager.pause();
              screen.menu.selectContinue();
          }
          
        state = MENU;
    }
    
    GraphicsMenu getMenu() {
        return screen.menu;
    }
    
    public void toggleAutofire() {       
        if (autofire) autofire = false;
        else autofire = true;     
    }

    public synchronized boolean levelStarted() {
        boolean x = level_started;

        level_started = false;

        return x;
    }

    public synchronized void start() {
        try {
            reset();
        } catch (Exception e) {
            midlet.showErrorMsg(e.getMessage());
        }

        state = PLAY;
    }

    public synchronized void stop() {
        done = true;
    }

    public void pause() {
        Timing.setPause(true);
    }

    public void resume() {
        state = PLAY;
        Timing.setPause(false);

        // ugly fix, breaks midp2.0 compatibility
        //screen.clearKeys();
    }

    public Canvas getCanvas() {
        return screen;
    }

    public void run() {
        long then;
        int keyStates, delta;

        then = System.currentTimeMillis();

        while (!done) {
            try {
                if (state == PLAY) {
                    // 1.- process new events
                    eh.processEvents();

                    // 2.- destroy all objects that are out of the limits
                    destroyOutOfScreen(
                        playerBullets,
                        -50,
                        -50,
                        Screen.width + 50,
                        Screen.height + 50);

                    destroyOutOfScreen(
                        enemies,
                        -200,
                        -200,
                        Screen.width + 200,
                        Screen.height + 200);

                    // 4.- collision detection
                    makeCollisions();

                    // 6.- movement and fire keys
                    keyStates = screen.getKeyStates();
                    KeyboardPath.setKeys(keyStates);
                    if ((autofire || (keyStates & GameCanvas.FIRE_PRESSED) != 0)
                        && player != null
                        && player.isVisible())
                        player.fire();

                }

                screen.drawFrame();

                if (state == PLAY)
                    destroyEndedBackground();

                delta = (int) (System.currentTimeMillis() - then);

                if (delta < 30 && calibration < 100) {
                    try {
                        Thread.sleep(30 - delta);
                    } catch (InterruptedException e) {
                    }
                }

                then = System.currentTimeMillis();
            } catch (Exception e) {
                String msg = e.getMessage();
                if(msg == null) msg = e.toString();
                e.printStackTrace();
                state = TITLE;
                midlet.showErrorMsg(msg);
            }
        }
    }

    public GameObject searchGameObject(String name) {
        GameObject o;

        o = (GameObject) enemies.get(name);
        if (o != null)
            return o;

        o = (GameObject) playerBullets.get(name);
        if (o != null)
            return o;

        if (name.equals("player"))
            return (GameObject) player;

        // TODO  searching in background and foreground

        return null;
    }
    
    public String searchGameObjectKey(GameObject o) {
        String key;
        
        for (Enumeration e = enemies.keys(); e.hasMoreElements();) {
                    key = (String) e.nextElement();
                    if((GameObject) enemies.get(key) == o) return key;
        }
        
        return null;
    }

    public void destroyGameObject(String name, Object creator) throws Exception {
        GameObject o;
        
        if(name.equals("*")) name = searchGameObjectKey((GameObject)creator);

        o = (GameObject) enemies.get(name);

        if (o != null) {
            o.end();
            enemies.remove(name);
        }

        // TODO searching in playerBullets, background and foreground
    }

    void setScrollSpeed(int s) {
        screen.starfield.setSpeed(s);
        Path.setScrollSpeed(s);
    }

    public void processEvent(Event e, Object creator) throws Exception {
        switch (e.type) {
            case Event.ENEMY :
                //System.out.println("ENEMY: " + e.name);
                enemies.put(
                    e.name,
                    GameObject.gameObjectFactory(
                        e,
                        this,
                        (GameObject) creator));
                break;
            case Event.PLAYER :
                //System.out.println("PLAYER: " + e.name);
                player = Player.playerFactory(e, this, (GameObject) creator);
                player.points = 0;
                TargetPlayerPath.setPlayer((GameObject) player);
                break;
            case Event.PLAYERBULLET :
                //System.out.println("PLAYERBULLET: " + e.name);
                playerBullets.put(
                    e.name,
                    GameObject.gameObjectFactory(
                        e,
                        this,
                        (GameObject) creator));
                break;
            case Event.BACKGROUND :
                //System.out.println("BACKGROUND: " + e.name);
                background.put(
                    e.name,
                    GameObject.gameObjectFactory(
                        e,
                        this,
                        (GameObject) creator));
                break;

            case Event.END :
                System.out.println("END: " + e.name);
                state = WIN;
                break;

            case Event.LOAD :
                System.out.println("Loading: " + e.name);
                eh.load(getLevel(e.name));
                break;

            case Event.SPEED :
                GameObject o =
                    searchGameObject(((ObjectPropParams) e.params).object);
                if (o != null)
                    o.path.params.speed += ((ObjectPropParams) e.params).value;
                break;
                
            case Event.LIFE :
                player.lifes += ((ObjectPropParams) e.params).value;
                break;
               
            case Event.ENERGY :
                GameObject o2 =
                    searchGameObject(((ObjectPropParams) e.params).object);
                if (o2 != null) {
                    o2.energy += ((ObjectPropParams) e.params).value;
                    if (o2.energy <= 0) {
                        o2.end();
                        enemies.remove(((ObjectPropParams) e.params).object);
                    }

                    // More energy that 100 not allow
                    if(o2.energy > 100) o2.energy = 100;
                }
                break;

            case Event.SOUND :
                //System.out.println("Playing: " + e.name);
                sound.playSound(e.name);
                break;

            case Event.DESTROY :
                System.out.println(
                    "Destroying: " + ((ObjectPropParams) e.params).object);
                destroyGameObject(((ObjectPropParams) e.params).object, creator);
                break;

            case Event.TEXT:
                centerText = ResourceBundle.getString("i18n.GameTexts", e.name);
                break;

            case Event.FIRE:
                GameObject obj =
                    searchGameObject(((ChangeEventParams) e.params).object);
                if (obj != null)
                    obj.fireEvent = ((ChangeEventParams) e.params).event;
                break;
        }
    }
}
