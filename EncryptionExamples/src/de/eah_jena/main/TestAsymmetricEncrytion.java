package de.eah_jena.main;

//import com.reindel.keys.RSACipher;
//import com.reindel.keys.RSAKeyPair;
import de.eah_jena.source.AsymmetricEncryption;


public class TestAsymmetricEncrytion {

	public static void main(String[] args) throws Exception {

    	//generating the Keys
    	
    	//RSAKeyPair rsaKeyPair = new RSAKeyPair(2048);
       // rsaKeyPair.toFileSystem("Temp//private.key", "Temp//public.key");
    	
        
        AsymmetricEncryption rsaKeyPair = new AsymmetricEncryption();
        rsaKeyPair.KeyPair(2048);
        rsaKeyPair.toFileSystem("Temp//private.key", "Temp//public.key");
        
        //Encryption
        
        AsymmetricEncryption rsaCipherEncryption = new AsymmetricEncryption();         
        rsaCipherEncryption.readClearSaveEncrypt("cleartext.txt", "ciphertextRSA_3.txt", "Temp//public.key", "RSA/ECB/PKCS1Padding", "UTF-8");
        //System.out.println(encrypted);   
        
    	// Decryption
    	
        AsymmetricEncryption rsaCipherDecryption = new AsymmetricEncryption();
        rsaCipherDecryption.readEncryptSaveDecrypt("ciphertextRSA_3.txt", "Temp//private.key", "RSA/ECB/PKCS1Padding", "UTF-8");
         //String decrypted =  // System.out.println(decrypted);  
                  
          
    }
	
	
}
