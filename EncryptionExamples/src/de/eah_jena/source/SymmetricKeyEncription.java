package de.eah_jena.source;

import javax.crypto.*;
//zur Schlüsselgenerierung 
import javax.crypto.spec.SecretKeySpec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Arrays;


public class SymmetricKeyEncription {
	
private Cipher AesCipher;
private byte[] key;
private String keyStr;
private byte[] encryptText;
private SecretKeySpec secretKeySpec;
private BufferedReader console;
private String textStr;
private byte[] byteText;
private MessageDigest sha;

	public SymmetricKeyEncription() throws Exception{
		// TODO Auto-generated constructor stub
		System.out.println("Bitte Schlüssel/Passwort angeben:");
//    	Konsolenobjekt für Eingabe initialisieren
    	BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
//        String keyStr = null;
    //  Ausgangstext einlesen  
    	try {
    		keyStr = new String(console.readLine());
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	System.out.println("\nBitte zu verschlüsselnden Text eingeben: ");
     	try {
    		textStr = new String(console.readLine());
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
		key = (keyStr).getBytes("UTF-8");
		byteText = textStr.getBytes("UTF-8");
		AesCipher = Cipher.getInstance("AES");
//		aus dem  Array einen Hash-Wert mit MD5 oder SHA erzeugen
		sha = MessageDigest.getInstance("MD5");
		key = sha.digest(key);
		// nur die ersten 128 bit nutzen; da AES auf eine feste Größe beschränkt ist
		key = Arrays.copyOf(key, 16); 
		secretKeySpec = new SecretKeySpec(key, "AES");
	}

//    public static void main(String[] args) throws Exception {
//        
//        // Das Passwort 
//        String keyStr = "geheim";
//        // byte-Array erzeugen
//        byte[] key = (keyStr).getBytes("UTF-8");
//        // aus dem Array einen Hash-Wert erzeugen mit MD5 oder SHA
//        MessageDigest sha = MessageDigest.getInstance("MD5");
//        key = sha.digest(key);
//        // nur die ersten 128 bit nutzen; da AES auf eine feste Größe beschränkt ist
//        key = Arrays.copyOf(key, 16); 
//        // der fertige Schluessel
//        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
// 
//        
//
//        //zu verschlüsselnder Text
//        byte[] byteText = "quelle: http://stackoverflow.com/questions/20796042/aes-encryption-and-decryption-with-java".getBytes();
//
//        AesCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
//        
//        //Verschlüsseln und Ausgabe in Console
//        byte[] encryptText = AesCipher.doFinal(byteText);
//        System.out.println("Verschlüsselter Text:");
//        System.out.write(encryptText);
//
////      Milestone
//        //hier liest er den verschlüsselten Text aus, den er entschlüsselt
//        byte[] cipherText = encryptText;
//
//        //Entschlüsseln
//        AesCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
//        byte[] bytePlainText = AesCipher.doFinal(cipherText);
//        
//        //Ausgabe in Console
//        System.out.println("\n \n Entschlüsselter Text:");
//        System.out.write(bytePlainText);
//    }

    
    public void encrypt() throws Exception {
    	
    	// byte-Array erzeugen
        // aus dem Array einen Hash-Wert erzeugen mit MD5 oder SHA
//        MessageDigest sha = MessageDigest.getInstance("MD5");
        // der fertige Schluessel
        //  Ausgangstext einlesen  
            //zu verschlüsselnder Text
//            byte[] byteText = textStr.getBytes();
//          Verschlüsselungsmodus der Cipher-Klasse initialisieren
            AesCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);	
            
            //Verschlüsseln und Ausgabe in Console
            encryptText = AesCipher.doFinal(byteText);
            System.out.println("\nVerschlüsselter Text:");
            
            System.out.write(encryptText);
            System.out.println("\n" + encryptText);
            String encryptString = encryptText.toString();
            System.out.println(encryptString);
            
          
    }
    
    public void decrypt() throws Exception {
    	
    	 //hier liest er den verschlüsselten Text aus, den er entschlüsselt
        byte[] cipherText = encryptText;

        //Entschlüsseln
        AesCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] bytePlainText = AesCipher.doFinal(cipherText);
        
        //Ausgabe in Console
        System.out.println("\n \nEntschlüsselter Text:");
        System.out.write(bytePlainText);
    }
    
    public void decryptCustomEncryptedText() throws Exception {
    	
    BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
       System.out.println("\n \nVerschlüsselter Text:");
//      String cipherTextCustom = new String(console.readLine());
       String inputText =  new String(console.readLine());
       
       //hier liest er den verschlüsselten Text aus, den er entschlüsselt
      
  
       byte[] cipherTextCustom1 = (inputText).getBytes();
       
       System.out.println("\nBitte Schlüssel/Passwort angeben:");
       
      String inputKey =  new String(console.readLine());
       
      byte[] keyCustom = (inputKey).getBytes("UTF-8");
     // aus dem Array einen Hash-Wert erzeugen mit MD5 oder SHA
     keyCustom = sha.digest(keyCustom);
     // nur die ersten 128 bit nutzen; da AES auf eine feste Größe beschränkt ist
     keyCustom = Arrays.copyOf(keyCustom, 16); 
     // der fertige Schluessel
     SecretKeySpec secretKeySpecCustom = new SecretKeySpec(keyCustom, "AES");
    
//       System.out.println(keyCustom.length);
      
       AesCipher.init(Cipher.DECRYPT_MODE, secretKeySpecCustom);
       byte[] bytePlainTextCustom = AesCipher.doFinal(cipherTextCustom1);
       
       //Ausgabe in Console
       System.out.println("\n \nEntschlüsselter Text:");
       System.out.write(bytePlainTextCustom);
   }
}
