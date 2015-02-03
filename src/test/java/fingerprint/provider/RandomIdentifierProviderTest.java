package fingerprint.provider;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.BigIntegerIdentifier;

public class RandomIdentifierProviderTest {
    private IdentifierProvider provider1;
    private IdentifierProvider provider2;
    
    @Before
    public void setUp() {
        provider1 = new RandomIdentifierProvider(4);
        provider2 = new RandomIdentifierProvider(31);
    }
    
    @Test
    public void testGetID() {
        Identifier id;
        
        id = provider1.getID();
        assertTrue(id.getID().shortValue() >= 0);
        assertTrue(id.getID().shortValue() < 16);
        
        id = provider2.getID();
        assertTrue(id.getID().longValue() >= 0);
        assertTrue(id.getID().longValue() < Integer.MAX_VALUE);
    }
    
    @Test(expected=UnsupportedOperationException.class)
    public void testSetID() {
        Identifier id = new BigIntegerIdentifier(new BigInteger("0"));
        provider1.setID(id);
    }
}
