package common.security;

import com.kitchen.market.common.security.sign.md5.KitSignFileByMD5;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-08
 */
@Ignore
public class TestFileSignByMD5 {
    private static String testFileName = "hello.apk";
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
    public void testFileSign() throws Exception {
        //String bigFile = "F:\\1.操作系统安装文件\\win7(64bit)\\CN_WIN7_SP1_X64_33in1_V1.2.iso";
        System.out.println("获取文件的MD5");
        long start = System.currentTimeMillis();
        String md5 = KitSignFileByMD5.getMD5(curDir + testFileName);
        //String md5 = KitSignFileByMD5.getMD5(bigFile);
        long end = System.currentTimeMillis();
        System.err.println("完成用时：" + (end - start) + "ms  MD5:" + md5);
    }
}
