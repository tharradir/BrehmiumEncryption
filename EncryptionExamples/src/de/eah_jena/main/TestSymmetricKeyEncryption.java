package de.eah_jena.main;

import de.eah_jena.source.SymmetricKeyEncription;

public class TestSymmetricKeyEncryption {

	public static void main(String[] args) throws Exception {
		// Funktionen zur symmetrischenn Verschlüsselung aufrufen
		SymmetricKeyEncription Symm = new SymmetricKeyEncription();
		Symm.encrypt();
		Symm.decrypt();

		// gegebenen verschlüsselten Text entschlüsseln
		Symm.decryptCustomEncryptedText();

	}
}
