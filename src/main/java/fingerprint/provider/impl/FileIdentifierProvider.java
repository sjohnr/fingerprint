package fingerprint.provider.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import fingerprint.Identifier;
import fingerprint.identifier.BigIntegerIdentifier;
import fingerprint.provider.IdentifierProvider;

public class FileIdentifierProvider implements IdentifierProvider {
	private File file;
	
	public FileIdentifierProvider(File file) {
		this.file = file;
	}
	
	public void setID(Identifier identifier) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(identifier.getID().toString());
		} catch (IOException ex) {
		    throw new RuntimeException("Unable to write file " + file + ":", ex);
		}
	}
	
	public Identifier getID() {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			return new BigIntegerIdentifier(new BigInteger(reader.readLine()));
		} catch (IOException ex) {
			throw new RuntimeException("Unable to read file " + file + ":", ex);
		} catch (NumberFormatException ex) {
		    throw new RuntimeException("Unable to parse data in file " + file + ":", ex);
		}
	}
}

