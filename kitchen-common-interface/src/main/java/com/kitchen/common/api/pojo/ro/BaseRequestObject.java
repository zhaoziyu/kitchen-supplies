package com.kitchen.common.api.pojo.ro;

import java.io.Serializable;

/**
 * Sign公共请求参数的基础类
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-08-23
 */
public abstract class BaseRequestObject implements Serializable {
    /**
     * 分配给开发者（商户、接口调用方）的签名标识，每个签名标识对应一对唯一的密钥
     */
    private String appId;
    /**
     * 签名算法的类型[RSA2,RSA,MD5]
     */
    private String signType;
    /**
     * 请求参数的签名串
     */
    private String sign;
    /**
     * 发送请求的时间
     */
    private String timestamp;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
