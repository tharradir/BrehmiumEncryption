package de.eah_jena.main;

import java.io.ByteArrayInputStream;

import de.eah_jena.source.GenerateHash;
import de.eah_jena.source.SymmetricKeyEncription;

public class Main {
	

	
	
public static void main(String[] args) throws Exception {
	
	SymmetricKeyEncription Symm = new SymmetricKeyEncription();
	Symm.encrypt();
	Symm.decrypt();
	Symm.decryptCustomEncryptedText();
	
	
}
}
