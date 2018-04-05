package com.kitchen.market.common.identity;

import java.util.UUID;

/**
 * 标识生成器
 *
 * @date 2016-12-29
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class KitUUID {

    /**
     * 获取一个去除分隔符“-”，字符全部大写的UUID
     * @return
     */
    public static String getPureUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
}
