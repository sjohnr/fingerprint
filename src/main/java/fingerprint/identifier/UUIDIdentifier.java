package fingerprint.identifier;

import java.math.BigInteger;
import java.util.UUID;

import fingerprint.Identifier;

public class UUIDIdentifier implements java.io.Serializable, Identifier {
	private static final long serialVersionUID = 1L;
	
	private final UUID id;
	private byte[] raw;
	
	public UUIDIdentifier() {
		this(UUID.randomUUID());
	}
	
	public UUIDIdentifier(UUID id) {
		this.id = id;
	}
	
	@Override
	public BigInteger getID() {
		return new BigInteger(getBytes());
	}
	@Override
	public byte[] getBytes() {
		if (raw == null) {
			raw = new byte[16];
			byte[] msb = new LongIdentifier(id.getMostSignificantBits()).getBytes();
			byte[] lsb = new LongIdentifier(id.getLeastSignificantBits()).getBytes();
			System.arraycopy(msb, 0, raw, 0, 8);
			System.arraycopy(lsb, 0, raw, 8, 8);
		}
		return raw;
	}
}
