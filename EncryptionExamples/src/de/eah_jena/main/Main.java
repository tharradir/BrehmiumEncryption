package de.eah_jena.main;

import de.eah_jena.source.GenerateHash;
import de.eah_jena.source.SymmetricKeyEncription;

public class Main {

	public static void main(String[] args) throws Exception {

		// Funktionen zur Generierung des Hashwerts aufrufen
		GenerateHash hash = new GenerateHash();
		hash.generateHash();
		System.out.println("\n");

		// Funktionen zur symmetrischenn Verschlüsselung aufrufen
		SymmetricKeyEncription Symm = new SymmetricKeyEncription();
		Symm.encrypt();
		Symm.decrypt();

		// gegebenen verschlüsselten Text entschlüsseln
		Symm.decryptCustomEncryptedText();

	}
}
