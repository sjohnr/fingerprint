package fingerprint.provider;

import fingerprint.ServerIdentifier;

public interface ServerIdentifierProvider {
	void setID(ServerIdentifier identifier);
	ServerIdentifier getID();
}
