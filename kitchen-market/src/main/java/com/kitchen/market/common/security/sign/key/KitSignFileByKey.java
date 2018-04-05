package com.kitchen.market.common.security.sign.key;

import com.kitchen.market.common.security.KitSecretKeyUtil;
import com.kitchen.market.common.security.codec.Base64Utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

/**
 * 根据PrivateKey（私钥字符串）和PublicKey（公钥字符串），对文件进行签名、验签
 * 注：可使用openssl工具生成私钥和公钥
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-05
 */
public class KitSignFileByKey {
    private static final int CACHE_SIZE = 2048;
    //---------------------------------------------SHA1WithRSA签名、验签---------------------------------------------
    public static String rsaSign(String filePath, String privateKey) throws Exception {
        File file = new File(filePath);
        return rsaSign(file, privateKey);
    }
    public static boolean verifyRsaSign(String filePath, String sign, String publicKey) throws Exception {
        File file = new File(filePath);
        return verifyRsaSign(file, sign, publicKey);
    }

    public static String rsaSign(byte[] data, String privateKey) throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        byte[] sign = generateFileSign(inputStream, privateKey, RSAType.RSA);
        return new String(Base64Utils.encodeBase64(sign));
    }
    public static boolean verifyRsaSign(byte[] data, String sign, String publicKey) throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        return validateFileSign(inputStream, sign, publicKey, RSAType.RSA);
    }

    public static String rsaSign(File file, String privateKey) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] sign = generateFileSign(fileInputStream, privateKey, RSAType.RSA);
        return new String(Base64Utils.encodeBase64(sign));
    }
    public static boolean verifyRsaSign(File file, String sign, String publicKey) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        return validateFileSign(fileInputStream, sign, publicKey, RSAType.RSA);
    }
    //---------------------------------------------SHA1WithRSA签名、验签---------------------------------------------

    //--------------------------------------------SHA256WithRSA签名、验签--------------------------------------------
    public static String rsa256Sign(String filePath, String privateKey) throws Exception {
        File file = new File(filePath);
        return rsa256Sign(file, privateKey);
    }
    public static boolean verifyRsa256Sign(String filePath, String sign, String publicKey) throws Exception {
        File file = new File(filePath);
        return verifyRsa256Sign(file, sign, publicKey);
    }

    public static String rsa256Sign(byte[] data, String privateKey) throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        byte[] sign = generateFileSign(inputStream, privateKey, RSAType.RSA256);
        return new String(Base64Utils.encodeBase64(sign));
    }
    public static boolean verifyRsa256Sign(byte[] data, String sign, String publicKey) throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        return validateFileSign(inputStream, sign, publicKey, RSAType.RSA256);
    }

    public static String rsa256Sign(File file, String privateKey) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] sign = generateFileSign(fileInputStream, privateKey, RSAType.RSA256);
        return new String(Base64Utils.encodeBase64(sign));
    }
    public static boolean verifyRsa256Sign(File file, String sign, String publicKey) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        return validateFileSign(fileInputStream, sign, publicKey, RSAType.RSA256);
    }
    //--------------------------------------------SHA256WithRSA签名、验签--------------------------------------------

    private static byte[] generateFileSign(InputStream inputStream, String privateKeyStr, RSAType rsaType) throws Exception {
        PrivateKey privateKey = KitSecretKeyUtil.getPrivateKeyFromPKCS8(rsaType.getName(), new ByteArrayInputStream(privateKeyStr.getBytes()));
        Signature signature = Signature.getInstance(rsaType.getAlgorithms());
        signature.initSign(privateKey);
        byte[] cache = new byte[CACHE_SIZE];
        int nRead = 0;
        while ((nRead = inputStream.read(cache)) != -1) {
            signature.update(cache, 0, nRead);
        }
        inputStream.close();

        byte[] sign = signature.sign();
        return sign;
    }

    private static boolean validateFileSign(InputStream inputStream, String sign, String publicKeyStr, RSAType rsaType) throws Exception {
        PublicKey publicKey = KitSecretKeyUtil.getPublicKeyFromX509(rsaType.getName(), new ByteArrayInputStream(publicKeyStr.getBytes()));
        // 构建签名
        Signature signature = Signature.getInstance(rsaType.getAlgorithms());
        signature.initVerify(publicKey);

        byte[] decodedSign = Base64Utils.decodeBase64(sign.getBytes());
        byte[] cache = new byte[CACHE_SIZE];
        int nRead = 0;
        while ((nRead = inputStream.read(cache)) != -1) {
            signature.update(cache, 0, nRead);
        }
        inputStream.close();

        boolean result = signature.verify(decodedSign);
        return result;
    }
}
