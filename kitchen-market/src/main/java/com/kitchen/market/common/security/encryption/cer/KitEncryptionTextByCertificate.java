package com.kitchen.market.common.security.encryption.cer;

import com.kitchen.market.common.security.KitSecretKeyUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.cert.X509Certificate;
import com.kitchen.market.common.security.codec.Base64Utils;
import com.kitchen.market.common.security.encryption.RSACipherUtil;

/**
 * 根据keystore（密钥库文件）和cer（证书文件），对文本进行加密、解密
 * 注：可使用java的keytool生成keystore和cer
 *
 * 支持：
 * 1、公钥加密——私钥解密
 * 2、私钥加密——公钥解密
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-01
 */
public class KitEncryptionTextByCertificate {
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;//解密byte数组最大长度限制: 128

    //-------------------------------------------私钥加密、公钥解密-------------------------------------------
    /**
     * 通过密钥库（私钥）加密
     * @param text
     * @param keyStorePath
     * @param alias
     * @param password
     * @return
     * @throws Exception
     */
    public static String encryptByKeyStore(String text, String keyStorePath, String alias, String password) throws Exception {
        byte[] data = text.getBytes();
        // 取得私钥
        PrivateKey privateKey = KitSecretKeyUtil.getPrivateKey(keyStorePath, alias, password);
        return encryptByPrivateKey(data, privateKey);
    }
    public static String encryptByKeyStore(String text, KeyStore keyStore, String alias, String password) throws Exception {
        byte[] data = text.getBytes();
        PrivateKey privateKey = KitSecretKeyUtil.getPrivateKey(keyStore, alias, password);
        return encryptByPrivateKey(data, privateKey);
    }
    private static String encryptByPrivateKey(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        byte[] encryptedData = RSACipherUtil.sectionCipher(data, cipher, MAX_ENCRYPT_BLOCK);
        return new String(Base64Utils.encodeBase64(encryptedData));
    }
    /**
     * 通过证书（公钥）解密
     * @param encryptedText
     * @param certificatePath
     * @return
     * @throws Exception
     */
    public static String decryptByCertificate(String encryptedText, String certificatePath) throws Exception {
        byte[] encryptedData = Base64Utils.decodeBase64(encryptedText.getBytes());
        PublicKey publicKey = KitSecretKeyUtil.getPublicKey(certificatePath);

        return decryptByPublicKey(encryptedData, publicKey);
    }
    public static String decryptByCertificate(String encryptedText, X509Certificate certificate) throws Exception {
        byte[] encryptedData = Base64Utils.decodeBase64(encryptedText.getBytes());
        PublicKey publicKey = KitSecretKeyUtil.getPublicKey(certificate);

        return decryptByPublicKey(encryptedData, publicKey);
    }
    private static String decryptByPublicKey(byte[] encryptedData, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        byte[] decryptedData = RSACipherUtil.sectionCipher(encryptedData, cipher, MAX_DECRYPT_BLOCK);
        return new String(decryptedData);
    }
    //-------------------------------------------私钥加密、公钥解密-------------------------------------------


    //-------------------------------------------公钥加密、私钥解密-------------------------------------------
    /**
     * 通过证书（公钥）加密
     * @param text
     * @param certificatePath
     * @return
     * @throws Exception
     */
    public static String encryptByCertificate(String text, String certificatePath) throws Exception {
        byte[] data = text.getBytes();
        // 取得公钥
        PublicKey publicKey = KitSecretKeyUtil.getPublicKey(certificatePath);
        return encryptByPublicKey(data, publicKey);
    }
    public static String encryptByCertificate(String text, X509Certificate certificate) throws Exception {
        byte[] data = text.getBytes();
        return encryptByPublicKey(data, certificate.getPublicKey());
    }
    private static String encryptByPublicKey(byte[] data, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptedData = RSACipherUtil.sectionCipher(data, cipher, MAX_ENCRYPT_BLOCK);
        return new String(Base64Utils.encodeBase64(encryptedData));
    }
    /**
     * 通过密钥库（私钥）解密
     * @param encryptedText
     * @param keyStorePath
     * @param alias
     * @param password
     * @return
     * @throws Exception
     */
    public static String decryptByKeyStore(String encryptedText, String keyStorePath, String alias, String password) throws Exception {
        byte[] encryptedData = Base64Utils.decodeBase64(encryptedText.getBytes());
        // 取得私钥
        PrivateKey privateKey = KitSecretKeyUtil.getPrivateKey(keyStorePath, alias, password);
        return decryptByPrivateKey(encryptedData, privateKey);
    }
    public static String decryptByKeyStore(String encryptedText, KeyStore keyStore, String alias, String password) throws Exception {
        byte[] encryptedData = Base64Utils.decodeBase64(encryptedText.getBytes());
        PrivateKey privateKey = KitSecretKeyUtil.getPrivateKey(keyStore, alias, password);
        return decryptByPrivateKey(encryptedData, privateKey);
    }
    private static String decryptByPrivateKey(byte[] encryptedData, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException {
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] decryptedData = RSACipherUtil.sectionCipher(encryptedData, cipher, MAX_DECRYPT_BLOCK);
        return new String(decryptedData);
    }
    //-------------------------------------------公钥加密、私钥解密-------------------------------------------
}
