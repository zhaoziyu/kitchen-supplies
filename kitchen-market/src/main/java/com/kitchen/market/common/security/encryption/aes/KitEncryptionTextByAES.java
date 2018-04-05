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
     * 完整算法 AES/CBC/PKCS5Padding
     * @param plainText
     * @param keyStr
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static String encryptToBase64(String plainText, String keyStr) throws GeneralSecurityException, IOException {
        byte[] bytePlainText = plainText.getBytes();
        byte[] byteSeyStr = keyStr.getBytes();
        byte[] encryptData = AESAlgorithmImpl.encryptText(bytePlainText, byteSeyStr, AESAlgorithmType.AES, AESWorkingType.CBC, AESPaddingType.PKCS5Padding);
        // Base64处理后返回
        return new String(Base64Utils.encodeBase64(encryptData));
    }

    /**
     * 解密
     * 完整算法 AES/CBC/PKCS5Padding
     *
     * @param base64CipherText
     * @param keyStr
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static String decryptByBase64(String base64CipherText, String keyStr) throws GeneralSecurityException, IOException {
        // 处理接收到的Base64编码的密文
        byte[] byteCipherText = Base64Utils.decodeBase64(base64CipherText.getBytes());
        byte[] byteSeyStr = keyStr.getBytes();
        byte[] byteDecryptedData = AESAlgorithmImpl.decryptText(byteCipherText, byteSeyStr, AESAlgorithmType.AES, AESWorkingType.CBC, AESPaddingType.PKCS5Padding);
        return new String(byteDecryptedData);
    }
}
