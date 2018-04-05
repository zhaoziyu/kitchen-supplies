package common.security;

import com.kitchen.market.common.security.encryption.cer.KitEncryptionFileByCertificate;
import com.kitchen.market.common.security.encryption.cer.KitEncryptionTextByCertificate;
import com.kitchen.market.common.security.KitSecretKeyUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.security.KeyStore;
import java.security.cert.X509Certificate;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 测试
 * 测试之前把依赖文件拷贝到target/test-classes中的相应目录
 *
 * @date 2016-12-29
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Ignore
public class TestEncryptionByCertificate {
    private static final String KEY_STORE_NAME = "appstore.keystore";
    private static final String CERTIFICATE_NAME = "appstore.cer";
    private static final String password = "1qw23er4";
    private static final String alias = "appstore";


    private static String testFileName = "hello.txt";
    private static String certificatePath = "";
    private static String keyStorePath = "";
    private static String testFilePath = "";
    private static String curDir = "";

    private static X509Certificate certificate;
    private static KeyStore keyStore;
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
        keyStorePath = currentDir + KEY_STORE_NAME;
        certificatePath = currentDir + CERTIFICATE_NAME;
        testFilePath = currentDir + testFileName;

        certificate = KitSecretKeyUtil.getX509Certificate(certificatePath);
        keyStore = KitSecretKeyUtil.getKeyStore(keyStorePath, password);
    }
    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void verifyCertificate() throws Exception {
        System.err.println("校验证书是否有效");
        assert KitSecretKeyUtil.verifyCertificate(certificatePath);
    }

    @Test
    public void testFileKeyStoreEncryptCertificateDecrypt() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String encryptFilePath = curDir + "encrypt-" + testFileName;
        String decryptFilePath = curDir + "decrypt-" + testFileName;

        long start = System.currentTimeMillis();
        KitEncryptionFileByCertificate.encryptByKeyStore(testFilePath, encryptFilePath, keyStorePath, alias, password);
        long end = System.currentTimeMillis();
        System.err.println("完成文件加密    用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        KitEncryptionFileByCertificate.decryptByCertificate(encryptFilePath, decryptFilePath, certificatePath);
        end = System.currentTimeMillis();
        System.err.println("完成文件解密    用时：" + (end - start) + "ms");
    }
    @Test
    public void testFileCertificateEncryptKeyStoreDecrypt() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String encryptFilePath = curDir + "encrypt-" + testFileName;
        String decryptFilePath = curDir + "decrypt-" + testFileName;

        long start = System.currentTimeMillis();
        KitEncryptionFileByCertificate.encryptByCertificate(testFilePath, encryptFilePath, certificatePath);
        long end = System.currentTimeMillis();
        System.err.println("完成文件加密    用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        KitEncryptionFileByCertificate.decryptByKeyStore(encryptFilePath, decryptFilePath, keyStorePath, alias, password);
        end = System.currentTimeMillis();
        System.err.println("完成文件解密    用时：" + (end - start) + "ms");
    }

    @Test
    public void testTextKeyStoreEncryptCertificateDecrypt() throws Exception {
        System.err.println("私钥加密——公钥解密");
        // KeyStore加密，Certificate解密
        String content = "plaintext明文MingWen";
        System.out.println("原始明文：" + content);

        long start = System.currentTimeMillis();
        String encryptContent = KitEncryptionTextByCertificate.encryptByKeyStore(content, keyStorePath, alias, password);
        long end = System.currentTimeMillis();
        System.out.println("加密密文：" + encryptContent + "    加密用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        String decryptContent = KitEncryptionTextByCertificate.decryptByCertificate(encryptContent, certificatePath);
        end = System.currentTimeMillis();
        System.out.println("解密明文：" + decryptContent + "    解密用时：" + (end - start) + "ms");

        assert content.equals(decryptContent);
    }

    @Test
    public void testTextCertificateEncryptKeyStoreDecrypt() throws Exception {
        System.err.println("公钥加密——私钥解密");
        // Certificate加密，KeyStore解密
        String content = "MingWen明文plaintext";
        System.out.println("原始明文：" + content);

        long start = System.currentTimeMillis();
        String encryptContent = KitEncryptionTextByCertificate.encryptByCertificate(content, certificatePath);
        long end = System.currentTimeMillis();
        System.out.println("加密密文：" + encryptContent + "    加密用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        String decryptContent = KitEncryptionTextByCertificate.decryptByKeyStore(encryptContent, keyStorePath, alias, password);
        end = System.currentTimeMillis();
        System.out.println("解密明文：" + decryptContent + "    解密用时：" + (end - start) + "ms");

        assert content.equals(decryptContent);
    }

}
