package com.kitchen.market.common.security.encryption.des;

import com.kitchen.market.common.security.codec.Base64Utils;

/**
 * 3DES对称加密、解密工具类
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-07
 */
public class KitEncryptionTextBy3DES {
    /**
     * 加密。
     * 完整算法 3DES/CBC/PKCS5Padding
     *
     * @param plainText
     * @param keyStr
     * @return
     */
    public static String encryptToBase64(String plainText, String keyStr) {
        byte[] bytePlainText = plainText.getBytes();
        byte[] byteSeyStr = keyStr.getBytes();
        byte[] encryptData = DESAlgorithmImpl.encryptText(bytePlainText, byteSeyStr, DESAlgorithmType.DESede, DESWorkingType.CBC, DESPaddingType.PKCS5Padding);
        // Base64处理后返回
        return new String(Base64Utils.encodeBase64(encryptData));
    }

    /**
     * 解密。
     * 完整算法 3DES/CBC/PKCS5Padding
     *
     * @param base64CipherText
     * @param keyStr
     * @return
     */
    public static String decryptByBase64(String base64CipherText, String keyStr) {
        // 处理接收到的Base64编码的密文
        byte[] byteCipherText = Base64Utils.decodeBase64(base64CipherText.getBytes());
        byte[] byteSeyStr = keyStr.getBytes();
        byte[] byteDecryptedData = DESAlgorithmImpl.decryptText(byteCipherText, byteSeyStr, DESAlgorithmType.DESede, DESWorkingType.CBC, DESPaddingType.PKCS5Padding);
        return new String(byteDecryptedData);
    }
}
