package fingerprint.provider;

import fingerprint.Identifier;

public interface IdentifierProvider {
	void setID(Identifier identifier);
	Identifier getID();
}
