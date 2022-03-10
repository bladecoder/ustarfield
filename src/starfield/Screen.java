package starfield;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.util.*;
import starfield.event.*;
import commons.HallOfFame;
import commons.SpriteFont;
import commons.i18n.ResourceBundle;

public class Screen extends GameCanvas {
    static int width;
    static int height;
    private static final int BACKGROUND = 0x000000; // BLACK
    private Engine engine;
    private long last;
    private int fps;
    private String fps_string;
    private boolean show_fps;
    private String sPause;

    Starfield starfield;
    
    private SpriteFont font;

    private Image sf;
    //private Image gameover;
    private long last_key_press;
    
    GraphicsMenu menu;
    
    public Screen(Engine engine) throws Exception {
        super(false);

        this.engine = engine;
        width = getWidth();
        height = getHeight();
  
        fps_string = "0";
        show_fps = false;

        starfield = new Starfield(width - 1, height - 1);
        DefinePathParams.screenWidth = width;
        DefinePathParams.screenHeight = height;
        sPause = ResourceBundle.getString("commons.i18n.CommonTexts", "PAUSE");

        last_key_press = System.currentTimeMillis();

        try {
            sf = Image.createImage("/starfield-alien.png");
            //gameover = Image.createImage("/gameover.png");
            Image font_metal = Image.createImage("/font_metal.png");
            font = new SpriteFont(font_metal, font_metal.getWidth()/11, font_metal.getHeight()/4);
            
            Image bomb = Image.createImage("/bomb.png");
            Sprite selector = new Sprite(bomb, bomb.getWidth()/2, bomb.getHeight());
            
            menu = new GraphicsMenu(engine.midlet, font, selector, sf);
        } catch (java.io.IOException ioe) {
            throw new Exception("ERROR: loading init screens");
        }
    }

    public void keyPressed(int keycode) {
        super.keyPressed(keycode);
        int game_action = getGameAction(keycode);
        
        if(engine.state == Engine.MENU) {
          menu.keyPressed(game_action);
          return;
        }
        
        if(engine.state == Engine.HOF) {
            engine.midlet.showMenu(false);
            return;
        }

        //if (key_code == Canvas.KEY_NUM1) {
        if (game_action == Canvas.GAME_D) {
            setShowFPS(!isShowFPS());
            return;
        }

        //if (key_code == Canvas.KEY_NUM7 || game_action == Canvas.GAME_C) {
        if (game_action == Canvas.GAME_A) {
            if (Timing.isPaused())
                Timing.setPause(false);
            else
                Timing.setPause(true);
            return;
        }
        
        if (game_action == Canvas.GAME_C) {
           engine.toggleAutofire();
           // Para TEST del juego: pulsando 'C' añadimos una vida
           //engine.player.lifes++;
            return;
        }

        if (engine.state == Engine.OVER || engine.state == Engine.WIN) {
            if (System.currentTimeMillis() - last_key_press < 2000)
                return;
            else {
                int min = engine.midlet.hof.getMinScore();

                if (min < engine.player.points) {
                    engine.midlet.showNameEditor(engine.player.points);
                    engine.player = null;
                }

                engine.state = Engine.TITLE;
                return;
            }
        }

        last_key_press = System.currentTimeMillis();

        if (engine.state == Engine.TITLE) {
            engine.midlet.showMenu(true);
        //} else if (key_code == Canvas.KEY_NUM9) {
        //} else if (game_action == Canvas.GAME_B) {
        } else if ((getKeyStates() & GameCanvas.GAME_B_PRESSED) != 0) {
            engine.pause();
            engine.midlet.showMenu(false);
        }
    } 

    public void keyReleased(int keycode) {
        super.keyReleased(keycode);
    } 

    public boolean isShowFPS() {
        return show_fps;
    } 

    public void setShowFPS(boolean x) {
        show_fps = x;
    } 

    private void paintBackground(Graphics g) {
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, width, height);
        
