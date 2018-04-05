package com.kitchen.market.common.security.encryption.des;

/**
 * DES算法类型
 *
 * DES      —— （Data Encryption Standard）数据加密标准，速度较快，适用于加密大量数据的场合。
 * DESede   —— （Triple DES）是基于DES，对一块数据用三个不同的密钥进行三次加密，强度更高。
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-07
 */
enum DESAlgorithmType {
    DES("DES"),
    DESede("DESede"),
    SHA1PRNG("SHA1PRNG");

    private String name;

    DESAlgorithmType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
