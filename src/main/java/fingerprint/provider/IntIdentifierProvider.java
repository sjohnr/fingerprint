package fingerprint.provider;

import java.util.concurrent.atomic.AtomicInteger;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.IntIdentifier;

public class IntIdentifierProvider implements IdentifierProvider {
    private AtomicInteger safe = new AtomicInteger();

    @Override
    public void setID(Identifier identifier) {
        safe.set(identifier.getID().intValue());
    }

    @Override
    public Identifier getID() {
        return new IntIdentifier(safe.incrementAndGet());
    }
}
