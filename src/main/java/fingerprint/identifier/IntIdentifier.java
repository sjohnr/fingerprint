package fingerprint.identifier;

import java.math.BigInteger;

import fingerprint.Identifier;

public class IntIdentifier implements java.io.Serializable, Identifier {
	private static final long serialVersionUID = 1L;
	
	private final int id;
	private transient byte[] raw;
	
	public IntIdentifier(int id) {
		this.id = id;
	}
    
    public IntIdentifier(BigInteger id) {
        this.id = id.intValue();
    }
	
	public IntIdentifier(byte[] identifier) {
		if (identifier.length != 4) {
			throw new IllegalArgumentException("Invalid raw identifier: Must be 4 bytes!");
		}
		
		this.raw = identifier;
		this.id = ((identifier[0] & 0xff) << 24)
				+ ((identifier[1] & 0xff) << 16)
				+ ((identifier[2] & 0xff) << 8)
				+ ((identifier[3] & 0xff));
	}
	
	@Override
	public BigInteger getID() {
		return new BigInteger(getBytes());
	}
	
	@Override
	public byte[] getBytes() {
		if (raw == null) {
			raw = new byte[4];
			raw[0] = (byte) (id >> 24);
			raw[1] = (byte) (id >> 16);
			raw[2] = (byte) (id >> 8);
			raw[3] = (byte) (id);
		}
		return raw;
	}
}
