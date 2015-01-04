package finterprint.provider.impl;

import java.io.*;

import finterprint.provider.SIDProvider;

public class FileSIDProvider implements SIDProvider {
	private File file;
	
	public FileSIDProvider(File file) {
		this.file = file;
	}
	
	public void setID(SID identifier) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(String.valueOf(identifier.getID()));
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	
	public SID getID() {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			return new SID(Integer.parseInt(reader.readLine()));
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} catch (NumberFormatException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
}

