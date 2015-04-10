package de.eah_jena.main;

import de.eah_jena.source.GenerateHash;
import de.eah_jena.source.SymmetricKeyEncription;

public class Main {
public static void main(String[] args) throws Exception {
//	GenerateHash Hash = new GenerateHash();
//	Hash.generateHash();
	
	SymmetricKeyEncription Symm = new SymmetricKeyEncription();
	Symm.encrypt();
	Symm.decrypt();
//	Symm.decryptCustomEncryptedText();
}
}
