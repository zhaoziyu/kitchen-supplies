package com.kitchen.market.common.security.encryption.rsa;

import com.kitchen.market.common.security.KitSecretKeyUtil;
import com.kitchen.market.common.security.codec.Base64Utils;
import com.kitchen.market.common.security.encryption.RSACipherUtil;

import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 根据PrivateKey（私钥字符串）和PublicKey（公钥字符串），对文本进行加密、解密
 * 注：可使用openssl工具生成私钥和公钥
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-05
 */
public class KitEncryptionTextByRSA {
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;//解密byte数组最大长度限制: 128
    private static final String RSA = "RSA";

    //-------------------------------------------私钥加密、公钥解密-------------------------------------------
    public static String encryptByPrivateKey(String text, String privateKeyPKCS8) throws Exception {
        // 取得私钥
        InputStream privateKeyInputStream = new ByteArrayInputStream(privateKeyPKCS8.getBytes());
        PrivateKey privateKey = KitSecretKeyUtil.getPrivateKeyFromPKCS8(RSA, privateKeyInputStream);

        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        byte[] data = text.getBytes();
        byte[] encryptedData = RSACipherUtil.sectionCipher(data, cipher, MAX_ENCRYPT_BLOCK);
        return new String(Base64Utils.encodeBase64(encryptedData));
    }

    public static String decryptByPublicKey(String encryptedText, String publicKeyStr) throws Exception {
        PublicKey publicKey = KitSecretKeyUtil.getPublicKeyFromX509(RSA, new ByteArrayInputStream(publicKeyStr.getBytes()));

        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        byte[] encryptedData = Base64Utils.decodeBase64(encryptedText.getBytes());
        byte[] decryptedData = RSACipherUtil.sectionCipher(encryptedData, cipher, MAX_DECRYPT_BLOCK);
        return new String(decryptedData);
    }
    //-------------------------------------------私钥加密、公钥解密-------------------------------------------

    //-------------------------------------------公钥加密、私钥解密-------------------------------------------
    public static String encryptByPublicKey(String text, String publicKeyStr) throws Exception {
        PublicKey publicKey = KitSecretKeyUtil.getPublicKeyFromX509(RSA, new ByteArrayInputStream(publicKeyStr.getBytes()));
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] data = text.getBytes();
        byte[] encryptedData = RSACipherUtil.sectionCipher(data, cipher, MAX_ENCRYPT_BLOCK);
        return new String(Base64Utils.encodeBase64(encryptedData));
    }
    public static String decryptByPrivateKey(String encryptedText, String privateKeyPKCS8) throws Exception {
        // 取得私钥
        InputStream privateKeyInputStream = new ByteArrayInputStream(privateKeyPKCS8.getBytes());
        PrivateKey privateKey = KitSecretKeyUtil.getPrivateKeyFromPKCS8(RSA, privateKeyInputStream);

        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] encryptedData = Base64Utils.decodeBase64(encryptedText.getBytes());
        byte[] decryptedData = RSACipherUtil.sectionCipher(encryptedData, cipher, MAX_DECRYPT_BLOCK);
        return new String(decryptedData);
    }
    //-------------------------------------------公钥加密、私钥解密-------------------------------------------
}
