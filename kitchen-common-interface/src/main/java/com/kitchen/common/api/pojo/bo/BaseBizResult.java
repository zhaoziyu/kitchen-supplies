package com.kitchen.common.api.pojo.bo;

import java.io.Serializable;

/**
 * 业务逻辑层返回结果统一执行结果模型的基类
 *
 * @date 2017-05-17
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public abstract class BaseBizResult implements Serializable {
    /**
     * 执行的结果
     * true：执行成功
     * false：执行失败
     */
    private boolean success;

    /**
     * 业务返回码
     */
    private String bizCode;

    /**
     * 业务返回码说明
     */
    private String bizMsg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBizMsg() {
        return bizMsg;
    }

    public void setBizMsg(String bizMsg) {
        this.bizMsg = bizMsg;
    }
}
