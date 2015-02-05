package fingerprint.provider;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.UUIDIdentifier;

public class UUIDIdentifierProvider implements IdentifierProvider {
	@Override
	public void setID(Identifier identifier) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Identifier getID() {
		return new UUIDIdentifier();
	}
}
