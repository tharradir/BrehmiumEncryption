package de.eah_jena.source;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import java.io.File;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class AsymmetricEncryption {

    private int keyLength;    
    private PrivateKey privateKey;    
    private PublicKey publicKey;
    
   // Constructor for KeyGeneration ??
	public void KeyPair(int keyLength)
            throws GeneralSecurityException {
        
        this.keyLength = keyLength;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(this.keyLength);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        privateKey = keyPair.getPrivate();
        publicKey = keyPair.getPublic();
    }
    
    public final PrivateKey getPrivateKey() {
        return privateKey;
    }
    
    public final PublicKey getPublicKey() {
        return publicKey;
    }
    
    /**
	 * Save your private and public key to a file. 
	 * The strings must not be null.
	 * 
	 * @param privateKeyPathName String -> you can use "//" to switch the folder
	 * @param publicKeyPathName String 
	 */
    public final void toFileSystem(String privateKeyPathName, String publicKeyPathName)
            throws IOException {
        
        FileOutputStream privateKeyOutputStream = null;
        FileOutputStream publicKeyOutputStream = null;
        
        try {
        
            File privateKeyFile = new File(privateKeyPathName);
            File publicKeyFile = new File(publicKeyPathName);

            privateKeyOutputStream = new FileOutputStream(privateKeyFile);
            privateKeyOutputStream.write(privateKey.getEncoded());

            publicKeyOutputStream = new FileOutputStream(publicKeyFile);
            publicKeyOutputStream.write(publicKey.getEncoded());
            
        } catch(IOException ioException) {
            throw ioException;
        } finally {
        
            try {
                
                if (privateKeyOutputStream != null) {
                    privateKeyOutputStream.close();
                }
                if (publicKeyOutputStream != null) {
                    publicKeyOutputStream.close();
                }   
                
            } catch(IOException ioException) {
                throw ioException;
            }
        }
    }
	
    /**
	 * Read a encrypted .txt file and write its content to decrypted .txt file.
	 * The strings must not be null.
	 * 
	 * @param cipherTextPath Path to encrypted .txt file
	 * @param cleartextAgainPath  Path to decrypted .txt file
	 * @param privateKeyPath 
	 * @param transformation always includes the name of a cryptographic algorithm and may be followed by a feedback mode and padding scheme
	 * @param encoding
	 */
	public void readEncryptSaveDecrypt (String cipherTextPath, String cleartextAgainPath, String privateKeyPath, String transformation, String encoding)
            throws IOException, GeneralSecurityException {

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(IOUtils.toByteArray(new FileInputStream(privateKeyPath)));
                
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec));

        // return new String(cipher.doFinal(Base64.decodeBase64(cipherText)), encoding); 
        
        //start saving
        
		FileInputStream fis = new FileInputStream(cipherTextPath);
		System.out.println("Lesen von "+ cipherTextPath);
		System.out.println("Inhalt:");
		int content;
		while ((content = fis.read()) != -1) {
			// convert to char and display it
			System.out.print((char) content);
		}
		System.out.println();

		CipherInputStream cis = new CipherInputStream(fis, cipher);
		FileOutputStream fos = new FileOutputStream(cleartextAgainPath);
		
    	byte[] block = new byte[32];
    	int i;
		while ((i = cis.read(block)) != -1) {
			fos.write(block, 0, i);
		}	
	    
		fos.close();
		cis.close();	
		
		System.out.println();
		System.out.println("\nEntschlüsselten Text gespeichert in "+ cleartextAgainPath);
        // end saving
        
}

	/**
	 * Read clear text from .txt file and write its content encrypted in another .txt file.
	 * The strings must not be null.
	 * 
	 * @param rawTextPath  Path to a .txt file with text use "//" to switch folder
	 * @param encryptTextPath  Path to a .txt file
	 * @param publicKeyPath 
	 * @param transformation always includes the name of a cryptographic algorithm and may be followed by a feedback mode and padding scheme
	 * @param encoding
	 */
	public void readClearSaveEncrypt (String rawTextPath, String encryptTextPath, String publicKeyPath, String transformation, String encoding)throws Exception
{
	
    X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(IOUtils.toByteArray(new FileInputStream(publicKeyPath)));

    Cipher cipher = Cipher.getInstance(transformation);
    cipher.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec));
	
    //return Base64.encodeBase64String(cipher.doFinal(rawText.getBytes(encoding)));
	
    
    FileInputStream fis = new FileInputStream(rawTextPath);
    System.out.println("Lesen von "+ rawTextPath);
    System.out.println("Inhalt:");
	int content;
	while ((content = fis.read()) != -1) {
		// convert to char and display it
		System.out.print((char) content);
	}
	System.out.println();
    
    FileOutputStream fos = new FileOutputStream(encryptTextPath);
    CipherOutputStream cos = new CipherOutputStream(fos, cipher);

    byte[] block = new byte[32];
    int i;
    while ((i = fis.read(block)) != -1) {
	cos.write(block, 0, i);
    }
    	cos.close();
    	fis.close();
    	
    	
    System.out.println("\nVerschlüsselungsprogramm erfolgreich ausgeführt.");
    System.out.println();
}
	
}
