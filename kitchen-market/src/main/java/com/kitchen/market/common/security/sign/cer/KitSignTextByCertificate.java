package com.kitchen.market.common.security.sign.cer;

import com.kitchen.market.common.security.KitSecretKeyUtil;
import com.kitchen.market.common.security.codec.Base64Utils;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.X509Certificate;

/**
 * 根据keystore（密钥库文件）和cer（证书文件），对文本进行签名、验签
 * 注：可使用java的keytool生成keystore和cer
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-01
 */
public class KitSignTextByCertificate {
    public static String signToBase64(String data, KeyStore keyStore, String alias, String password) throws Exception {
        byte[] sign = sign(data.getBytes(), keyStore, alias, password);
        return new String(Base64Utils.encodeBase64(sign));
    }

    public static String signToBase64(String data, String keyStorePath, String alias, String password) throws Exception {
        // 获取私钥
        KeyStore keyStore = KitSecretKeyUtil.getKeyStore(keyStorePath, password);

        byte[] sign = sign(data.getBytes(), keyStore, alias, password);
        return new String(Base64Utils.encodeBase64(sign));
    }

    public static boolean verifyBase64Sign(String data, String sign, X509Certificate certificate) throws Exception {
        return verifySign(data.getBytes(), Base64Utils.decodeBase64(sign.getBytes()), certificate);
    }
    public static boolean verifyBase64Sign(String data, String sign, String certificatePath) throws Exception {
        // 获得证书
        X509Certificate certificate = KitSecretKeyUtil.getX509Certificate(certificatePath);

        return verifySign(data.getBytes(), Base64Utils.decodeBase64(sign.getBytes()), certificate);
    }

    private static byte[] sign(byte[] data, KeyStore keyStore, String alias, String password) throws Exception {
        String sigAlgName = KitSecretKeyUtil.getX509Certificate(keyStore, alias, password).getSigAlgName();
        // 取得私钥
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
        // 构建签名
        Signature signature = Signature.getInstance(sigAlgName);
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    private static boolean verifySign(byte[] data, byte[] sign, X509Certificate certificate) throws Exception {
        // 获得公钥
        PublicKey publicKey = certificate.getPublicKey();
        // 构建签名
        Signature signature = Signature.getInstance(certificate.getSigAlgName());
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sign);
    }
}
