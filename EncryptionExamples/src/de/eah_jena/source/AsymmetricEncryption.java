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
	
	
	public void readEncryptSaveDecrypt (String cipherTextPath, String privateKeyPath, String transformation, String encoding)
            throws IOException, GeneralSecurityException {

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(IOUtils.toByteArray(new FileInputStream(privateKeyPath)));

        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec));

        //start saving
        
    	String ciphertextFile = cipherTextPath;
        String cleartextAgainFile = "cleartextAgainRSA_neuneu.txt";

		FileInputStream fis = new FileInputStream(ciphertextFile);
		System.out.println("Lesen von "+ ciphertextFile);
		CipherInputStream cis = new CipherInputStream(fis, cipher);
		FileOutputStream fos = new FileOutputStream(cleartextAgainFile);
		
    	byte[] block = new byte[32];
    	int i;
		while ((i = cis.read(block)) != -1) {
			fos.write(block, 0, i);
		}
		fos.close();
		cis.close();
		System.out.println("Verschlüsselten Text gespeichert in "+ cleartextAgainFile);
        // end saving
        
		System.out.println("Entschlüsselungsprogramm erfolgreich ausgeführt.");
}


	public void readClearSaveEncrypt (String rawTextPath, String encryptTextPath, String publicKeyPath, String transformation, String encoding)throws Exception
{
	
    X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(IOUtils.toByteArray(new FileInputStream(publicKeyPath)));

    Cipher cipher = Cipher.getInstance(transformation);
    cipher.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec));
	
	
    String cleartextFile = rawTextPath; //"cleartext.txt";
    //String ciphertextFile = "ciphertextRSA_neu.txt";


    FileInputStream fis = new FileInputStream(cleartextFile);
    FileOutputStream fos = new FileOutputStream(encryptTextPath);
    CipherOutputStream cos = new CipherOutputStream(fos, cipher);

    byte[] block = new byte[32];
    int i;
    while ((i = fis.read(block)) != -1) {
	cos.write(block, 0, i);
    }
    	cos.close();
    	fis.close();
    	
    	
    System.out.println("Verschlüsselungsprogramm erfolgreich ausgeführt.");

}
	
}
