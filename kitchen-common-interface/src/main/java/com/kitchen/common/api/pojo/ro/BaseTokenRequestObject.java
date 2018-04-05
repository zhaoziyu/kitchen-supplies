package com.kitchen.common.api.pojo.ro;

/**
 * Token公共请求参数的基础类
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018-01-23
 */
public abstract class BaseTokenRequestObject extends BaseRequestObject {
    /**
     * 用户标识（可以是id、code等唯一标识用户的字段）
     */
    private String userId;
    /**
     * 设备标识（预留）
     */
    private String deviceId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
