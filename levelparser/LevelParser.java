import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;

import starfield.event.*;


public class LevelParser extends DefaultHandler {

    int depth;
    Event event;
    static DataOutputStream dos;

    public LevelParser() {
        super();
    }

    public static void main(String[] args) {
        String inFileName = null;
        String outFileName;

        if (args.length < 2) {
            System.err.println("java LevelParser <filein.xml> <fileout.evt>");
            return;
        } else {
            inFileName = args[0];
            outFileName = args[1];
        }

        try {
            dos = new DataOutputStream(new FileOutputStream(outFileName));
        } catch (IOException e) {
            System.out.println("File not found: " + outFileName);
            System.exit(1);
        }

        (new LevelParser()).parse(inFileName);
    }

    public void parse(String xmlFile) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            saxParser.parse(xmlFile, this);
            
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endDocument() {
    }

    @Override
    public void endElement(String namespaceURI, String qName,
                           String localName) {

        //System.out.println("end: " + localName + " depth: " + depth + " qname: " + qName);

        if(depth == 2) {
            if(localName.equals("definepath")) { // add point if playertarget path
                DefinePathParams p = (DefinePathParams)event.params;
                if(p.type == DefinePathParams.TARGETPLAYER) {
                    addPathPoint(p);
                }
            }
                    
            try{
                event.write(dos);
            } catch (java.io.IOException ioe) {
                System.out.println(ioe.toString());
                System.exit(1);
            }
        }
        
        depth--;
    }

    @Override
    public void startDocument() {
        depth = 0;
    }

    @Override
    public void startElement(String namespaceURI, String qName,
                             String localName, Attributes attrList) {
        depth++;
        
        //System.out.println("start: " + localName + " depth: " + depth + " qName: " + qName);

        switch(depth) {
            case 1:

                if(localName.equals("level")) {
                } else {
                    System.out.println("ERROR, element unknown: " + localName);
                    System.exit(1);
                }
                
            break;
           
            case 2:
                if(localName.equals("defineobject")) {
                    event = new Event(Event.DEFINEOBJECT);
                    parseEvent(attrList);
                    parseObjectAttr(attrList);
                } else if(localName.equals("definepath")) {
                    event = new Event(Event.DEFINEPATH);
                    parseEvent(attrList);
                    parsePathAttr(attrList);
                } else if(localName.equals("enemy")) {
                    event = new Event(Event.ENEMY);
                    parseEvent(attrList);
                    parseCreateObjectAttr(attrList);
                } else if(localName.equals("background")) {
                    event = new Event(Event.BACKGROUND);
                    parseEvent(attrList);
                    parseCreateObjectAttr(attrList);
                } else if(localName.equals("player")) {
                    event = new Event(Event.PLAYER);
                    parseEvent(attrList);
                    parseCreateObjectAttr(attrList);
                } else if(localName.equals("playerbullet")) {
                    event = new Event(Event.PLAYERBULLET);
                    parseEvent(attrList);
                    parseCreateObjectAttr(attrList);
                } else if(localName.equals("end")) {
                    event = new Event(Event.END);
                    parseEvent(attrList);
                } else if(localName.equals("load")) {
                    event = new Event(Event.LOAD);
                    parseEvent(attrList);
                } else if(localName.equals("sound")) {
                    event = new Event(Event.SOUND);
                    parseEvent(attrList);
                } else if(localName.equals("text")) {
                    event = new Event(Event.TEXT);
                    parseEvent(attrList);
                } else if(localName.equals("speed")) {
                    event = new Event(Event.SPEED);
                    parseEvent(attrList);
                    parseObjectPropAttr(attrList);
                } else if(localName.equals("energy")) {
                    event = new Event(Event.ENERGY);
                    parseEvent(attrList);
                    parseObjectPropAttr(attrList);
                } else if(localName.equals("life")) {
                    event = new Event(Event.LIFE);
                    parseEvent(attrList);
                    parseObjectPropAttr(attrList);
                } else if(localName.equals("destroy")) {
                    event = new Event(Event.DESTROY);
                    parseEvent(attrList);
                    parseObjectPropAttr(attrList);
                } else if(localName.equals("fire")) {
                    event = new Event(Event.FIRE);
                    parseEvent(attrList);
                    parseObjectEventAttr(attrList);
                } else if(localName.equals("pause")) {
                    event = new Event(Event.PAUSE);
                    parseEvent(attrList);
                } else if(localName.equals("continue")) {
                    event = new Event(Event.CONTINUE);
                    parseEvent(attrList);
                } else {
                    System.out.println("ERROR, element unknown: " + localName);
                    System.exit(1);
                }

            break;
            
            case 3:
                if(localName.equals("point") && 
                        event.type == Event.DEFINEPATH) {
                    parsePathPoint(attrList);
                } else {
                    System.out.println("ERROR, element unknown: " + localName);
                    System.exit(1);
                }
                
            break;
 
        }
    }

