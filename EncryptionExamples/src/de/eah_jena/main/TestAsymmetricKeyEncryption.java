package de.eah_jena.main;

import de.eah_jena.source.AsymmetricKeyEncryption;

public class TestAsymmetricKeyEncryption {

	public static void main(String[] args) throws Exception {

		// kompletten Ver- und ENtschl�sselungsprozess mithilfe von RSA
		// durchf�hren
		AsymmetricKeyEncryption asymm = new AsymmetricKeyEncryption();
		asymm.completeAsymetricCryptography();
//		asymm.decryptTest();
		asymm.encrypt();
	}
}
