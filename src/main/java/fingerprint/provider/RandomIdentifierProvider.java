package fingerprint.provider;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.BigIntegerIdentifier;

public class RandomIdentifierProvider implements IdentifierProvider {
    private int numBits;
    private Random random;
    
    public RandomIdentifierProvider() {
        this(128);
    }
    
    public RandomIdentifierProvider(int numBits) {
        this(numBits, new SecureRandom());
    }
    
    public RandomIdentifierProvider(int numBits, byte[] seed) {
        this(numBits, new SecureRandom(seed));
    }
    
    public RandomIdentifierProvider(int numBits, Random random) {
        this.numBits = numBits;
        this.random = random;
    }
    
    @Override
    public void setID(Identifier identifier) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Identifier getID() {
        return new BigIntegerIdentifier(new BigInteger(numBits, random));
    }
}
