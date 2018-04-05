package com.kitchen.market.common.security;

import com.kitchen.market.common.security.codec.Base64Utils;

import java.io.*;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-01
 */
public class KitSecretKeyUtil {
    public static final String KEY_STORE = "JKS";
    public static final String X509 = "X.509";

    public static X509Certificate getX509Certificate(String keyStorePath, String alias, String password) throws Exception {
        KeyStore keyStore = getKeyStore(keyStorePath, password);
        X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);
        return certificate;
    }

    public static X509Certificate getX509Certificate(KeyStore keyStore, String alias, String password) throws Exception {
        X509Certificate certificate = (X509Certificate) keyStore.getCertificate(alias);
        return certificate;
    }

    public static X509Certificate getX509Certificate(String certificatePath) throws Exception {
        CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);
        FileInputStream in = new FileInputStream(certificatePath);
        X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(in);
        in.close();
        return certificate;
    }

    public static KeyStore getKeyStore(String keyStorePath, String password) throws Exception {
        FileInputStream in = new FileInputStream(keyStorePath);
        KeyStore keyStore = KeyStore.getInstance(KEY_STORE);
        keyStore.load(in, password.toCharArray());
        in.close();
        return keyStore;
    }

    public static PublicKey getPublicKey(String certificatePath) throws Exception {
        Certificate certificate = KitSecretKeyUtil.getX509Certificate(certificatePath);
        PublicKey publicKey = certificate.getPublicKey();
        return publicKey;
    }
    public static PublicKey getPublicKey(Certificate certificate) throws Exception {
        PublicKey publicKey = certificate.getPublicKey();
        return publicKey;
    }
    public static PrivateKey getPrivateKey(String keyStorePath, String alias, String password) throws Exception {
        KeyStore keyStore = KitSecretKeyUtil.getKeyStore(keyStorePath, password);
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
        return privateKey;
    }
    public static PrivateKey getPrivateKey(KeyStore keyStore, String alias, String password) throws Exception {
        PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
        return privateKey;
    }

    //-------------------------------------验证证书是否有效-------------------------------------
    public static boolean verifyCertificate(Certificate certificate) {
        return verifyCertificate(new Date(), certificate);
    }
    public static boolean verifyCertificate(Date date, Certificate certificate) {
        boolean isValid = true;
        try {
            X509Certificate x509Certificate = (X509Certificate) certificate;
            x509Certificate.checkValidity(date);
        } catch (Exception e) {
            isValid = false;
        }
        return isValid;
    }
    public static boolean verifyCertificate(Date date, String certificatePath) {
        Certificate certificate;
        try {
            certificate = getX509Certificate(certificatePath);
            return verifyCertificate(date, certificate);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean verifyCertificate(Date date, String keyStorePath, String alias, String password) {
        Certificate certificate;
        try {
            certificate = getX509Certificate(keyStorePath, alias, password);
            return verifyCertificate(date, certificate);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean verifyCertificate(String keyStorePath, String alias, String password) {
        return verifyCertificate(new Date(), keyStorePath, alias, password);
    }
    public static boolean verifyCertificate(String certificatePath) {
        return verifyCertificate(new Date(), certificatePath);
    }
    //-------------------------------------验证证书是否有效-------------------------------------

    public static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
        if (ins == null || StringUtils.isEmpty(algorithm)) {
            return null;
        }
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        byte[] encodedKey = StreamUtil.readText(ins).getBytes();
        encodedKey = Base64Utils.decodeBase64(encodedKey);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }
    public static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        StringWriter writer = new StringWriter();
        StreamUtil.io(new InputStreamReader(ins), writer);
        byte[] encodedKey = writer.toString().getBytes();
        encodedKey = Base64Utils.decodeBase64(encodedKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }
}
