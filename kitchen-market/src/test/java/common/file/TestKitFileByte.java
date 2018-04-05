package common.file;

import com.kitchen.market.common.file.KitFileByte;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-14
 */
@Ignore
public class TestKitFileByte {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test() throws Exception {
        byte[] fileByte = KitFileByte.fileToByte("D:\\test.txt");

        KitFileByte.byteArrayToFile(fileByte, "D:\\test1.txt");
    }
}
