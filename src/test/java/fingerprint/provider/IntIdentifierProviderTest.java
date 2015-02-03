package fingerprint.provider;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.IntIdentifier;

public class IntIdentifierProviderTest {
    private IdentifierProvider provider;
    
    @Before
    public void setUp() {
        provider = new IntIdentifierProvider();
    }
    
    @Test
    public void testGetID() {
        Identifier id;
        
        id = provider.getID();
        assertEquals(1, id.getID().intValue());
        
        id = provider.getID();
        assertEquals(2, id.getID().intValue());
    }
    
    @Test
    public void testSetID() {
        Identifier id = new IntIdentifier(5);
        provider.setID(id);
        
        id = provider.getID();
        assertEquals(6, id.getID().intValue());
        
        id = provider.getID();
        assertEquals(7, id.getID().intValue());
    }
}
