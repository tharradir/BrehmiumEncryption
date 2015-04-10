package de.eah_jena.source;

import javax.crypto.*;
//zur Schlüsselgenerierung 
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;


public class SymmetricKeyEncription {

    public static void main(String[] args) throws Exception {
        
        // Das Passwort 
        String keyStr = "geheim";
        // byte-Array erzeugen
        byte[] key = (keyStr).getBytes("UTF-8");
        // aus dem Array einen Hash-Wert erzeugen mit MD5 oder SHA
        MessageDigest sha = MessageDigest.getInstance("MD5");
        key = sha.digest(key);
        // nur die ersten 128 bit nutzen; da AES auf eine feste Größe beschränkt ist
        key = Arrays.copyOf(key, 16); 
        // der fertige Schluessel
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
 
        
        Cipher AesCipher = Cipher.getInstance("AES");

        //zu verschlüsselnder Text
        byte[] byteText = "quelle: http://stackoverflow.com/questions/20796042/aes-encryption-and-decryption-with-java".getBytes();

        AesCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        
        //Verschlüsseln und Ausgabe in Console
        byte[] encryptText = AesCipher.doFinal(byteText);
        System.out.println("Verschlüsselter Text:");
        System.out.write(encryptText);

      
        //hier liest er die Verschlüsselte text aus, die er entschlüsselt
        byte[] cipherText = encryptText;

        //Entschlüsseln
        AesCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] bytePlainText = AesCipher.doFinal(cipherText);
        
        //Ausgabe in Console
        System.out.println("\n \n Entschlüsselter Text:");
        System.out.write(bytePlainText);
    }
}
