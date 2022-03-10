package starfield.event;

public interface EventProcessor {
    void processEvent(Event e, Object creator) throws Exception ;
}
