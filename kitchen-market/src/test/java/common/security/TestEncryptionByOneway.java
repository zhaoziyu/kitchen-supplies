package common.security;

import com.kitchen.market.common.security.encryption.single.KitEncryptionCustom;
import com.kitchen.market.common.security.encryption.single.KitEncryptionMD5;
import com.kitchen.market.common.security.encryption.single.KitEncryptionPBKDF2;
import org.javatuples.Pair;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 测试单向加密算法
 *
 * 包括：PBKDF2、MD5等
 *
 * @date 2017-01-01
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Ignore
public class TestEncryptionByOneway {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }
    private static String password = "Hello,World!";

    @Test
    public void testKitEncryptionCustom() throws InterruptedException {
        System.out.println("原文：" + password);

        Pair<String, String> pair = KitEncryptionCustom.encrypt(password);
        System.out.println("生成的盐：" + pair.getValue0());
        System.out.println(pair.getValue1() + " 密文");

        String encryptStr = KitEncryptionCustom.encrypt(password, pair.getValue0());
        System.out.println(encryptStr + " 使用盐加密结果");

        assert encryptStr.equals(pair.getValue1());
    }

    @Test
    public void testKitEncryptionMD5() throws InterruptedException {
        System.out.println("原文：" + password);

        String encrypt1 = KitEncryptionMD5.encrypt(password);
        String encrypt2 = KitEncryptionMD5.encrypt(password);

        System.out.println("密文：" + encrypt1);

        assert encrypt1.equals(encrypt2);
    }

    @Test
    public void testKitEncryptionPBKDF2() throws InterruptedException {
        System.out.println("原文：" + password);

        Pair<String, String> pair = KitEncryptionPBKDF2.encryptByRandomSalt(password);
        System.out.println("盐值 16进制：" + pair.getValue0());
        System.out.println("密文 16进制：" + pair.getValue1());

        Pair<String, String> pair1 = KitEncryptionPBKDF2.encryptByFixSalt(password, pair.getValue0());
        System.out.println("盐值 16进制：" + pair1.getValue0());
        System.out.println("密文 16进制：" + pair1.getValue1());

        assert pair.getValue1().equals(pair1.getValue1());
    }
}
