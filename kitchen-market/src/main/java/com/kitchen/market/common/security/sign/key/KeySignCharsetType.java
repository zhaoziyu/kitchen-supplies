package com.kitchen.market.common.security.sign.key;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-08
 */
public enum KeySignCharsetType {
    UTF8("UTF-8"),
    GBK("GBK");

    private String name;

    KeySignCharsetType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
