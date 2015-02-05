package fingerprint.provider;

import java.io.File;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;

public class PersistedIdentifierProvider implements IdentifierProvider {
    private boolean init;
    
    private FileIdentifierProvider file;
    private IdentifierProvider provider;
    
    public PersistedIdentifierProvider(File file, IdentifierProvider provider) {
        this.file = new FileIdentifierProvider(file);
        this.provider = provider;
        this.init = true;
    }
    
    @Override
    public void setID(Identifier identifier) {
        file.setID(identifier);
        init = true;
    }
    
    @Override
    public Identifier getID() {
        initIfNeeded();
        
        Identifier identifier = provider.getID();
        file.setID(identifier);
        
        return identifier;
    }
    
    private void initIfNeeded() {
        if (init) {
            Identifier identifier = file.getID();
            if (identifier != null) {
                provider.setID(identifier);
            }
            
            init = false;
        }
    }
}
