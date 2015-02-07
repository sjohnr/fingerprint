package fingerprint.provider;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fingerprint.Identifier;
import fingerprint.identifier.UUIDIdentifier;

public class TimeBasedUUIDIdentifierProviderTest {
	private TimeBasedUUIDIdentifierProvider provider;
	
	@Before
	public void setUp() throws IOException {
		provider = new TimeBasedUUIDIdentifierProvider();
	}
	
	@Test
	public void testGetID() {
		Identifier id1 = provider.getID();
		Identifier id2 = provider.getID();
		
		assertNotNull(id1);
		assertTrue(id1 instanceof UUIDIdentifier);
		assertEquals(16, id1.getBytes().length);
		assertNotEquals(id1.getID(), id2.getID());
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testSetID() {
		Identifier id = new UUIDIdentifier();
		provider.setID(id);
	}
	
	@Test
	public void testTimeBasedUUID() {
		UUID uuid = provider.timeBasedUUID();
		assertTrue(System.currentTimeMillis() >= uuid.getMostSignificantBits() / 1000);
	}
}
