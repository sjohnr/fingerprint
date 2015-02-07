package fingerprint.provider;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.IntIdentifier;
import fingerprint.provider.server.ZMQIdentifierServer;

public class ZMQIdentifierProviderTest {
	private ZMQIdentifierServer server;
	private ZMQIdentifierProvider provider1;
	private ZMQIdentifierProvider provider2;
	private IdentifierProvider provider3;
	
	@Before
	public void setUp() {
		provider1 = new ZMQIdentifierProvider("localhost");
		provider2 = new ZMQIdentifierProvider("localhost");
		provider3 = new IntIdentifierProvider();
		server = new ZMQIdentifierServer(provider3);
		server.start();
	}
	
	@After
	public void tearDown() {
		server.destroy();
	}
	
	@Test
	public void testGetID() {
		Identifier id;
		
		id = provider1.getID();
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
