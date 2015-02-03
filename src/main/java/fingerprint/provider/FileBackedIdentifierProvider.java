package fingerprint.provider;

import java.io.File;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;

public class FileBackedIdentifierProvider implements IdentifierProvider {
    private FileIdentifierProvider file;
    private IdentifierProvider provider;
    
    public FileBackedIdentifierProvider(File file, IdentifierProvider provider) {
        this.file = new FileIdentifierProvider(file);
        this.provider = provider;
    }
    
    @Override
    public void setID(Identifier identifier) {
        file.setID(identifier);
    }
    
    @Override
    public Identifier getID() {
        // get the identifier from file
        Identifier id = file.getID();
        
        // if the identifier is not available, use another provider and
        // persist to the file for future use
        if (id == null) {
            id = provider.getID();
            file.setID(id);
        }
        
        return id;
    }
}
