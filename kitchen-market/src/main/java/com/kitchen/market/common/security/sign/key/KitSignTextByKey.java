package com.kitchen.market.common.security.sign.key;

import com.kitchen.market.common.security.KitSecretKeyUtil;
import com.kitchen.market.common.security.codec.Base64Utils;

import java.io.ByteArrayInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;

/**
 * 根据PrivateKey（私钥字符串）和PublicKey（公钥字符串），对文本进行签名、验签
 * 注：可使用openssl工具生成私钥和公钥
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-05
 */
public class KitSignTextByKey {
    //---------------------------------------------SHA1WithRSA签名、验签---------------------------------------------
    public static String rsaSign(String content, String privateKeyStr) throws Exception {
        return rsaSign(content, privateKeyStr, null);
    }
    public static String rsaSign(String content, String privateKeyStr, KeySignCharsetType charset) throws Exception {
        try {
            PrivateKey privateKey = KitSecretKeyUtil.getPrivateKeyFromPKCS8(RSAType.RSA.getName(), new ByteArrayInputStream(privateKeyStr.getBytes()));

            Signature signature = Signature.getInstance(RSAType.RSA.getAlgorithms());

            signature.initSign(privateKey);

            if (charset == null) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset.getName()));
            }

            byte[] signed = signature.sign();

            return new String(Base64Utils.encodeBase64(signed));
        } catch (InvalidKeySpecException ie) {
            throw new Exception("RSA私钥格式不正确，请检查是否正确配置了PKCS8格式的私钥", ie);
        } catch (Exception e) {
            throw new Exception("RSAcontent = " + content + "; charset = " + charset, e);
        }
    }
    public static boolean verifyRsaSign(String content, String sign, String publicKey) throws Exception {
        return verifyRsaSign(content, sign, publicKey, null);
    }
    public static boolean verifyRsaSign(String content, String sign, String publicKey, KeySignCharsetType charset) throws Exception {
        try {
            PublicKey pubKey = KitSecretKeyUtil.getPublicKeyFromX509(RSAType.RSA.getName(), new ByteArrayInputStream(publicKey.getBytes()));

            Signature signature = Signature.getInstance(RSAType.RSA.getAlgorithms());

            signature.initVerify(pubKey);

            if (charset == null) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset.getName()));
            }

            return signature.verify(Base64Utils.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw new Exception("RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, e);
        }
    }
    //---------------------------------------------SHA1WithRSA签名、验签---------------------------------------------

    //---------------------------------------------SHA256WithRSA签名、验签---------------------------------------------
    public static String rsa256Sign(String content, String privateKeyStr) throws Exception {
        return rsa256Sign(content, privateKeyStr, null);
    }
    public static String rsa256Sign(String content, String privateKeyStr, KeySignCharsetType charset) throws Exception {
        try {
            PrivateKey privateKey = KitSecretKeyUtil.getPrivateKeyFromPKCS8(RSAType.RSA256.getName(), new ByteArrayInputStream(privateKeyStr.getBytes()));

            Signature signature = Signature.getInstance(RSAType.RSA256.getAlgorithms());

            signature.initSign(privateKey);

            if (charset == null) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset.getName()));
            }

            byte[] signed = signature.sign();

            return new String(Base64Utils.encodeBase64(signed));
        } catch (Exception e) {
            throw new Exception("RSAcontent = " + content + "; charset = " + charset, e);
        }
    }
    public static boolean verifyRsa256Sign(String content, String sign, String publicKey) throws Exception {
        return verifyRsa256Sign(content, sign, publicKey, null);
    }
    public static boolean verifyRsa256Sign(String content, String sign, String publicKey, KeySignCharsetType charset) throws Exception {
        try {
            PublicKey pubKey = KitSecretKeyUtil.getPublicKeyFromX509(RSAType.RSA256.getName(), new ByteArrayInputStream(publicKey.getBytes()));

            Signature signature = Signature.getInstance(RSAType.RSA256.getAlgorithms());

            signature.initVerify(pubKey);

            if (charset == null) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset.getName()));
            }

            return signature.verify(Base64Utils.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw new Exception("RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, e);
        }
    }
    //---------------------------------------------SHA256WithRSA签名、验签---------------------------------------------
}
