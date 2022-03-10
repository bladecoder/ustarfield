package starfield;

/*
 * Must control sprite sequences, player explosion,
 * screen fit..
 */
import javax.microedition.lcdui.*;
import starfield.event.*;

public class Player extends GameObject {
    static final int LIFES = 2;
    //static final int LIFES = 20;
    int lifes;
    boolean isExploded;
    long explodedTick;

    int seq1[] = {0,1,2,3,4};
    int seq2[] = {4,3,2,1,0};
    int seq3[] = {5,6,7,8,9};
    int seq4[] = {9,8,7,6,5};
    int seq5[] = {4,3,2,1,5,6,7,8,9};
    int seq6[] = {9,8,7,6,5,1,2,3,4};

    public Player(Image image, int frameWidth, int frameHeight) {
        super(image,frameWidth, frameHeight);

        setFrameSequence(seq2);
        explodedTick=lastTickFrame=lastTickFire = Timing.getTick();
        isExploded = false;
        lifes = LIFES;
    }
    
    public static Player playerFactory(Event e, Engine engine,
            GameObject creator) throws Exception {
        GameObject parent = null;
        Player o;
        
        CreateObjectParams p = (CreateObjectParams)e.params;


        Event evObject = (Event) engine.eh.getDefined(p.object);
        Event evPath = (Event) engine.eh.getDefined(p.path);

        DefineObjectParams dop = (DefineObjectParams)evObject.params;
        
        Image img = getImage("/" + dop.imgFile);
        
        o = new Player(img, img.getWidth() / dop.width, img.getHeight() / dop.height);

        o.firedelay = dop.firedelay;
        o.energy = dop.energy;
        o.damage = dop.damage;
        o.points = dop.points;
        // type = dop.type; // depends of e.type
        o.beginEvent = dop.beginEvent;
        o.fireEvent = dop.fireEvent;
        o.endEvent = dop.endEvent;
        o.delay = dop.delay;
        o.loop = dop.loop;
        o.eh = engine.eh;
        
        // parent serch
        if(p.parent.equals("*")) {
            parent = creator;
        } else if(!p.parent.equals("")) {
            parent = engine.searchGameObject(p.parent);
        }

        if(parent != null) {
            o.path = Path.pathFactory(evPath, 0, 0, creator.path);
        } else {
            o.path = Path.pathFactory(evPath, 0, 0, null);
        }
        
        o.update();
        o.begin();

        return o;

    }

    public void update() {
        // sequence change
        KeyboardPath p = (KeyboardPath)path;
        long actualTick = Timing.getTick();

        if(!isVisible()&&!isExploded) {
            if(actualTick - lastTickFrame > 1000) {
                setVisible(true);
                explodedTick = actualTick;
                isExploded = true;
            } else return;
        }
        
        if(isExploded) { 
            if(actualTick - explodedTick < 2000) {
                if(actualTick%600 > 350) setVisible(true);
                else setVisible(false);
            } else {
                setVisible(true);
                isExploded = false;
            }
        }
    
        if(p.lastvx != KeyboardPath.vx) {
            if(p.lastvx == 0 && KeyboardPath.vx == 1) setFrameSequence(seq3);
            else if(p.lastvx == 1 && KeyboardPath.vx == 0) setFrameSequence(seq4);
            else if(p.lastvx == -1 && KeyboardPath.vx == 0) setFrameSequence(seq2);
            else if(p.lastvx == 0 && KeyboardPath.vx == -1) setFrameSequence(seq1);
            else if(p.lastvx == 1 && KeyboardPath.vx == -1) setFrameSequence(seq6);
            else if(p.lastvx == -1 && KeyboardPath.vx == 1) setFrameSequence(seq5);
        }
        
        path.update();
        
        // Screen exit detection.
        int w =  DefinePathParams.screenWidth;
        int h = DefinePathParams.screenHeight;

        if(path.currentX < 0) path.currentX = 0;
        else if(path.currentX > w - getWidth()) path.currentX = w - getWidth();
        
        if(path.currentY < 0) path.currentY = 0;
        else if(path.currentY > h - getHeight()) path.currentY = h - getHeight();

        setPosition(path.currentX, path.currentY);

        // NEXT FRAME if time
        updateAnim();
    }

}
