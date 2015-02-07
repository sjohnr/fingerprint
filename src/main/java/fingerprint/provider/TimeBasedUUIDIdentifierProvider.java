package fingerprint.provider;

import java.security.SecureRandom;
import java.util.UUID;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.UUIDIdentifier;

/**
 * Non-Microsoft implementation of type 6 variant UUID with MSB time order.
 * 
 * @see https://github.com/jabr/snowball/blob/master/UUID.java
 * @see https://github.com/olegz/uuid/blob/master/uuidgen/src/main/java/org/olegz/uuid/TimeBasedUUIDGenerator.java
 */
public class TimeBasedUUIDIdentifierProvider implements IdentifierProvider {
	private static long versionIdentifier = 0xC000000000000000L;
	private long nodeIdentifier;
	private long versionAndNodeIdentifier;
	
	private long lastTime = Long.MIN_VALUE;
	private long baseTime = System.nanoTime();
	private int sequenceCounter = 0;
	
	public TimeBasedUUIDIdentifierProvider() {
		// generate a random number for the node identifier.
		SecureRandom random = new SecureRandom();
		this.nodeIdentifier = (random.nextLong() & 0x0000FFFFFFFFFFFFL) << 8;
		this.versionAndNodeIdentifier = versionIdentifier | nodeIdentifier;
	}
	
	public TimeBasedUUIDIdentifierProvider(String nodeId) {
		// generate a random number from the node identifier.
		SecureRandom random = new SecureRandom(nodeId.getBytes());
		this.nodeIdentifier = (random.nextLong() & 0x0000FFFFFFFFFFFFL) << 8;
		this.versionAndNodeIdentifier = versionIdentifier | nodeIdentifier;
	}
	
	@Override
	public void setID(Identifier identifier) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public UUIDIdentifier getID() {
		return new UUIDIdentifier(timeBasedUUID());
	}
	
	public UUID timeBasedUUID() {
		// pad out to microseconds and use nanotime to approximate.
		long time = System.currentTimeMillis() * 1000;
		time += (System.nanoTime() - baseTime) / 1000 % 1000;
		
		// handle the clock moving backwards.
		if (time < lastTime) time = lastTime;
		
		// handle multiple ids generated "simultaneously".
		if (time == lastTime) {
			if (sequenceCounter == 256) {
				// rather than block, we'll cheat and return a UUID from the very near future.
				lastTime = ++time;
				sequenceCounter = 0;
			} else {
				sequenceCounter++;
			}
		} else {
			lastTime = time;
			sequenceCounter = 0;
		}
		
		return new UUID(time, versionAndNodeIdentifier | (sequenceCounter & 0xFF));
	}
}
