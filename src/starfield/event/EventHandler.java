package starfield.event;

import java.util.Stack;
import java.util.Hashtable;
import java.io.*;
import starfield.Timing;

public class EventHandler {
    EventProcessor ep;

    Hashtable defined;
    Event currentEvent;
    long lastTick;
    long actualTick;
    boolean pause = false;

    int dinamicName;

    DataInputStream dis;

    Stack streamStack;

    /** is end of level reached? */
    boolean eol;

    public EventHandler(DataInputStream dis, EventProcessor ep) 
        throws IOException {
        this.ep = ep;

        eol = false;
        dinamicName = 10;
        defined = new Hashtable();
        
        this.dis = dis;
        streamStack = new Stack();
       
        readNextEvent();
    }

    public void load(DataInputStream dis) {
        streamStack.push(this.dis);
        this.dis = dis;
    }

    void readNextEvent() throws EOFException, IOException {

        currentEvent = new Event();

        currentEvent.read(dis);

        lastTick = Timing.getTick();
    }

    public boolean processEvents() throws Exception {
        boolean eof;

        if(eol || pause) return false;
        
        while(processEvent(currentEvent)) {
            do {
                eof = false;
                
                try {
                    readNextEvent();
                } catch (EOFException e ) {
                    System.out.println("EventHandler: End of file reached");
                    eof = true;
                
                    if(streamStack.isEmpty()) {
                        System.out.println("EventHandler: End of level reached");
                        eol = true;
                        return false;
                    }

                    dis = (DataInputStream)streamStack.pop();
                }
            } while(eof);
        }

        return true;
    }

    void addDefined(Event e) {
        e.wait = 0;
        defined.put(e.name, e);
    }
    
    /** 
     * Gets an event from the defined Hash and process it 
     */
    public void processDefined(String name, Object oParent) throws Exception{
        Event e;

        e = (Event)defined.get(name);
        if( e == null) {
            System.out.println("Event not found: " + name);
            return;
        }

        if(e.params != null) {
            e = new Event(e, "" + dinamicName);
            dinamicName++;
        }

        if(e.type == Event.CONTINUE) pause = false;
        else ep.processEvent(e, oParent);
    }

    public Event getDefined(String key) {
        Event e = (Event)defined.get(key);

        if(e == null) {
            System.out.println("EventHandler::getDefined->"+key+" not found");
        }

        return e;
    }

    Object getParent(Event e) {
        return null;
    }

    boolean processEvent(Event e) throws Exception {
        //System.out.println("processevent " + e.name + " type: " + e.type + " defined: " + e.defined); 
            
        if(e.defined) {
            addDefined(e);
            return true;
        }

        
        actualTick = Timing.getTick();
        
        if((int)(actualTick - lastTick) < e.wait) {
            //System.out.println("waiting in event a-l: "+ 
            //        (int)(actualTick - lastTick) + " e.wait: " + e.wait);
            return false;
        }
        
        if(e.type == Event.PAUSE) pause = true;
        else ep.processEvent(e, null);
        
        return true;
    }
}
