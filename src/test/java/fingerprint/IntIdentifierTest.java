package fingerprint;

import static org.junit.Assert.*;

import org.junit.Test;

import fingerprint.identifier.IntIdentifier;

public class IntIdentifierTest {
    @Test
    public void testGetBytes() {
        IntIdentifier id;
        byte[] raw;
        
        id = new IntIdentifier(7);
        raw = id.getBytes();
        
        assertEquals(4, raw.length);
        assertEquals((byte) 0x00, raw[0]);
        assertEquals((byte) 0x00, raw[1]);
        assertEquals((byte) 0x00, raw[2]);
        assertEquals((byte) 0x07, raw[3]);
        
        id = new IntIdentifier(15);
        raw = id.getBytes();
        
        assertEquals(4, raw.length);
        assertEquals((byte) 0x00, raw[0]);
        assertEquals((byte) 0x00, raw[1]);
        assertEquals((byte) 0x00, raw[2]);
        assertEquals((byte) 0x0f, raw[3]);
        
        id = new IntIdentifier(16);
        raw = id.getBytes();
        
        assertEquals(4, raw.length);
        assertEquals((byte) 0x00, raw[0]);
        assertEquals((byte) 0x00, raw[1]);
        assertEquals((byte) 0x00, raw[2]);
        assertEquals((byte) 0x10, raw[3]);
        
        id = new IntIdentifier(256);
        raw = id.getBytes();
        
        assertEquals(4, raw.length);
        assertEquals((byte) 0x00, raw[0]);
        assertEquals((byte) 0x00, raw[1]);
        assertEquals((byte) 0x01, raw[2]);
        assertEquals((byte) 0x00, raw[3]);
        
        id = new IntIdentifier(2147483647);
        raw = id.getBytes();
        
        assertEquals(4, raw.length);
        assertEquals((byte) 0x7f, raw[0]);
        assertEquals((byte) 0xff, raw[1]);
        assertEquals((byte) 0xff, raw[2]);
        assertEquals((byte) 0xff, raw[3]);
    }
    
    @Test
    public void testGetID() {
        IntIdentifier id;
        
        id = new IntIdentifier(7);
        assertEquals(7, id.getID().intValue());
        
        id = new IntIdentifier(15);
        assertEquals(15, id.getID().intValue());
        
        id = new IntIdentifier(16);
        assertEquals(16, id.getID().intValue());
        
        id = new IntIdentifier(256);
        assertEquals(256, id.getID().intValue());
        
        id = new IntIdentifier(2147483647);
        assertEquals(2147483647, id.getID().intValue());
    }
}
