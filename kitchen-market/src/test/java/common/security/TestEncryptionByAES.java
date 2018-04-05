package common.security;

import com.kitchen.market.common.security.encryption.aes.KitEncryptionFileByAES;
import com.kitchen.market.common.security.encryption.aes.KitEncryptionTextByAES;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-07-11
 */
@Ignore
public class TestEncryptionByAES {

    private static final String KEY_STR = "TP5MUp08KH2OMlZ3oA90"; // 一个加密解密双方约定好的字符串
    private static String testFileName = "hello.txt";
    private static String curDir = "";


    @Before
    public void setUp() throws Exception {
        String currentDir = TestEncryptionByCertificate.class.getResource("").getPath();
        /* Mac中去除前缀后找不到文件
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
    public void testFileEncryption() throws Exception {
        String aesEncryptFile = curDir + "AES-Encrypt-" + testFileName;
        String aesDecryptFile = curDir + "AES-Decrypt-" + testFileName;

        long start;
        long end;

        System.out.println("使用AES算法加密、解密");
        start = System.currentTimeMillis();
        KitEncryptionFileByAES.encryptFile(curDir + testFileName, aesEncryptFile, KEY_STR);
        end = System.currentTimeMillis();
        System.err.println("完成AES文件加密    用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        KitEncryptionFileByAES.decryptFile(aesEncryptFile, aesDecryptFile, KEY_STR);
        end = System.currentTimeMillis();
        System.err.println("完成AES文件解密    用时：" + (end - start) + "ms");
    }

    @Test
    public void testTextEncryption() throws Exception {
        String plaintext = "Hello World! 您好，世界！";
        System.out.println("原文：" + plaintext);
        long start;
        long end;

        System.out.println("使用AES算法加密、解密");
        start = System.currentTimeMillis();
        String desEncryptText = KitEncryptionTextByAES.encryptToBase64(plaintext, KEY_STR);
        end = System.currentTimeMillis();
        System.out.println("完成AES文本加密 密文：" + desEncryptText + "\n    用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        String desDecryptText = KitEncryptionTextByAES.decryptByBase64(desEncryptText, KEY_STR);
        end = System.currentTimeMillis();
        System.out.println("完成AES文本解密 解密后：" + desDecryptText + "\n    用时：" + (end - start) + "ms");
    }
}
