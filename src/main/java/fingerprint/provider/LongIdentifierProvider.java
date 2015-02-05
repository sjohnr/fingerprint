package fingerprint.provider;

import java.util.concurrent.atomic.AtomicLong;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.LongIdentifier;

public class LongIdentifierProvider implements IdentifierProvider {
	private AtomicLong safe = new AtomicLong();

	@Override
	public void setID(Identifier identifier) {
		safe.set(identifier.getID().longValue());
	}

	@Override
	public Identifier getID() {
		return new LongIdentifier(safe.incrementAndGet());
	}
}
