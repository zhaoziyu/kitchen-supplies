package demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * 使用CountDownLatch进行多线程并发控制
 * 适用场景：当有处理时长较高的业务，且该业务可以有并行处理的可能时，可以使用多个线程分别处理一部分工作，最后将各个线程的处理结果汇总，再进行下一步的业务处理
 *
 * @date 2016-12-24
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Ignore
public class DemoCountDownLatch {
    /**
     * 计数器，用来控制线程
     * 传入参数2，表示计数器计数为2
     */
    private final static CountDownLatch mCountDownLatch = new CountDownLatch(2);

    @Before
    public void setUp() throws Exception {

    }
    @After
    public void tearDown() throws Exception {

    }
    @Test
    public void test() {
        // 最先run SampleThread
        new SampleThread().start();

        // 运行两个工作线程
        new WorkingThread("WorkingThread1", 5000).start();// 工作线程1运行5秒
        new WorkingThread("WorkingThread2", 2000).start();// 工作线程2运行2秒

        // 得到运行结果后，继续处理下一步业务
        // ...

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("测试完成");
    }

    /**
     * 示例工作线程类
     */
    private static class WorkingThread extends Thread {
        private final String mThreadName;
        private final int mSleepTime;
        public WorkingThread(String name, int sleepTime) {
            mThreadName = name;
            mSleepTime = sleepTime;
        }

        @Override
        public void run() {
            System.out.println("[" + mThreadName + "] started!");
            try {
                Thread.sleep(mSleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mCountDownLatch.countDown();
            System.out.println("[" + mThreadName + "] end!");
        }
    }

    /**
     * 示例线程类
     */
    private static class SampleThread extends Thread {

        @Override
        public void run() {
            System.out.println("[SampleThread] started!");

            try {
                // 会阻塞在这里等待 mCountDownLatch 里的count变为0；
                // 也就是等待另外的WorkingThread调用countDown()
                mCountDownLatch.await();
            } catch (InterruptedException e) {

            }
            System.out.println("[SampleThread] end!");
        }
    }

}