        starfield.draw(g);
    } 

    private void paintTitle(Graphics g) {
        g.drawImage(sf, width/2, (height - sf.getHeight())/2, 
                Graphics.HCENTER|Graphics.TOP);
    }
    
    private void paintHof(Graphics g) {

         for (int i = 0; i < HallOfFame.NUM_ENTRIES; i++) {
             String name = engine.midlet.hof.getName(i);
             int score = engine.midlet.hof.getScore(i);
             if (name.equals(""))  break;
             
             font.paint(g, name, font.getWidth(), (font.getHeight()+15) * (i + 1) ,
                                         SpriteFont.TOP | SpriteFont.LEFT);
             
             font.paint(g, String.valueOf(score), getWidth() - font.getWidth(), 
                                        (font.getHeight() + 15) * (i + 1) ,
                                        SpriteFont.TOP | SpriteFont.RIGHT);
         }

         if (engine.midlet.hof.getName(0).equals(""))
            font.paint(g, "NO SCORES YET", width / 2, height / 2,
                            SpriteFont.HCENTER | SpriteFont.VCENTER);         
    }

    private void paintOver(Graphics g) {
        font.paint(g, "GAME OVER", width / 2, height / 2,
                    SpriteFont.HCENTER | SpriteFont.VCENTER); 

        //g.drawImage(gameover, width/2, (height - gameover.getHeight())/2, 
        //        Graphics.HCENTER|Graphics.TOP);
    } 

    private void paintYouWin(Graphics g) {
        font.paint(g, "GAME", width / 2 , height / 2 - 30,
                    SpriteFont.HCENTER | SpriteFont.VCENTER); 
        font.paint(g, "COMPLETED", width / 2 , height / 2 - 10,
                    SpriteFont.HCENTER | SpriteFont.VCENTER); 

        font.paint(g, "YOU WIN", width / 2, height / 2 + 10,
                    SpriteFont.HCENTER | SpriteFont.VCENTER); 
    } 
    
    private void paintScore(Graphics g) {       
        // draw score
        font.paint(g, ""+engine.player.points, width, 0, SpriteFont.TOP | SpriteFont.RIGHT);
        
        // draw energy: engine.player.energy
        int pos = font.getWidth() + 5;
        g.setColor(0x00ff00);
        g.drawRect(pos,0,51,5);
        if(engine.player.energy > 30) g.setColor(0x00aa00);
        else g.setColor(0xaa0000);
        g.fillRect(pos+1,1,engine.player.energy>>1,4);
       
        // draw player lifes
        font.paint(g, ""+engine.player.lifes, 0, 0, 0);
    }

    private void paintFPS(Graphics g) {
        fps++;

        long n = System.currentTimeMillis() / 1000;

        if (n != last) {
            fps_string = "F:" + fps + " E:" + engine.enemies.size() + 
                " B:" + engine.playerBullets.size();
            fps = 0;
            last = n;
        } 

        font.paint(g, fps_string, width, height, SpriteFont.BOTTOM | SpriteFont.RIGHT);
    }

    void drawGameObjets(Hashtable h, Graphics g) throws Exception {
        GameObject o;
        
        for (Enumeration e = h.elements() ; e.hasMoreElements() ; ) {
            o = (GameObject)e.nextElement();
            o.paint(g);

            if(!Timing.isPaused()) {
                o.update();
                o.fire();
            }
        }
    }

    public void drawFrame() throws Exception {

        Graphics g = getGraphics();

        paintBackground(g);
        
        starfield.update();

        if (engine.state == Engine.TITLE) {
            paintTitle(g);
        } else if (engine.state == Engine.WIN) {
            paintYouWin(g);
        } else if (engine.state == Engine.OVER) {
            paintOver(g);
        } else if (engine.state == Engine.HOF) {
            paintHof(g);          
        } else if (engine.state == Engine.MENU) {
            menu.paint(g);
        } else {
            drawGameObjets(engine.background, g);
            drawGameObjets(engine.playerBullets, g);
            drawGameObjets(engine.enemies, g);
            
            // draw player
            if(engine.player != null) {
                engine.player.paint(g);
                if(!Timing.isPaused()) engine.player.update();
                paintScore(g);
            }

            if(Timing.isPaused()) {
                font.paint(g, sPause, width / 2, height / 2,
                    SpriteFont.HCENTER | SpriteFont.VCENTER); 
            } else if(engine.centerText.length() != 0){
                font.paint(g, engine.centerText, width / 2, height / 2,
                    SpriteFont.HCENTER | SpriteFont.VCENTER);
            }

        } 
        
        if (show_fps) paintFPS(g); 

        flushGraphics();
    } 

}

