/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.marista.sigma.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Administrador
 */
public class MaristaCifra {

    private final String CRYPTOGRAPHY_ALGO_DES = "DES";
    private Cipher cipher = null;
    private DESKeySpec keySpec = null;
    private SecretKeyFactory keyFactory = null;
    private String a = "ALOKDFJUU3NC87EHWGDKEMUSHYDEN3W";

    public String encrypt(String inputString)
            throws Exception {
        String encryptedValue = null;
        SecretKey key = getSecretKey(a);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] inputBytes = inputString.getBytes();
        byte[] outputBytes = cipher.doFinal(inputBytes);
        encryptedValue = Base64.encodeBase64String(outputBytes);
        return encryptedValue;
    }

    public String decrypt(String encryptedString) throws Exception {
        String decryptedValue = "";
        // When Base64Encoded strings are passed in URLs, '+' character gets 
        // converted to space and so we need to reconvert the space to '+' and 
        // since encoded string cannot have space in it so we are 
        // completely safe.
        encryptedString = encryptedString.replace(' ', '+');
        SecretKey key = getSecretKey(a);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] recoveredBytes = cipher.doFinal(Base64.decodeBase64(encryptedString));
        decryptedValue = new String(recoveredBytes);
        return decryptedValue;
    }

    private SecretKey getSecretKey(String secretPassword)
            throws Exception {
        SecretKey key = null;
        cipher = Cipher.getInstance(CRYPTOGRAPHY_ALGO_DES);
        keySpec = new DESKeySpec(secretPassword.getBytes("UTF8"));
        keyFactory = SecretKeyFactory.getInstance(CRYPTOGRAPHY_ALGO_DES);
        key = keyFactory.generateSecret(keySpec);
        return key;
    }
}
