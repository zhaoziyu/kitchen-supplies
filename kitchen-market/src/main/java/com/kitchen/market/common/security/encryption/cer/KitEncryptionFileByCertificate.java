package com.kitchen.market.common.security.encryption.cer;

import com.kitchen.market.common.security.KitSecretKeyUtil;
import com.kitchen.market.common.security.encryption.RSACipherUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.cert.X509Certificate;

/**
 * 根据keystore（密钥库文件）和cer（证书文件），对文件进行加密、解密
 * 注：可使用java的keytool生成keystore和cer
 *
 * 支持：
 * 1、私钥加密文件——公钥解密文件
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-01
 */
public class KitEncryptionFileByCertificate {
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;

    //-------------------------------------------私钥加密、公钥解密-------------------------------------------
    /**
     * 私钥加密文件
     * @param srcFilePath
     * @param encryptFilePath
     * @param keyStorePath
     * @param alias
     * @param password
     * @throws Exception
     */
    public static void encryptByKeyStore(String srcFilePath, String encryptFilePath, String keyStorePath, String alias, String password) throws Exception {
        // 取得私钥
        PrivateKey privateKey = KitSecretKeyUtil.getPrivateKey(keyStorePath, alias, password);
        // 加密文件
        encryptByPrivateKey(srcFilePath, encryptFilePath, privateKey);
    }
    public static void encryptByKeyStore(String srcFilePath, String encryptFilePath, KeyStore keyStore, String alias, String password) throws Exception {
        // 取得私钥
        PrivateKey privateKey = KitSecretKeyUtil.getPrivateKey(keyStore, alias, password);
        // 加密文件
        encryptByPrivateKey(srcFilePath, encryptFilePath, privateKey);
    }
    private static void encryptByPrivateKey(String srcFilePath, String targetFilePath, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        RSACipherUtil.cipherDoFinal(srcFilePath, targetFilePath, cipher, MAX_ENCRYPT_BLOCK);
    }
    public static byte[] encryptByKeyStore(byte[] data, String keyStorePath, String alias, String password) throws Exception {
        // 取得私钥
        PrivateKey privateKey = KitSecretKeyUtil.getPrivateKey(keyStorePath, alias, password);
        return encryptByPrivateKey(data, privateKey);
    }
    public static byte[] encryptByKeyStore(byte[] data, KeyStore keyStore, String alias, String password) throws Exception {
        // 取得私钥
        PrivateKey privateKey = KitSecretKeyUtil.getPrivateKey(keyStore, alias, password);
        return encryptByPrivateKey(data, privateKey);
    }
    private static byte[] encryptByPrivateKey(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return RSACipherUtil.cipherDoFinal(data, cipher, MAX_ENCRYPT_BLOCK);
    }

    /**
     * 公钥解密文件
     * @param encryptFilePath
     * @param srcFilePath
     * @param certificatePath
     * @throws Exception
     */
    public static void decryptByCertificate(String encryptFilePath, String srcFilePath, String certificatePath) throws Exception {
        // 获取公钥
        PublicKey publicKey = KitSecretKeyUtil.getPublicKey(certificatePath);
        // 解密文件
        decryptByPublicKey(encryptFilePath, srcFilePath, publicKey);
    }
    public static void decryptByCertificate(String encryptFilePath, String srcFilePath, X509Certificate certificate) throws Exception {
        // 获取公钥
        PublicKey publicKey = KitSecretKeyUtil.getPublicKey(certificate);
        // 解密文件
        decryptByPublicKey(encryptFilePath, srcFilePath, publicKey);
    }
    private static void decryptByPublicKey(String srcFilePath, String targetFilePath, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        RSACipherUtil.cipherDoFinal(srcFilePath, targetFilePath, cipher, MAX_DECRYPT_BLOCK);
    }
    public static byte[] decryptByCertificate(byte[] data, String certificatePath) throws Exception {
        // 获取公钥
        PublicKey publicKey = KitSecretKeyUtil.getPublicKey(certificatePath);
        // 解密文件
        return decryptByPublicKey(data, publicKey);
    }
    public static byte[] decryptByCertificate(byte[] data, X509Certificate certificate) throws Exception {
        // 获取公钥
        PublicKey publicKey = KitSecretKeyUtil.getPublicKey(certificate);
        // 解密文件
        return decryptByPublicKey(data, publicKey);
    }
    private static byte[] decryptByPublicKey(byte[] data, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return RSACipherUtil.cipherDoFinal(data, cipher, MAX_DECRYPT_BLOCK);
    }
    //-------------------------------------------私钥加密、公钥解密-------------------------------------------

