package com.kitchen.market.common.security.encryption.aes;

import com.kitchen.market.common.security.codec.Base64Utils;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-07
 */
public class KitEncryptionTextByAES {
    /**
     * 加密
     * 完整算法 AES/ECB/PKCS5Padding
     * @param plainText
     * @param keyStr
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static String encryptToBase64(String plainText, String keyStr) {
        return encryptToBase64(plainText, keyStr, null, AESWorkingType.ECB, AESPaddingType.PKCS5Padding);
    }
    /**
     * 解密
     * 完整算法 AES/ECB/PKCS5Padding
     *
     * @param base64CipherText
     * @param keyStr
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static String decryptByBase64(String base64CipherText, String keyStr) {
        return decryptByBase64(base64CipherText, keyStr, null, AESWorkingType.ECB, AESPaddingType.PKCS5Padding);
    }

    /**
     * 加密
     * 完整算法 AES/CBC/PKCS5Padding
     * @param plainText
     * @param keyStr
     * @param iv 为NULL则使用默认向量
     * @return
     */
    public static String encryptToBase64(String plainText, String keyStr, String iv) {
        return encryptToBase64(plainText, keyStr, iv, AESWorkingType.CBC, AESPaddingType.PKCS5Padding);
    }

    /**
     * 解密
     * 完整算法 AES/CBC/PKCS5Padding
     * 默认向量
     * @param base64CipherText
     * @param keyStr
     * @param iv 为NULL则使用默认向量
     * @return
     */
    public static String decryptByBase64(String base64CipherText, String keyStr, String iv) {
        return decryptByBase64(base64CipherText, keyStr, iv, AESWorkingType.CBC, AESPaddingType.PKCS5Padding);
    }

    public static String encryptToBase64(String plainText, String keyStr, String iv, AESWorkingType workingType, AESPaddingType paddingType) {
        byte[] bytePlainText = plainText.getBytes();
        byte[] encryptData = AESAlgorithmImpl.encryptText(bytePlainText, keyStr, iv, AESAlgorithmType.AES, workingType, paddingType);
        // Base64处理后返回
        return new String(Base64Utils.encodeBase64(encryptData));
    }
    public static String decryptByBase64(String base64CipherText, String keyStr, String iv, AESWorkingType workingType, AESPaddingType paddingType) {
        // 处理接收到的Base64编码的密文
        byte[] byteCipherText = Base64Utils.decodeBase64(base64CipherText.getBytes());
        byte[] byteDecryptedData = AESAlgorithmImpl.decryptText(byteCipherText, keyStr, iv, AESAlgorithmType.AES, workingType, paddingType);
        return new String(byteDecryptedData);
    }
}
