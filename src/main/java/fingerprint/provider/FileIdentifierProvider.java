package fingerprint.provider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import org.apache.log4j.Logger;

import fingerprint.Identifier;
import fingerprint.IdentifierProvider;
import fingerprint.identifier.BigIntegerIdentifier;

public class FileIdentifierProvider implements IdentifierProvider {
    private static final Logger log = Logger.getLogger(FileIdentifierProvider.class);
    
	private File file;
	
	public FileIdentifierProvider(File file) {
		this.file = file;
	}
	
	@Override
	public void setID(Identifier identifier) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(identifier.getID().toString());
		} catch (IOException ex) {
		    throw new RuntimeException("Unable to write file " + file + ":", ex);
		}
	}
	
	@Override
	public Identifier getID() {
	    Identifier id = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			id = new BigIntegerIdentifier(new BigInteger(reader.readLine()));
		} catch (IOException ex) {
			log.debug("Unable to read file " + file + " due to IOException: " + ex.getMessage());
		} catch (NumberFormatException ex) {
		    log.warn("Unable to parse data in file " + file + ": " + ex.getMessage());
		}
		
		return id;
	}
}

