package com.kitchen.market.common.security.encryption.aes;

/**
 * AES算法类型
 *
 * AES      —— （Data Encryption Standard）数据加密标准，速度较快，适用于加密大量数据的场合。
 * DESede   —— （Triple DES）是基于DES，对一块数据用三个不同的密钥进行三次加密，强度更高。
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-07
 */
public enum AESAlgorithmType {
    AES("AES"),
    SHA1PRNG("SHA1PRNG");

    private String name;

    AESAlgorithmType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
