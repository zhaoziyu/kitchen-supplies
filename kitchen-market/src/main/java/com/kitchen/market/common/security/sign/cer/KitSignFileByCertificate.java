package com.kitchen.market.common.security.sign.cer;

import com.kitchen.market.common.security.KitSecretKeyUtil;
import com.kitchen.market.common.security.codec.Base64Utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.*;
import java.security.cert.X509Certificate;

/**
 * 根据keystore（密钥库文件）和cer（证书文件），对文件进行签名、验签
 * 注：可使用java的keytool生成keystore和cer
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-01
 */
public class KitSignFileByCertificate {
    private static final int CACHE_SIZE = 2048;

    public static String sign(File file, KeyStore keyStore, String alias, String password) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] sign = generateFileSign(fileInputStream, keyStore, alias, password);
        return new String(Base64Utils.encodeBase64(sign));
    }
    public static String sign(String filePath, KeyStore keyStore, String alias, String password) throws Exception {
        File file = new File(filePath);
        return sign(file, keyStore, alias, password);
    }
    public static String sign(byte[] data, KeyStore keyStore, String alias, String password) throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        byte[] sign = generateFileSign(inputStream, keyStore, alias, password);
        return new String(Base64Utils.encodeBase64(sign));
    }

    public static String sign(File file, String keyStorePath, String alias, String password) throws Exception {
        // 获取密钥库
        KeyStore keyStore = KitSecretKeyUtil.getKeyStore(keyStorePath, password);

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] sign = generateFileSign(fileInputStream, keyStore, alias, password);
        return new String(Base64Utils.encodeBase64(sign));
    }
    public static String sign(String filePath, String keyStorePath, String alias, String password) throws Exception {
        File file = new File(filePath);
        return sign(file, keyStorePath, alias, password);
    }
    public static String sign(byte[] data, String keyStorePath, String alias, String password) throws Exception {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        // 获取密钥库
        KeyStore keyStore = KitSecretKeyUtil.getKeyStore(keyStorePath, password);

        byte[] sign = generateFileSign(inputStream, keyStore, alias, password);
        return new String(Base64Utils.encodeBase64(sign));
    }

    public static boolean verify(File file, String sign, X509Certificate certificate) throws Exception {
        byte[] decodedSign = Base64Utils.decodeBase64(sign.getBytes());

        FileInputStream fileInputStream = new FileInputStream(file);
        return validateFileSign(fileInputStream, decodedSign, certificate);
    }
    public static boolean verify(String filePath, String sign, X509Certificate certificate) throws Exception {
        File file = new File(filePath);
        return verify(file, sign, certificate);
    }
    public static boolean verify(byte[] data, String sign, X509Certificate certificate) throws Exception {
        byte[] decodedSign = Base64Utils.decodeBase64(sign.getBytes());

        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        return validateFileSign(inputStream, decodedSign, certificate);
    }

    public static boolean verify(File file, String sign, String certificatePath) throws Exception {
        // 获得证书
        X509Certificate certificate = KitSecretKeyUtil.getX509Certificate(certificatePath);

        byte[] decodedSign = Base64Utils.decodeBase64(sign.getBytes());
        FileInputStream fileInputStream = new FileInputStream(file);

        return validateFileSign(fileInputStream, decodedSign, certificate);
    }
    public static boolean verify(String filePath, String sign, String certificatePath) throws Exception {
        File file = new File(filePath);
        return verify(file, sign, certificatePath);
    }
    public static boolean verify(byte[] data, String sign, String certificatePath) throws Exception {
        // 获得证书
        X509Certificate certificate = KitSecretKeyUtil.getX509Certificate(certificatePath);

        byte[] decodedSign = Base64Utils.decodeBase64(sign.getBytes());
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);

        return validateFileSign(inputStream, decodedSign, certificate);
    }

    private static byte[] generateFileSign(InputStream inputStream, KeyStore keyStore, String alias, String password) throws Exception {
        String sigAlgName = KitSecretKeyUtil.getX509Certificate(keyStore, alias, password).getSigAlgName();
        // 取得私钥
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
        // 构建签名
        Signature signature = Signature.getInstance(sigAlgName);
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

    private static boolean validateFileSign(InputStream inputStream, byte[] decodedSign, X509Certificate certificate) throws Exception {
        // 获得公钥
        PublicKey publicKey = certificate.getPublicKey();
        // 构建签名
        Signature signature = Signature.getInstance(certificate.getSigAlgName());
        signature.initVerify(publicKey);

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
