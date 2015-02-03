package fingerprint.provider;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.IntIdentifier;

public class FileIdentifierProviderTest {
    private File file = new File("/tmp/myid");
    private IdentifierProvider provider;
    
    @Before
    public void setUp() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("1234");
        }
        
        provider = new FileIdentifierProvider(file);
    }
    
    @After
    public void tearDown() {
        file.delete();
    }
    
    @Test
    public void testGetID() {
        Identifier id = provider.getID();
        assertEquals("1234", id.getID().toString());
    }
    
    @Test
    public void testSetID() {
        Identifier id = new IntIdentifier(1235);
        provider.setID(id);
        
        id = provider.getID();
        assertEquals("1235", id.getID().toString());
    }
}
