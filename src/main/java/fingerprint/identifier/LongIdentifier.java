package fingerprint.identifier;

import java.math.BigInteger;

import fingerprint.Identifier;

public class LongIdentifier implements java.io.Serializable, Identifier {
	private static final long serialVersionUID = 1L;
	
	private final long id;
	private transient byte[] raw;
	
	public LongIdentifier(long id) {
		this.id = id;
	}
	
	public LongIdentifier(BigInteger id) {
		this.id = id.longValue();
	}
	
	public LongIdentifier(byte[] identifier) {
		if (identifier.length != 8) {
			throw new IllegalArgumentException("Invalid raw identifier: Must be 8 bytes!");
		}
		
		this.raw = identifier;
		this.id = ((identifier[0] & 0xff) << 56)
				+ ((identifier[1] & 0xff) << 48)
				+ ((identifier[2] & 0xff) << 40)
				+ ((identifier[3] & 0xff) << 32)
				+ ((identifier[4] & 0xff) << 24)
				+ ((identifier[5] & 0xff) << 16)
				+ ((identifier[6] & 0xff) << 8)
				+ ((identifier[7] & 0xff));
	}
	
	@Override
	public BigInteger getID() {
		return new BigInteger(getBytes());
	}
	
	@Override
	public byte[] getBytes() {
		if (raw == null) {
			raw = new byte[8];
			raw[0] = (byte) (id >> 56);
			raw[1] = (byte) (id >> 48);
			raw[2] = (byte) (id >> 40);
			raw[3] = (byte) (id >> 32);
			raw[4] = (byte) (id >> 24);
			raw[5] = (byte) (id >> 16);
			raw[6] = (byte) (id >> 8);
			raw[7] = (byte) (id);
		}
		return raw;
	}
}
