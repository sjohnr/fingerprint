package fingerprint.provider;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.UUIDIdentifier;

public class RandomUUIDIdentifierProvider implements IdentifierProvider {
	@Override
	public void setID(Identifier identifier) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public UUIDIdentifier getID() {
		return new UUIDIdentifier();
	}
}
