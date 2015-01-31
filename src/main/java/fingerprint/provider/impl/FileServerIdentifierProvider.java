package fingerprint.provider.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import fingerprint.ServerIdentifier;
import fingerprint.provider.ServerIdentifierProvider;

public class FileServerIdentifierProvider implements ServerIdentifierProvider {
	private File file;
	
	public FileServerIdentifierProvider(File file) {
		this.file = file;
	}
	
	public void setID(ServerIdentifier identifier) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(String.valueOf(identifier.getID()));
		} catch (IOException ex) {
		    throw new RuntimeException("Unable to write file " + file + ":", ex);
		}
	}
	
	public ServerIdentifier getID() {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			return new ServerIdentifier(Integer.parseInt(reader.readLine()));
		} catch (IOException ex) {
			throw new RuntimeException("Unable to read file " + file + ":", ex);
		} catch (NumberFormatException ex) {
		    throw new RuntimeException("Unable to parse data in file " + file + ":", ex);
		}
	}
}

