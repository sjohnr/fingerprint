package fingerprint.provider;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.IntIdentifier;

public class PersistedIdentifierProviderTest {
    private File file = new File("/tmp/myid");
    private IdentifierProvider provider1;
    private IdentifierProvider provider2;
    
    @Before
    public void setUp() throws IOException {
        provider1 = new PersistedIdentifierProvider(file, new IntIdentifierProvider());
        provider2 = new PersistedIdentifierProvider(file, new IntIdentifierProvider());
    }
    
    @After
    public void tearDown() {
        file.delete();
    }
    
    @Test
    public void testGetID() {
        Identifier id = provider1.getID();
        assertEquals(1, id.getID().intValue());
        
        id = provider2.getID();
        assertEquals(2, id.getID().intValue());
    }
    
    @Test
    public void testSetID() {
        Identifier id = new IntIdentifier(5);
        provider1.setID(id);
        
        id = provider1.getID();
        assertEquals(6, id.getID().intValue());
        
        id = provider2.getID();
        assertEquals(7, id.getID().intValue());
    }
}
