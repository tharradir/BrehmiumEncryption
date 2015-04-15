package de.eah_jena.source;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import java.io.*;
import java.security.*;
import java.security.spec.*;

import de.flexiprovider.core.FlexiCoreProvider;

@SuppressWarnings("unused")
public class AsymmetricKeyEncryption {

    public void completeAsymetricCryptography() throws Exception {

	Security.addProvider(new FlexiCoreProvider());

	KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "FlexiCore");
	Cipher cipher = Cipher.getInstance("RSA", "FlexiCore");

	kpg.initialize(1024);
	KeyPair keyPair = kpg.generateKeyPair();
	PrivateKey privKey = keyPair.getPrivate();
	PublicKey pubKey = keyPair.getPublic();
	
// show Keys in Console

        PublicKey pub = keyPair.getPublic();
        System.out.println("Public Key: " + getHexString(pub.getEncoded()));

        PrivateKey priv = keyPair.getPrivate();
        System.out.println("Private Key: " + getHexString(priv.getEncoded()));

	
	

	// Encrypt with public Key

	cipher.init(Cipher.ENCRYPT_MODE, pubKey);

	String cleartextFile = "cleartext.txt";
	String ciphertextFile = "ciphertextRSA.txt";

	FileInputStream fis = new FileInputStream(cleartextFile);
	FileOutputStream fos = new FileOutputStream(ciphertextFile);
	CipherOutputStream cos = new CipherOutputStream(fos, cipher);

	byte[] block = new byte[32];
	int i;
	while ((i = fis.read(block)) != -1) {
	    cos.write(block, 0, i);
	}
	cos.close();

	// Decrypt with private Key

	String cleartextAgainFile = "cleartextAgainRSA.txt";

	cipher.init(Cipher.DECRYPT_MODE, privKey);

	fis = new FileInputStream(ciphertextFile);
	CipherInputStream cis = new CipherInputStream(fis, cipher);
	fos = new FileOutputStream(cleartextAgainFile);

	while ((i = cis.read(block)) != -1) {
	    fos.write(block, 0, i);
	}
	fos.close();
	
    }

    
    private static String getHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
                result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
        
}
}