    void parseEvent(Attributes atts) {
        if(atts.getValue("name") != null) {
            event.name = atts.getValue("name");
        } else {
            System.out.println("ERROR: 'name' param missing in Event");
            System.exit(1);
        }


        if(atts.getValue("wait") != null) {
            event.wait = Integer.parseInt(atts.getValue("wait"));
        }
        
        if(atts.getValue("defined") != null) {
            event.defined = 
                Boolean.valueOf(atts.getValue("defined")).booleanValue();
        }
    }

    void parseObjectAttr(Attributes atts) {
        DefineObjectParams  p = (DefineObjectParams)event.params;

        
        if(atts.getValue("img") != null) {
            p.imgFile = atts.getValue("img");
        } else {
            p.imgFile = event.name + ".png";
        }
        
        if(atts.getValue("begin") != null) {
            p.beginEvent = atts.getValue("begin").split(",");
        }
          
        if(atts.getValue("fire") != null) {
            p.fireEvent = atts.getValue("fire").split(",");
        }

        if(atts.getValue("end") != null) {
            p.endEvent = atts.getValue("end").split(",");
        }

        if(atts.getValue("width") != null) {
            p.width = Integer.parseInt(atts.getValue("width"));
        }

        if(atts.getValue("height") != null) {
            p.height = Integer.parseInt(atts.getValue("height"));
        }


        if(atts.getValue("delay") != null) {
            p.delay = Integer.parseInt(atts.getValue("delay"));
        }


        if(atts.getValue("loop") != null) {
            p.loop = Integer.parseInt(atts.getValue("loop"));
        }

        if(atts.getValue("energy") != null) {
            p.energy = Integer.parseInt(atts.getValue("energy"));
        }

        if(atts.getValue("damage") != null) {
            p.damage = Integer.parseInt(atts.getValue("damage"));
        }

        if(atts.getValue("points") != null) {
            p.points = Integer.parseInt(atts.getValue("points"));
        }

        if(atts.getValue("firedelay") != null) {
            p.firedelay = Integer.parseInt(atts.getValue("firedelay"));
        }

        if(atts.getValue("bulletCollision") != null) {
            p.bulletCollision = 
                Boolean.valueOf(atts.getValue("bulletCollision")).booleanValue();
        }

    }
    
