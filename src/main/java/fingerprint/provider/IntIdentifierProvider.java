package fingerprint.provider;

import java.util.concurrent.atomic.AtomicInteger;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.IntIdentifier;

public class IntIdentifierProvider implements IdentifierProvider {
	private AtomicInteger safe = new AtomicInteger();
	private int unsafe = 0;
	
	private Type type;
	
	public IntIdentifierProvider() {
		this(Type.SAFE);
	}
	
	public IntIdentifierProvider(Type type) {
		this.type = type;
	}

	@Override
	public void setID(Identifier identifier) {
		switch (type) {
			case SAFE:
				safe.set(identifier.getID().intValue());
				break;
			case UNSAFE:
				unsafe = identifier.getID().intValue();
				break;
		}
	}

	@Override
	public Identifier getID() {
		Identifier identifier = null;
		switch (type) {
			case SAFE:
				identifier = new IntIdentifier(safe.incrementAndGet());
				break;
			case UNSAFE:
				identifier = new IntIdentifier(++unsafe);
				break;
		}
		
		return identifier;
	}
	
	public static enum Type {
		SAFE, UNSAFE
	}
}
