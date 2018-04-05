package com.kitchen.market.common.identity;

import com.kitchen.market.common.time.KitTimeFormatter;

/**
 * 订单号生成工具，生成非重复订单号，理论上限1毫秒1000个，可扩展
 *
 * @date 2016-12-29
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class KitOrderNumCreator {

    /**
     * 同步锁对象
     */
    private static Object LOCKER = new String("lockerOrder");
    /**
     * 订单号生成计数器
     */
    private static long ORDER_NUM_COUNT = 0L;
    /**
     * 每毫秒生成订单号数量最大值
     */
    private static int MS_MAX_COUNT = 10000;

    /**
     * 生成非重复订单号，理论上限1毫秒1000个，可扩展
     */
    public static String createOrderNum() {
        // 最终生成的订单号
        String finalOrderNumber = "";
        try {
            synchronized (LOCKER) {
                // 取系统当前时间作为订单号变量前半部分，精确到毫秒
                long nowLong = Long.parseLong(KitTimeFormatter.formatNowToCustom(KitTimeFormatter.YYYYMMDDHHMMSSSSS));
                // 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万
                if (ORDER_NUM_COUNT > MS_MAX_COUNT) {
                    ORDER_NUM_COUNT = 0L;
                }
                //组装订单号
                String countStr = MS_MAX_COUNT + ORDER_NUM_COUNT + "";
                finalOrderNumber = nowLong + countStr.substring(1);
                ORDER_NUM_COUNT++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalOrderNumber;
    }
}
