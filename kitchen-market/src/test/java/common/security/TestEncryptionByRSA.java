package common.security;

import com.kitchen.market.common.security.encryption.rsa.KitEncryptionFileByRSA;
import com.kitchen.market.common.security.encryption.rsa.KitEncryptionTextByRSA;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-05
 */
@Ignore
public class TestEncryptionByRSA {
    private static String privateKey_PKCS8 = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAPb9qeaArrL/HqzKheoz1LSOg1BDwJP9LjPkYQUuVYFkiYmX88dwrfTnYSOIfUEHEPTkgaVGKLBcNVlTAoZvVvK/aVPb8os9I4TUxFvC4Qy+qGv5F6FqJFjBIr+olP4K3SKKJ8Ld+U9vT2Vcxs6PC9PoBvy9Zzn5Ch7tpz5FcKSBAgMBAAECgYEAnI2EV3pRQVu70cI8x4o61IdQbFvFgQgFdRbY+DO6Nt3G39PUzSF64bSXObKV0dXsxYzhMCUcPUz0871N6HBCJzSt9iG+cpGS8X13AIa/uQ9/hFMn0BPyDOvdsL+/kv7Ahq2zHAMTmbJySMO7WCgMzd5O90KPFoJAGNLVXAtz+sECQQD83/bKI68XiepErLMzVIuOVt/WzvrcGOt6z0v1zthRHKdt6+nuhM/jNV1sDeteimgdT77j65qUcjcNMcrxDUUdAkEA+gsViMtoKgDSsMbKhNlLIlAxU/qvNXXwLx0E1Y3zcq+o/yJ4lgYbR3+MZuw8bfqkfparBOdERlrzH8xaG1IztQJAJeECncr0mmkNT5YzDbhXY03+H7ZHe5q8A1xz+3EtlBDfv6Z8Fz+LyHQg92OqYzIGYIWmiYusTxpAxtgzlyIuvQJBAO3AGlK+7iV6MNuruacGIh3XWH/8jhpsMNvrYMxaNBBpnGwz76re1ZNvYSYAHBmKyFwhkS2RZObs1d33ZfoyeD0CQQDzOP5DW/4EvbWc65gEzOnzVc14/7p6MRSRR/lfUYiU1zY1RTgzKylEFee+z/Jqd0IOOpP5dkFdLxHMbrgPZ63K";
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD2/anmgK6y/x6syoXqM9S0joNQQ8CT/S4z5GEFLlWBZImJl/PHcK3052EjiH1BBxD05IGlRiiwXDVZUwKGb1byv2lT2/KLPSOE1MRbwuEMvqhr+RehaiRYwSK/qJT+Ct0iiifC3flPb09lXMbOjwvT6Ab8vWc5+Qoe7ac+RXCkgQIDAQAB";

    private static String testFileName = "hello.txt";
    private static String curDir = "";

    @Before
    public void setUp() throws Exception {
        String currentDir = TestEncryptionByCertificate.class.getResource("").getPath();
        /* Mac中去除前缀找不到文件
        if (currentDir.startsWith("/"))
            currentDir = currentDir.substring(1);
        */
        if (!currentDir.endsWith("/"))
            currentDir += "/";

        curDir = currentDir;
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testTextPrivateKeyEncrypt_PublicKeyDecrypt() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String content = "plaintext明文MingWen";
        System.out.println("原始明文：" + content);

        long start = System.currentTimeMillis();
        String encryptContent = KitEncryptionTextByRSA.encryptByPrivateKey(content, privateKey_PKCS8);
        long end = System.currentTimeMillis();
        System.out.println("加密密文：" + encryptContent + "    加密用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        String decryptContent = KitEncryptionTextByRSA.decryptByPublicKey(encryptContent, publicKey);
        end = System.currentTimeMillis();
        System.out.println("解密明文：" + decryptContent + "    解密用时：" + (end - start) + "ms");

        assert content.equals(decryptContent);
    }
    @Test
    public void testTextPublicKeyEncrypt_PrivateKeyDecrypt() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String content = "plaintext明文MingWen";
        System.out.println("原始明文：" + content);

        long start = System.currentTimeMillis();
        String encryptContent = KitEncryptionTextByRSA.encryptByPublicKey(content, publicKey);
        long end = System.currentTimeMillis();
        System.out.println("加密密文：" + encryptContent + "    加密用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        String decryptContent = KitEncryptionTextByRSA.decryptByPrivateKey(encryptContent, privateKey_PKCS8);
        end = System.currentTimeMillis();
        System.out.println("解密明文：" + decryptContent + "    解密用时：" + (end - start) + "ms");

        assert content.equals(decryptContent);
    }

    @Test
    public void testFilePrivateKeyEncrypt_PublicKeyDecrypt() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String encryptFilePath = curDir + "encrypt-" + testFileName;
        String decryptFilePath = curDir + "decrypt-" + testFileName;

        long start = System.currentTimeMillis();
        KitEncryptionFileByRSA.encryptByPrivateKey(curDir + testFileName, encryptFilePath, privateKey_PKCS8);
        long end = System.currentTimeMillis();
        System.err.println("完成文件加密    用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        KitEncryptionFileByRSA.decryptByPublicKey(encryptFilePath, decryptFilePath, publicKey);
        end = System.currentTimeMillis();
        System.err.println("完成文件解密    用时：" + (end - start) + "ms");
    }
    @Test
    public void testFilePublicKeyEncrypt_PrivateKeyDecrypt() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String encryptFilePath = curDir + "encrypt-" + testFileName;
        String decryptFilePath = curDir + "decrypt-" + testFileName;

        long start = System.currentTimeMillis();
        KitEncryptionFileByRSA.encryptByPublicKey(curDir + testFileName, encryptFilePath, publicKey);
        long end = System.currentTimeMillis();
        System.err.println("完成文件加密    用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        KitEncryptionFileByRSA.decryptByPrivateKey(encryptFilePath, decryptFilePath, privateKey_PKCS8);
        end = System.currentTimeMillis();
        System.err.println("完成文件解密    用时：" + (end - start) + "ms");
    }
}
