package de.eah_jena.main;

import java.io.ByteArrayInputStream;

import de.eah_jena.source.GenerateHash;
import de.eah_jena.source.SymmetricKeyEncription;

public class Main {
	
//	private byte[] buf = s«nÏj|HØÊT†ÛaEw¿;
public static void main(String[] args) throws Exception {
//	GenerateHash Hash = new GenerateHash();
//	Hash.generateHash();
	
	SymmetricKeyEncription Symm = new SymmetricKeyEncription();
	Symm.encrypt();
	Symm.decrypt();
	Symm.decryptCustomEncryptedText();
	
//	ByteArrayInputStream input = new ByteArrayInputStream(buf);
	
}
}
