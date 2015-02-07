package fingerprint.identifier;

import java.math.BigInteger;

import fingerprint.Identifier;

public class BigIntegerIdentifier implements java.io.Serializable, Identifier {
	private static final long serialVersionUID = 1L;
	
	private final BigInteger id;
	private byte[] raw;
	
	public BigIntegerIdentifier(BigInteger id) {
		this.id = id;
	}
	
	public BigIntegerIdentifier(byte[] raw) {
		this.id = new BigInteger(raw);
		this.raw = raw;
	}

	@Override
	public BigInteger getID() {
		return id;
	}

	@Override
	public byte[] getBytes() {
		if (raw == null) {
			raw = id.toByteArray();
		}
		return raw;
	}
}
