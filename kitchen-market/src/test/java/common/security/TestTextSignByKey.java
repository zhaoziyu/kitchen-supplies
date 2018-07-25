package common.security;

import com.kitchen.market.common.security.sign.key.KitSignTextByKey;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-05
 */
@Ignore
public class TestTextSignByKey {
    private static String privateKey_PKCS8 = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAPb9qeaArrL/HqzKheoz1LSOg1BDwJP9LjPkYQUuVYFkiYmX88dwrfTnYSOIfUEHEPTkgaVGKLBcNVlTAoZvVvK/aVPb8os9I4TUxFvC4Qy+qGv5F6FqJFjBIr+olP4K3SKKJ8Ld+U9vT2Vcxs6PC9PoBvy9Zzn5Ch7tpz5FcKSBAgMBAAECgYEAnI2EV3pRQVu70cI8x4o61IdQbFvFgQgFdRbY+DO6Nt3G39PUzSF64bSXObKV0dXsxYzhMCUcPUz0871N6HBCJzSt9iG+cpGS8X13AIa/uQ9/hFMn0BPyDOvdsL+/kv7Ahq2zHAMTmbJySMO7WCgMzd5O90KPFoJAGNLVXAtz+sECQQD83/bKI68XiepErLMzVIuOVt/WzvrcGOt6z0v1zthRHKdt6+nuhM/jNV1sDeteimgdT77j65qUcjcNMcrxDUUdAkEA+gsViMtoKgDSsMbKhNlLIlAxU/qvNXXwLx0E1Y3zcq+o/yJ4lgYbR3+MZuw8bfqkfparBOdERlrzH8xaG1IztQJAJeECncr0mmkNT5YzDbhXY03+H7ZHe5q8A1xz+3EtlBDfv6Z8Fz+LyHQg92OqYzIGYIWmiYusTxpAxtgzlyIuvQJBAO3AGlK+7iV6MNuruacGIh3XWH/8jhpsMNvrYMxaNBBpnGwz76re1ZNvYSYAHBmKyFwhkS2RZObs1d33ZfoyeD0CQQDzOP5DW/4EvbWc65gEzOnzVc14/7p6MRSRR/lfUYiU1zY1RTgzKylEFee+z/Jqd0IOOpP5dkFdLxHMbrgPZ63K";
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD2/anmgK6y/x6syoXqM9S0joNQQ8CT/S4z5GEFLlWBZImJl/PHcK3052EjiH1BBxD05IGlRiiwXDVZUwKGb1byv2lT2/KLPSOE1MRbwuEMvqhr+RehaiRYwSK/qJT+Ct0iiifC3flPb09lXMbOjwvT6Ab8vWc5+Qoe7ac+RXCkgQIDAQAB";

    @Before
    public void setUp() throws Exception {

    }
    @After
    public void tearDown() throws Exception {

    }

    String data = "测试内容测试内容测试内容测试内容测试内容测试内容测试内容";
    @Test
    public void testRSA() throws Exception {
        String sign = "";
        long sum = 0L;
        long count = 1;
        for (int i = 0; i < count; i++) {
            data = UUID.randomUUID().toString();
            System.out.println("待签名数据：" + data);
            long start = System.nanoTime();
            sign = KitSignTextByKey.rsaSign(data, privateKey_PKCS8);
            long end = System.nanoTime();
            System.err.println("生成签名：" + sign + "    用时：" + (end - start) + "ns");
            sum += (end - start);
        }
        System.err.println("平均" + (sum / count) + "ns");

        sum = 0L;
        count = 10000;
        for (int i = 0; i < count; i++) {
            data = UUID.randomUUID().toString();
            long start = System.nanoTime();
            boolean result = KitSignTextByKey.verifyRsaSign(data, sign, publicKey);
            long end = System.nanoTime();
            System.err.println("校验签名结果：" + result + "    用时：" + (end - start) + "ns");
            sum += (end - start);
        }
        System.err.println("平均" + (sum / count) + "ns");
    }

    @Test
    public void testRSA256() throws Exception {
        System.out.println("待签名数据：" + data);
        long start = System.currentTimeMillis();
        String sign = KitSignTextByKey.rsa256Sign(data, privateKey_PKCS8);
        long end = System.currentTimeMillis();
        System.err.println("生成签名：" + sign + "    用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        boolean result = KitSignTextByKey.verifyRsa256Sign(data, sign, publicKey);
        end = System.currentTimeMillis();
        System.err.println("校验签名结果：" + result + "    用时：" + (end - start) + "ms");
    }

    @Test
    public void testMD5() throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        long sum = 0L;
        long count = 10000;
        for (int i = 0; i < count; i++) {
            data = UUID.randomUUID().toString();
            long start = System.nanoTime();
            byte[] digest = messageDigest.digest(data.getBytes(Charset.forName("UTF-8")));
            long end = System.nanoTime();
            System.err.println("结果：" + Base64.getEncoder().encodeToString(digest) + "    用时：" + (end - start) + "ns");
            sum += (end - start);
        }
        System.err.println("平均" + (sum / count) + "ns");
    }

    @Test
    public void testECDSA() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        System.out.println("生成ECC密钥对");
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(256);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        System.out.println(keyPair.getPrivate().getAlgorithm());
        System.out.println(keyPair.getPrivate().getFormat());
        System.out.println(Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));

        System.out.println(keyPair.getPublic().getAlgorithm());
        System.out.println(keyPair.getPublic().getFormat());
        System.out.println(Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));


        byte[] privateKeyEnc = keyPair.getPrivate().getEncoded();
        byte[] publicKeyEnc = keyPair.getPublic().getEncoded();

        System.out.println("签名");
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyEnc);
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        //构建签名
        Signature signature = Signature.getInstance("SHA1withECDSA");
        signature.initSign(priKey);
        signature.update(data.getBytes());
        byte [] result = signature.sign();   // 签名后的数据信息

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyEnc);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        signature.initVerify(publicKey);

        long sum = 0L;
        long count = 1000;
        System.out.println("验签");
        for (int i = 0; i < count; i++) {
            data = UUID.randomUUID().toString();
            long start = System.nanoTime();
            signature.update(data.getBytes());
            boolean ok = signature.verify(result);  // 验证结果
            System.out.println(ok);
            long end = System.nanoTime();
            System.err.println("校验签名结果：" + result + "    用时：" + (end - start) + "ns");
            sum += (end - start);
        }
        System.err.println("平均" + (sum / count) + "ns");

    }
}
