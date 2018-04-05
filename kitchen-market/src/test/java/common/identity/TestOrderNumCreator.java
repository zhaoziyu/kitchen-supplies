package common.identity;

import com.kitchen.market.common.identity.KitOrderNumCreator;
import com.kitchen.market.common.time.KitTimeFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * 测试订单号生成器
 *
 * @date 2016-12-29
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Ignore
public class TestOrderNumCreator {
    static int users = 100;
    static int request = 2000;
    static CountDownLatch mCountDownLatch = new CountDownLatch(users * request);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test() throws InterruptedException {
        String start = KitTimeFormatter.formatNowToCommonDateTimeMs();
        for (int i = 0; i < users; i++) {
            new SampleThread().start();
        }
        mCountDownLatch.await();
        System.out.println("开始：" + start);
        System.out.println("结束：" + KitTimeFormatter.formatNowToCommonDateTimeMs());
    }

    private static class SampleThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < request; i++) {
                Thread t = new Thread(() -> {
                    System.out.println(KitOrderNumCreator.createOrderNum());
                    mCountDownLatch.countDown();
                });
                t.start();
            }
        }
    }
}
