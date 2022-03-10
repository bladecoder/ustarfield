package starfield.event;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface EventParams {
    void read(DataInputStream dis) throws java.io.IOException;

    void write(DataOutputStream dos) throws java.io.IOException;
}