    //-------------------------------------------公钥加密、私钥解密-------------------------------------------
    /**
     * 通过证书（公钥）加密
     * @return
     * @throws Exception
     */
    public static void encryptByCertificate(String srcFilePath, String encryptFilePath, String certificatePath) throws Exception {
        // 取得公钥
        PublicKey publicKey = KitSecretKeyUtil.getPublicKey(certificatePath);
        encryptByPublicKey(srcFilePath, encryptFilePath, publicKey);
    }
    public static void encryptByCertificate(String srcFilePath, String encryptFilePath, X509Certificate certificate) throws Exception {
        encryptByPublicKey(srcFilePath, encryptFilePath, certificate.getPublicKey());
    }
    private static void encryptByPublicKey(String srcFilePath, String targetFilePath, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        RSACipherUtil.cipherDoFinal(srcFilePath, targetFilePath, cipher, MAX_ENCRYPT_BLOCK);
    }
    public static byte[] encryptByCertificate(byte[] data, String certificatePath) throws Exception {
        // 取得公钥
        PublicKey publicKey = KitSecretKeyUtil.getPublicKey(certificatePath);
        return encryptByPublicKey(data, publicKey);
    }
    public static byte[] encryptByCertificate(byte[] data, X509Certificate certificate) throws Exception {
        return encryptByPublicKey(data, certificate.getPublicKey());
    }
    private static byte[] encryptByPublicKey(byte[] data, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return RSACipherUtil.cipherDoFinal(data, cipher, MAX_ENCRYPT_BLOCK);
    }
    /**
     * 通过密钥库（私钥）解密
     * @param keyStorePath
     * @param alias
     * @param password
     * @return
     * @throws Exception
     */
    public static void decryptByKeyStore(String encryptFilePath, String srcFilePath, String keyStorePath, String alias, String password) throws Exception {
        // 取得私钥
        PrivateKey privateKey = KitSecretKeyUtil.getPrivateKey(keyStorePath, alias, password);
        decryptByPrivateKey(encryptFilePath, srcFilePath, privateKey);
    }
    public static void decryptByKeyStore(String encryptFilePath, String srcFilePath, KeyStore keyStore, String alias, String password) throws Exception {
        PrivateKey privateKey = KitSecretKeyUtil.getPrivateKey(keyStore, alias, password);
        decryptByPrivateKey(encryptFilePath, srcFilePath, privateKey);
    }
    private static void decryptByPrivateKey(String srcFilePath, String targetFilePath, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        RSACipherUtil.cipherDoFinal(srcFilePath, targetFilePath, cipher, MAX_DECRYPT_BLOCK);
    }
    public static byte[] decryptByKeyStore(byte[] data, String keyStorePath, String alias, String password) throws Exception {
        // 取得私钥
        PrivateKey privateKey = KitSecretKeyUtil.getPrivateKey(keyStorePath, alias, password);
        return decryptByPrivateKey(data, privateKey);
    }
    public static byte[] decryptByKeyStore(byte[] data, KeyStore keyStore, String alias, String password) throws Exception {
        PrivateKey privateKey = KitSecretKeyUtil.getPrivateKey(keyStore, alias, password);
        return decryptByPrivateKey(data, privateKey);
    }
    private static byte[] decryptByPrivateKey(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return RSACipherUtil.cipherDoFinal(data, cipher, MAX_DECRYPT_BLOCK);
    }
    //-------------------------------------------公钥加密、私钥解密-------------------------------------------
}
