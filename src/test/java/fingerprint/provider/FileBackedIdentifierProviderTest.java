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

public class FileBackedIdentifierProviderTest {
    private File file = new File("/tmp/myid");
    private IdentifierProvider provider;
    
    @Before
    public void setUp() throws IOException {
        provider = new FileBackedIdentifierProvider(file, new IntIdentifierProvider());
    }
    
    @After
    public void tearDown() {
        file.delete();
    }
    
    @Test
    public void testGetID() {
        Identifier id = provider.getID();
        assertEquals(1, id.getID().intValue());
        
        // again...
        id = provider.getID();
        assertEquals(1, id.getID().intValue());
    }
    
    @Test
    public void testSetID() {
        Identifier id = new IntIdentifier(2);
        provider.setID(id);
        
        id = provider.getID();
        assertEquals(2, id.getID().intValue());
        
        // again...
        id = provider.getID();
        assertEquals(2, id.getID().intValue());
    }
}
