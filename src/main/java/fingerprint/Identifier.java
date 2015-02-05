package fingerprint;

import java.math.BigInteger;

public interface Identifier {
	BigInteger getID();
	byte[] getBytes();
}
