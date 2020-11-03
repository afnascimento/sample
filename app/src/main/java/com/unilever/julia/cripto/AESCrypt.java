package com.unilever.julia.cripto;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class AESCrypt {

    private static final Charset charset = StandardCharsets.UTF_8;
    private static final String algorithm = "AES";
    private static final String cipherTransformation = "AES/CBC/PKCS5PADDING";

    private static final String key = "SMKCUITQSBGNWOVESMKCUITQSBGNWOVE";
    private static final String initVector = "RandomInitVector";

    private IAESBase64 base64;
    //private IvParameterSpec iv;
    //private SecretKeySpec skeySpec;

    private Cipher cipherEncrypt;
    private Cipher cipherDecrypt;

    public AESCrypt(IAESBase64 base64) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(charset));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(charset), algorithm);

            this.cipherEncrypt = Cipher.getInstance(cipherTransformation);
            this.cipherEncrypt.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            this.cipherDecrypt = Cipher.getInstance(cipherTransformation);
            this.cipherDecrypt.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            this.base64 = base64;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public String encrypt(String value) throws AESException {
        try {
            //IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(charset));
            //SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(charset), algorithm);

            //Cipher cipher = Cipher.getInstance(cipherTransformation);
            //cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipherEncrypt.doFinal(value.getBytes());
            return base64.encodeBase64String(encrypted);
        } catch (Throwable e) {
            throw new AESException("encrypt exception cause --> "+e.getMessage(), e);
        }
    }

    public String decrypt(String encrypted) throws AESException {
        try {
            //IvParameterSpec iv = new IvParameterSpec(initVector.getBytes(charset));
            //SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(charset), algorithm);

            //Cipher cipher = Cipher.getInstance(cipherTransformation);
            //cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipherDecrypt.doFinal(base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Throwable e) {
            throw new AESException("decrypt exception cause --> "+e.getMessage(), e);
        }
    }
}