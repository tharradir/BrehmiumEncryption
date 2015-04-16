package de.eah_jena.source;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import org.apache.commons.codec.binary.Base64;

import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.*;

import de.flexiprovider.core.FlexiCoreProvider;
import de.flexiprovider.core.pbe.PBEKeySpec;
import de.flexiprovider.core.rsa.RSAPublicKey;

@SuppressWarnings("unused")
public class AsymmetricKeyEncryption {

	private byte[] base64pubKey;
	private byte[] base64privKey;
	private BufferedReader console;
	private String pubKeyStr;
	private PublicKey publicKey;

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
		base64pubKey = Base64.encodeBase64(pub.getEncoded());
//		System.out.println("Public Key: " + getHexString(pub.getEncoded()));
//		System.out.println("\n");
		System.out.println("Public Key: " + base64pubKey.toString());
		 System.out.write(base64pubKey);
		 System.out.println(pub.toString());
		
		PrivateKey priv = keyPair.getPrivate();
		base64privKey = Base64.encodeBase64(priv.getEncoded());
    	System.out.println("\nPrivate Key: ");
        System.out.write(base64privKey);
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
	
//	public void decrypt() throws IOException {
//		
//		BufferedReader console = new BufferedReader(new InputStreamReader(
//				System.in));
//		System.out.println("\nBitte Schlüssel/Passwort angeben:");
//		try {
//			 pubKeyStr = new String(console.readLine());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		byte[] pubKeyByte = (pubKeyStr).getBytes();
//		
//		System.out.write(pubKeyByte);
//		
//		try {
//			publicKey = 
//				    KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(pubKeyByte));
//		} catch (InvalidKeySpecException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.out.println(publicKey.toString());
//		
//		
//	}
	
//	public void decrypt() {
//		
//		System.out.println("\nBitte public exponent angeben:");
//		BigInteger pubExp = new BigInteger(console.readLine());
//		System.out.println("\nBitte modulus angeben:");
//		String modulusBase64 =new String(console.readLine()); // your Base64 string here
//		BigInteger modulus = new BigInteger(1,
//		        Base64.decodeBase64(modulusBase64.getBytes("UTF-8")));
//		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//		RSAPublicKeySpec ks = new RSAPublicKeySpec(modulus, pubExp);
//		RSAPublicKey pubKey = (RSAPublicKey)keyFactory.generatePublic(KeySpec);
//	}
}