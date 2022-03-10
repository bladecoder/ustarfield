package starfield;

import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.*;
import java.util.Hashtable;
import starfield.event.*;

public class GameObject extends Sprite {
    // GameObject Types
    public static final int BACKGROUND = 0;
    public static final int FOREGROUND = 1;
    public static final int ENEMY = 2;
    public static final int PLAYER_BULLET = 3;
    public static final int ENEMY_BULLET = 4;
    public static final int PLAYER = 5;
    
   
    Path path;
    int firedelay;
    int energy;
    int damage;
    public int points;
    int type;
    int delay;
    boolean bulletCollision;
    int loop;
    boolean ping;

    long lastTickFire;
    long lastTickFrame;

    String beginEvent[];
    String fireEvent[];
    String endEvent[];

    public GameObject parent;
    
    EventHandler eh;


    // image cache
    private static Hashtable images = new Hashtable();

    public static Image getImage(String name) throws Exception {
        Image i = (Image)images.get(name);

        if(i == null) {
            try {
                i = Image.createImage(name);
                images.put(name, i);
            } catch (java.io.IOException ioe) {
                throw new Exception("ERROR: loading " + name);
            }
        }

        return i;
    }

    public static GameObject gameObjectFactory(Event e, Engine engine,
            GameObject creator) throws Exception {
        GameObject o, parent = null;
        
        CreateObjectParams p = (CreateObjectParams)e.params;


        Event evObject = (Event) engine.eh.getDefined(p.object);
        Event evPath = (Event) engine.eh.getDefined(p.path);

        DefineObjectParams dop = (DefineObjectParams)evObject.params;
        
        Image img = getImage("/" + dop.imgFile);
        
        o = new GameObject(img, img.getWidth() / dop.width, img.getHeight() / dop.height);

        o.firedelay = dop.firedelay;
        o.energy = dop.energy;
        o.damage = dop.damage;
        o.points = dop.points;
        // type = dop.type; // depends of e.type
        o.beginEvent = dop.beginEvent;
        o.fireEvent = dop.fireEvent;
        o.endEvent = dop.endEvent;
        o.delay = dop.delay;
        o.bulletCollision = dop.bulletCollision;
        o.loop = dop.loop;
        o.eh = engine.eh;
        o.lastTickFire = Timing.getTick();
        o.lastTickFrame = Timing.getTick();
        
        // parent serch
        if(p.parent.equals("*")) {
            parent = creator;
        } else if(!p.parent.equals("")) {
            parent = engine.searchGameObject(p.parent);
        }

        int dx=0, dy=0;

        // ANCHORS
        if(parent != null) {
            if((((DefinePathParams)evPath.params).anchor & 
                        DefinePathParams.VCENTER) != 0)  
                dy = (parent.getHeight() - o.getHeight())/2;
            else if((((DefinePathParams)evPath.params).anchor & 
                        DefinePathParams.TOP) != 0)  
                dy = -o.getHeight();
            else if((((DefinePathParams)evPath.params).anchor & 
                        DefinePathParams.BOTTOM) != 0)  
                dy = parent.getHeight();
          
            if((((DefinePathParams)evPath.params).anchor & 
                        DefinePathParams.HCENTER) != 0) 
                dx = (parent.getWidth() - o.getWidth())/2;
            else if((((DefinePathParams)evPath.params).anchor & 
                        DefinePathParams.RIGHT) != 0) 
                dx = parent.getWidth();
            else if((((DefinePathParams)evPath.params).anchor & 
                        DefinePathParams.LEFT) != 0) 
                dx = -o.getWidth();
        }
        
        o.path = Path.pathFactory(evPath, dx, dy, parent!=null?parent.path:null);
        
        o.update();
        o.begin();

        return o;

    }

    public GameObject(Image image, int frameWidth, int frameHeight) {
        super(image,frameWidth, frameHeight);

        ping = true;
    }

    public void update() {
        path.update();
        setPosition(path.currentX, path.currentY);
        updateAnim();
    }

    public void updateAnim() {
        long actualTick = Timing.getTick();

        if((int)(actualTick - lastTickFrame) >= delay) {
            if(loop == DefineObjectParams.LOOP) nextFrame(); 
            else if(loop == DefineObjectParams.NO_LOOP && 
                    getFrame() != getFrameSequenceLength() - 1 ) nextFrame();
            else if(loop == DefineObjectParams.PINGPONG) {
                if(ping) nextFrame();
                else prevFrame();

                if(ping && getFrame() == getFrameSequenceLength() - 1) ping = false;
                else if(!ping && getFrame() == 0) ping = true;
            }
            
            lastTickFrame = actualTick;
        }
    }

    public void begin() throws Exception {
        if(beginEvent == null) return;

        for(int i = 0; i < beginEvent.length; i++) 
            eh.processDefined(beginEvent[i], this);
    }

    public void fire() throws Exception {
        long actualTick = Timing.getTick();

        if((int)(actualTick - lastTickFire) < firedelay) return;
        //System.out.println("FIRE actualdelay: " + (actualTick - lastTickFire) + " firedelay:" + firedelay);

        lastTickFire = actualTick;
        
        if(fireEvent == null) return;

        for(int i = 0; i < fireEvent.length; i++) 
            eh.processDefined(fireEvent[i], this);
    }

    public void end() throws Exception {
        if(endEvent == null) return;

        for(int i = 0; i < endEvent.length; i++) 
            eh.processDefined(endEvent[i], this);
    }
}
