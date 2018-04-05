package com.kitchen.market.common.security.encryption.rsa;

import com.kitchen.market.common.security.KitSecretKeyUtil;
import com.kitchen.market.common.security.encryption.RSACipherUtil;

import javax.crypto.Cipher;
import java.io.*;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 根据PrivateKey（私钥字符串）和PublicKey（公钥字符串），对文件进行加密、解密
 * 注：可使用openssl工具生成私钥和公钥
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-05
 */
public class KitEncryptionFileByRSA {
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;
    private static final String RSA = "RSA";

    //-------------------------------------------私钥加密、公钥解密-------------------------------------------
    public static void encryptByPrivateKey(String srcFilePath, String targetFilePath, String privateKeyPKCS8) throws Exception {
        RSACipherUtil.cipherDoFinal(srcFilePath, targetFilePath, createEncryptCipherByPrivateKey(privateKeyPKCS8), MAX_ENCRYPT_BLOCK);
    }

    public static byte[] encryptByPrivateKey(byte[] data, String privateKeyPKCS8) throws Exception {
        return RSACipherUtil.cipherDoFinal(data, createEncryptCipherByPrivateKey(privateKeyPKCS8), MAX_ENCRYPT_BLOCK);
    }

    public static void decryptByPublicKey(String srcFilePath, String targetFilePath, String publicKeyStr) throws Exception {
        RSACipherUtil.cipherDoFinal(srcFilePath, targetFilePath, createDecryptCipherByPublicKey(publicKeyStr), MAX_DECRYPT_BLOCK);
    }
    public static byte[] decryptByPublicKey(byte[] data, String publicKeyStr) throws Exception {
        return RSACipherUtil.cipherDoFinal(data, createDecryptCipherByPublicKey(publicKeyStr), MAX_DECRYPT_BLOCK);
    }

    private static Cipher createEncryptCipherByPrivateKey(String privateKeyPKCS8) throws Exception {
        // 取得私钥
        InputStream privateKeyInputStream = new ByteArrayInputStream(privateKeyPKCS8.getBytes());
        PrivateKey privateKey = KitSecretKeyUtil.getPrivateKeyFromPKCS8(RSA, privateKeyInputStream);

        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher;
    }
    private static Cipher createDecryptCipherByPublicKey(String publicKeyStr) throws Exception {
        PublicKey publicKey = KitSecretKeyUtil.getPublicKeyFromX509(RSA, new ByteArrayInputStream(publicKeyStr.getBytes()));

        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher;
    }
    //-------------------------------------------私钥加密、公钥解密-------------------------------------------

    //-------------------------------------------公钥加密、私钥解密-------------------------------------------
    public static void encryptByPublicKey(String srcFilePath, String targetFilePath, String publicKeyStr) throws Exception {
        RSACipherUtil.cipherDoFinal(srcFilePath, targetFilePath, createEncryptCipherByPublicKey(publicKeyStr), MAX_ENCRYPT_BLOCK);
    }
    public static byte[] encryptByPublicKey(byte[] data, String publicKeyStr) throws Exception {
        return RSACipherUtil.cipherDoFinal(data, createEncryptCipherByPublicKey(publicKeyStr), MAX_ENCRYPT_BLOCK);
    }
    public static void decryptByPrivateKey(String srcFilePath, String targetFilePath, String privateKeyPKCS8) throws Exception {
        RSACipherUtil.cipherDoFinal(srcFilePath, targetFilePath, createDecryptCipherByPrivateKey(privateKeyPKCS8), MAX_DECRYPT_BLOCK);
    }
    public static byte[] decryptByPrivateKey(byte[] data, String privateKeyPKCS8) throws Exception {
        return RSACipherUtil.cipherDoFinal(data, createDecryptCipherByPrivateKey(privateKeyPKCS8), MAX_DECRYPT_BLOCK);
    }
    private static Cipher createEncryptCipherByPublicKey(String publicKeyStr) throws Exception {
        PublicKey publicKey = KitSecretKeyUtil.getPublicKeyFromX509(RSA, new ByteArrayInputStream(publicKeyStr.getBytes()));

        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher;
    }
    private static Cipher createDecryptCipherByPrivateKey(String privateKeyPKCS8) throws Exception {
        // 取得私钥
        InputStream privateKeyInputStream = new ByteArrayInputStream(privateKeyPKCS8.getBytes());
        PrivateKey privateKey = KitSecretKeyUtil.getPrivateKeyFromPKCS8(RSA, privateKeyInputStream);

        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher;
    }
    //-------------------------------------------公钥加密、私钥解密-------------------------------------------
}
