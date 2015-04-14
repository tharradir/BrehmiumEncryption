package de.eah_jena.source;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;
// import java.util.Base64; erst ab Java 8 (1.8)
import org.apache.commons.codec.binary.Base64;

@SuppressWarnings("unused")
public class SymmetricKeyEncription {

	private Cipher AesCipher;
	private byte[] key;
	private String keyStr;
	private byte[] encryptText;
	private byte[] base64Cipher;
	private SecretKeySpec secretKeySpec;
	private BufferedReader console;
	private String textStr;
	private byte[] byteText;
	private MessageDigest sha;

	public SymmetricKeyEncription() throws Exception {


		// Konsolenobjekt für Eingabe initialisieren
		BufferedReader console = new BufferedReader(new InputStreamReader(
				System.in));

		// Schlüsselwort abfragen
		System.out.println("Bitte Schlüssel/Passwort angeben:");
		try {
			keyStr = new String(console.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Ausgangstext einlesen
		System.out.println("\nBitte zu verschlüsselnden Text eingeben: ");
		try {
			textStr = new String(console.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Schlüssel-String in Byte-Format umwandeln
		key = (keyStr).getBytes("UTF-8");

		// Text-String in Byte-Format umwandeln
		byteText = textStr.getBytes("UTF-8");

		// Objekt AesCipher Verschlüsselungsverfahren zuweisen
		AesCipher = Cipher.getInstance("AES");

		// aus dem Array einen Hash-Wert mit MD5 oder SHA erzeugen
		sha = MessageDigest.getInstance("MD5");
		key = sha.digest(key);

		// nur die ersten 128 bit (16 Byte) nutzen; da AES auf eine feste Größe beschränkt
		key = Arrays.copyOf(key, 16);

		// geheimen Schlüssel aus eingegebenem Schlüsseltext erzeugen
		secretKeySpec = new SecretKeySpec(key, "AES");
	}

	public void encrypt() throws Exception {

		// Verschlüsselungsmodus der Cipher-Klasse initialisieren
		AesCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

		// Verschlüsseln
		encryptText = AesCipher.doFinal(byteText);
		
/**
 * 	Nutzbar ab Java 8 ohne weiteren import einer Fremden .jar
 * 
 * 	byte[] bytes = encryptText.getBytes("UTF-8");
 * 	String encoded = Base64.getEncoder().encodeToString(bytes);
 * 	byte[] decoded = Base64.getDecoder().decode(encoded);
 * 
 * 
 */
		// Codierung mittels Base64 zu 8 bit-Binärdaten
		base64Cipher = Base64.encodeBase64(encryptText);

		// Ausgabe in Console
		System.out.println("\nVerschlüsselter Text:");
		System.out.write(encryptText);
		System.out.println("\n");		
		System.out.write(base64Cipher);

	}

	public void decrypt() throws Exception {

		// hier liest er den verschlüsselten Text aus, den er entschlüsselt
		byte[] cipherText = encryptText;
		
		// Entschlüsseln
		AesCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
		byte[] bytePlainText = AesCipher.doFinal(cipherText);

		// Ausgabe in Console
		System.out.println("\n \nEntschlüsselter Text:");
		System.out.write(bytePlainText);
	}

	public void decryptCustomEncryptedText() throws Exception {

		// Konsolenobjekt erzeugen
		BufferedReader console = new BufferedReader(new InputStreamReader(
				System.in));

		// verschlüsselten Text abfragen
		System.out.println("\n \nVerschlüsselter Text:");
		String inputText = new String(console.readLine());
		
		// verschlüsselten Text Base64 decodieren und in Byte-Format umwandeln
		byte[] cipherTextCustom1 = Base64.decodeBase64(inputText.getBytes());
		
		//byte[] cipherTextCustom1 = (inputText).getBytes();

		// Schlüssel/Passwort der Verschlüsselung abfragen
		System.out.println("\nBitte Schlüssel/Passwort angeben:");
		String inputKey = new String(console.readLine());

		// Schlüssel in Byte-Format umwandeln
		byte[] keyCustom = (inputKey).getBytes("UTF-8");

		// aus dem Array einen Hash-Wert erzeugen mit MD5 oder SHA
		keyCustom = sha.digest(keyCustom);

		// nur die ersten 128 bit nutzen; da AES auf eine feste Größe beschränkt
		keyCustom = Arrays.copyOf(keyCustom, 16);

		// den geheimen Schluessel erzeugen
		SecretKeySpec secretKeySpecCustom = new SecretKeySpec(keyCustom, "AES");

		// "Entschlüsselungsmodus" initialisieren und entschlüsseln
		AesCipher.init(Cipher.DECRYPT_MODE, secretKeySpecCustom);
		byte[] bytePlainTextCustom = AesCipher.doFinal(cipherTextCustom1);

		// Ausgabe in Console
		System.out.println("\nEntschlüsselter Text:");
		System.out.write(bytePlainTextCustom);
	}
}
