package common.security;

import com.kitchen.market.common.security.encryption.des.KitEncryptionFileBy3DES;
import com.kitchen.market.common.security.encryption.des.KitEncryptionTextByDES;
import com.kitchen.market.common.security.encryption.des.KitEncryptionTextBy3DES;
import com.kitchen.market.common.security.encryption.des.KitEncryptionFileByDES;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-07-11
 */
@Ignore
public class TestEncryptionByDES {

    private static final String KEY_STR = "TP5MUp08TP5MUp08TP5MUp08"; // 一个加密解密双方约定好的字符串
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
    public void testFileEncryption() throws Exception {
        String desEncryptFile = curDir + "DES-Encrypt-" + testFileName;
        String desDecryptFile = curDir + "DES-Decrypt-" + testFileName;

        String des3EncryptFile = curDir + "3DES-Encrypt-" + testFileName;
        String des3DecryptFile = curDir + "3DES-Decrypt-" + testFileName;
        long start;
        long end;

        System.out.println("使用DES算法加密、解密");
        start = System.currentTimeMillis();
        KitEncryptionFileByDES.encryptFile(curDir + testFileName, desEncryptFile, KEY_STR);
        end = System.currentTimeMillis();
        System.err.println("完成DES文件加密    用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        KitEncryptionFileByDES.decryptFile(desEncryptFile, desDecryptFile, KEY_STR);
        end = System.currentTimeMillis();
        System.err.println("完成DES文件解密    用时：" + (end - start) + "ms");

        System.out.println("——————————————————————————————————————");

        System.out.println("使用3DES算法加密、解密");
        start = System.currentTimeMillis();
        KitEncryptionFileBy3DES.encryptFile(curDir + testFileName, des3EncryptFile, KEY_STR);
        end = System.currentTimeMillis();
        System.err.println("完成3DES文件加密    用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        KitEncryptionFileBy3DES.decryptFile(des3EncryptFile, des3DecryptFile, KEY_STR);
        end = System.currentTimeMillis();
        System.err.println("完成3DES文件解密    用时：" + (end - start) + "ms");
    }

    @Test
    public void testTextEncryption() throws Exception {
        String plaintext = "Hello World! 您好，世界！";
        System.out.println("原文：" + plaintext);
        long start;
        long end;

        System.out.println("使用DES算法加密、解密");
        start = System.currentTimeMillis();
        String desEncryptText = KitEncryptionTextByDES.encryptToBase64(plaintext, KEY_STR);
        end = System.currentTimeMillis();
        System.out.println("完成DES文本加密 密文：" + desEncryptText + "\n    用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        String desDecryptText = KitEncryptionTextByDES.decryptByBase64(desEncryptText, KEY_STR);
        end = System.currentTimeMillis();
        System.out.println("完成DES文本解密 解密后：" + desDecryptText + "\n    用时：" + (end - start) + "ms");

        System.out.println("——————————————————————————————————————");

        System.out.println("使用3DES算法加密、解密");
        start = System.currentTimeMillis();
        String _3desEncryptText = KitEncryptionTextBy3DES.encryptToBase64(plaintext, KEY_STR);
        end = System.currentTimeMillis();
        System.out.println("完成DES文本加密 密文：" + _3desEncryptText + "\n    用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        String _3desDecryptText = KitEncryptionTextBy3DES.decryptByBase64(_3desEncryptText, KEY_STR);
        end = System.currentTimeMillis();
        System.out.println("完成DES文本解密 解密后：" + _3desDecryptText + "\n    用时：" + (end - start) + "ms");
    }
}
