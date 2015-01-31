package fingerprint;

import java.math.BigInteger;

public class BigIntegerIdentifier implements java.io.Serializable, Identifier {
    private static final long serialVersionUID = 1L;
    private final BigInteger id;
    private byte[] raw;
    
    public BigIntegerIdentifier(BigInteger id) {
        this.id = id;
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
