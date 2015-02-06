package fingerprint.provider;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.IntIdentifier;

public class MySQLIdentifierProviderTest {
	private IdentifierProvider provider1;
	private IdentifierProvider provider2;
	
	@Before
	public void setUp() {
		provider1 = new MySQLIdentifierProvider("test");
		provider2 = new MySQLIdentifierProvider("test");
	}
	
	@After
	public void tearDown() {
		provider1.setID(new IntIdentifier(0));
	}
	
	@Test
	public void testGetID() {
		Identifier id;
		
		id = provider1.getID();
		assertEquals(1, id.getID().longValue());
		
		id = provider2.getID();
		assertEquals(2, id.getID().longValue());
		
		id = provider1.getID();
		assertEquals(3, id.getID().longValue());
	}
	
	@Test
	public void testSetID() {
		Identifier id = new IntIdentifier(5);
		provider1.setID(id);
		
		id = provider1.getID();
		assertEquals(6, id.getID().longValue());
		
		id = provider2.getID();
		assertEquals(7, id.getID().longValue());
		
		id = provider1.getID();
		assertEquals(8, id.getID().longValue());
	}
}
