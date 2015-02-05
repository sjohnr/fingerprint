package fingerprint.provider;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.LongIdentifier;

public class LongIdentifierProviderTest {
	private IdentifierProvider provider;
	
	@Before
	public void setUp() {
		provider = new LongIdentifierProvider();
	}
	
	@Test
	public void testGetID() {
		Identifier id;
		
		id = provider.getID();
		assertEquals(1, id.getID().longValue());
		
		id = provider.getID();
		assertEquals(2, id.getID().longValue());
	}
	
	@Test
	public void testSetID() {
		Identifier id = new LongIdentifier(5);
		provider.setID(id);
		
		id = provider.getID();
		assertEquals(6, id.getID().longValue());
		
		id = provider.getID();
		assertEquals(7, id.getID().longValue());
	}
}
