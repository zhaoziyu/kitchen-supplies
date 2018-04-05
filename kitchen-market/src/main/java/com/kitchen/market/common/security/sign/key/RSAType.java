package com.kitchen.market.common.security.sign.key;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-08
 */
enum RSAType {
    RSA("RSA", "SHA1WithRSA"),
    RSA256("RSA", "SHA256WithRSA");

    private String name;
    private String algorithms;

    RSAType(String name, String algorithms) {
        this.name = name;
        this.algorithms = algorithms;
    }

    public String getName() {
        return this.name;
    }

    public String getAlgorithms() {
        return this.algorithms;
    }
}