    void parsePathAttr(Attributes atts) {
        DefinePathParams  p = (DefinePathParams)event.params;
        String type = atts.getValue("type");
        
        if(type != null) {
            if(type.equals("static")) p.type = DefinePathParams.STATIC;
            else if(type.equals("vector")) p.type = DefinePathParams.VECTOR;
            else if(type.equals("line")) p.type = DefinePathParams.LINE;
            else if(type.equals("circle")) p.type = DefinePathParams.CIRCLE;
            else if(type.equals("ellipse")) p.type = DefinePathParams.ELLIPSE;
            else if(type.equals("free")) p.type = DefinePathParams.FREE;
            else if(type.equals("keyboard")) p.type = DefinePathParams.KEYBOARD;
            else if(type.equals("sinhor")) p.type = DefinePathParams.SINHOR;
            else if(type.equals("sinvert")) p.type = DefinePathParams.SINVERT;
            else if(type.equals("targetplayer")) p.type = DefinePathParams.TARGETPLAYER;
            else if(type.equals("random")) p.type = DefinePathParams.RANDOM;
            else if(type.equals("free")) p.type = DefinePathParams.FREE;
            else if(type.equals("follow")) p.type = DefinePathParams.FOLLOW;
            else {
                System.out.println("ERROR: unknown path type: " + type);
                System.exit(1);
            }
        }
        
        if(atts.getValue("speed") != null) {
            p.speed = Integer.parseInt(atts.getValue("speed"));
        }        
        
        if(atts.getValue("loop") != null) {
            p.loop = Integer.parseInt(atts.getValue("loop"));
        }

        if(atts.getValue("scrollable") != null) {
            p.scrollable = 
                Boolean.valueOf(atts.getValue("scrollable")).booleanValue();
        }
        
        if(atts.getValue("anchor") != null) {
            String anchors[];
            anchors = atts.getValue("anchor").split(",");
            for(int i=0; i<anchors.length; i++) {
                if(anchors[i].equals("hcenter")) p.anchor |= p.HCENTER;
                else if(anchors[i].equals("vcenter")) p.anchor |= p.VCENTER;
                else if(anchors[i].equals("top")) p.anchor |= p.TOP;
                else if(anchors[i].equals("bottom")) p.anchor |= p.BOTTOM;
                else if(anchors[i].equals("left")) p.anchor |= p.LEFT;
                else if(anchors[i].equals("right")) p.anchor |= p.RIGHT;
            }
        }

    }

    void parsePathPoint(Attributes atts) {
        DefinePathParams  p = (DefinePathParams)event.params;

        addPathPoint(p);
        
        if(atts.getValue("x") != null) {
            p.x[p.nPoints - 1] = Integer.parseInt(atts.getValue("x"));
        }
        
        if(atts.getValue("y") != null) {
            p.y[p.nPoints - 1] = Integer.parseInt(atts.getValue("y"));
        }
        
        if(atts.getValue("percent") != null) {
            p.percent[p.nPoints - 1] = 
                Boolean.valueOf(atts.getValue("percent")).booleanValue();
        }

    }

    private void addPathPoint(DefinePathParams p) {
        int oldx[] = p.x;
        int oldy[] = p.y;
        boolean oldpercent[] = p.percent;

        p.nPoints++;

        p.x = new int [p.nPoints];
        p.y = new int [p.nPoints];
        p.percent = new boolean[p.nPoints];

        for(int i = 0; i < p.nPoints - 1; i++) {
            p.x[i] = oldx[i];
            p.y[i] = oldy[i];
            p.percent[i] = oldpercent[i];
        }   
    }
    
    void parseCreateObjectAttr(Attributes atts) {
        CreateObjectParams  p = (CreateObjectParams)event.params;
 
        if(atts.getValue("object") != null) {
            p.object = atts.getValue("object");
        } else {
            System.out.println("ERROR: 'object' param missing in 'createobject'");
            System.exit(1);
        }
  
        if(atts.getValue("path") != null) {
            p.path = atts.getValue("path");
        } else {
            System.out.println("ERROR: 'path' param missing in 'createobject'");
            System.exit(1);
        }
   
        if(atts.getValue("parent") != null) {
            p.parent = atts.getValue("parent");
        }       
    }
    
    void parseObjectPropAttr(Attributes atts) {
        ObjectPropParams  p = (ObjectPropParams)event.params;
 
        if(atts.getValue("object") != null) {
            p.object = atts.getValue("object");
        } else {
            System.out.println("ERROR: 'object' param missing");
            System.exit(1);
        }
  
        if(atts.getValue("value") != null) {
            p.value = Integer.parseInt(atts.getValue("value"));
        }
        /*
        else {
            System.out.println("ERROR: 'value' param missing in 'createobject'");
            System.exit(1);
        }*/
    }

    void parseObjectEventAttr(Attributes atts) {
        ChangeEventParams  p = (ChangeEventParams)event.params;
 
        if(atts.getValue("object") != null) {
            p.object = atts.getValue("object");
        } else {
            System.out.println("ERROR: 'object' param missing changing event");
            System.exit(1);
        }
  
        if(atts.getValue("event") != null) {        
            p.event = atts.getValue("event").split(",");
        } else {
            System.out.println("ERROR: 'event' param missing changing event");
            System.exit(1);
        }
    }

}
