package com.kitchen.sdk.sign.rsa;

import com.kitchen.sdk.sign.KitSignConstant;
import com.kitchen.sdk.sign.util.StreamUtil;
import com.kitchen.sdk.sign.util.StringUtils;
import com.kitchen.sdk.sign.util.codec.Base64;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-08-25
 */
public class KitRSACheck {
    public static boolean rsaCheck(String content, String sign, String publicKey, String charset, String signType) throws Exception {

        if (KitSignConstant.SIGN_TYPE_RSA.equals(signType)) {
            return rsaCheckContent(content, sign, publicKey, charset);
        } else if (KitSignConstant.SIGN_TYPE_RSA2.equals(signType)) {
            return rsa256CheckContent(content, sign, publicKey, charset);
        } else {
            throw new Exception("Sign Type is Not Support : signType=" + signType);
        }

    }

    private static boolean rsa256CheckContent(String content, String sign, String publicKey, String charset) throws Exception {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));

            Signature signature = Signature.getInstance(KitSignConstant.SIGN_SHA256RSA_ALGORITHMS);

            signature.initVerify(pubKey);

            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw new Exception("RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, e);
        }
    }

    private static boolean rsaCheckContent(String content, String sign, String publicKey, String charset) throws Exception {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));

            Signature signature = Signature.getInstance(KitSignConstant.SIGN_ALGORITHMS);

            signature.initVerify(pubKey);

            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw new Exception("RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, e);
        }
    }

    private static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        StringWriter writer = new StringWriter();
        StreamUtil.io(new InputStreamReader(ins), writer);

        byte[] encodedKey = writer.toString().getBytes();

        encodedKey = Base64.decodeBase64(encodedKey);

        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }
}
