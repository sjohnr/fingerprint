package fingerprint.provider;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.IntIdentifier;
import fingerprint.identifier.LongIdentifier;

public class ZooKeeperIdentifierProviderTest {
//	private ZkServer zkServer;
	private IdentifierProvider provider1;
	private IdentifierProvider provider2;
	
	@Before
	public void setUp() {
//		zkServer = new ZkServer("/tmp/zk/data", "/tmp/zk/log", null, 2182, 2000);
//		zkServer.start();
		
		provider1 = new ZooKeeperIdentifierProvider("localhost:2181", "test");
		provider2 = new ZooKeeperIdentifierProvider("localhost:2181", "test");
	}
	
	@After
	public void tearDown() {
		provider1.setID(new IntIdentifier(0));
//		zkServer.shutdown();
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
		Identifier id = new LongIdentifier(5);
		provider1.setID(id);
		
		id = provider1.getID();
		assertEquals(6, id.getID().longValue());
		
		id = provider2.getID();
		assertEquals(7, id.getID().longValue());
		
		id = provider1.getID();
		assertEquals(8, id.getID().longValue());
	}
}
