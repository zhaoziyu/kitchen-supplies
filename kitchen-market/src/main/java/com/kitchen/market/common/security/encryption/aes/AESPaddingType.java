package com.kitchen.market.common.security.encryption.aes;

/**
 * AES算法的填充方式
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-07
 */
public enum AESPaddingType {
    NoPadding("NoPadding"),
    PKCS5Padding("PKCS5Padding"),
    ISO10126Padding("ISO10126Padding");

    private String name;

    AESPaddingType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
