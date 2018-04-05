package common.security;

import com.kitchen.market.common.security.KitSecretKeyUtil;
import com.kitchen.market.common.security.sign.cer.KitSignFileByCertificate;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.security.KeyStore;
import java.security.cert.X509Certificate;

/**
 * 测试之前把依赖文件拷贝到target/test-classes中的相应目录
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-05
 */
@Ignore
public class TestFileSignByCertificate {
    private static final String KEY_STORE_NAME = "appstore.keystore";
    private static final String CERTIFICATE_NAME = "appstore.cer";
    private static final String password = "1qw23er4";
    private static final String alias = "appstore";


    private static String testFileName = "hello.txt";
    private static String certificatePath = "";
    private static String keyStorePath = "";
    private static String testFilePath = "";

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
    public void testFileSignV1() throws Exception {
        System.err.println("私钥签名——公钥验签");
        long start = System.currentTimeMillis();
        String sign = KitSignFileByCertificate.sign(testFilePath, keyStorePath, alias, password);
        long end = System.currentTimeMillis();
        System.err.println("根据KeyStore生成签名：" + sign + "    用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        boolean result = KitSignFileByCertificate.verify(testFilePath, sign, certificatePath);
        end = System.currentTimeMillis();
        System.err.println("使用Certificate校验签名结果：" + result + "    用时：" + (end - start) + "ms");
    }
    @Test
    public void testFileSignV2() throws Exception {
        System.err.println("私钥签名——公钥验签");
        long start = System.currentTimeMillis();
        String sign = KitSignFileByCertificate.sign(testFilePath, keyStore, alias, password);
        long end = System.currentTimeMillis();
        System.err.println("根据Certificate生成签名：" + sign + "    用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        boolean result = KitSignFileByCertificate.verify(testFilePath, sign, certificate);
        end = System.currentTimeMillis();
        System.err.println("使用KeyStore校验签名结果：" + result + "    用时：" + (end - start) + "ms");
    }

}
