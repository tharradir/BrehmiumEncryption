package de.eah_jena.main;

import de.eah_jena.source.SymmetricKeyEncription;

public class TestSymmetricKeyEncryption {

	public static void main(String[] args) throws Exception {
		// Funktionen zur symmetrischenn Verschl�sselung aufrufen
		SymmetricKeyEncription Symm = new SymmetricKeyEncription();
		Symm.encrypt();
		Symm.decrypt();

		// gegebenen verschl�sselten Text entschl�sseln
		Symm.decryptCustomEncryptedText();

	}
}
