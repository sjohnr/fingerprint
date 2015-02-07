package fingerprint.provider;

import java.util.concurrent.atomic.AtomicLong;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.LongIdentifier;

public class LongIdentifierProvider implements IdentifierProvider {
	private AtomicLong safe = new AtomicLong();
	private long unsafe = 0;
	
	private Type type;
	
	public LongIdentifierProvider() {
		this(Type.SAFE);
	}
	
	public LongIdentifierProvider(Type type) {
		this.type = type;
	}

	@Override
	public void setID(Identifier identifier) {
		switch (type) {
			case SAFE:
				safe.set(identifier.getID().longValue());
				break;
			case UNSAFE:
				unsafe = identifier.getID().longValue();
				break;
		}
	}

	@Override
	public Identifier getID() {
		Identifier identifier = null;
		switch (type) {
			case SAFE:
				identifier = new LongIdentifier(safe.incrementAndGet());
				break;
			case UNSAFE:
				identifier = new LongIdentifier(++unsafe);
				break;
		}
		
		return identifier;
	}
	
	public static enum Type {
		SAFE, UNSAFE
	}
}
