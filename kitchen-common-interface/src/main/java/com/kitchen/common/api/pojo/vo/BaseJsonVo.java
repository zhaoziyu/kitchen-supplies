package com.kitchen.common.api.pojo.vo;

import com.kitchen.common.api.constant.code.IReturnCode;

import java.io.Serializable;

/**
 * Json***Vo的基类
 * 提炼了公共返回参数
 *
 * @date 2017-04-28
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public abstract class BaseJsonVo implements Serializable {
    /**
     * 执行的结果
     * true：执行成功
     * false：执行失败
     */
    private boolean success;

    /**
     * 接口返回码
     */
    private String code;

    /**
     * 接口返回码说明
     */
    private String msg;

    /**
     * 业务返回码
     */
    private String bizCode;

    /**
     * 业务返回码说明
     */
    private String bizMsg;

    /**
     * 签名算法的类型[RSA2,RSA]
     */
    private String signType;
    /**
     * 返回参数的签名
     */
    private String sign;
    /**
     * 服务器响应时间
     */
    private String timestamp;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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

    /**
     * 通过返回码枚举类设置Code
     * @param returnCode
     */
    public void setCode(IReturnCode returnCode) {
        this.code = returnCode.getCode();
    }
    /**
     * 通过返回码枚举类设置Code
     * @param returnCode
     */
    public void setBizCode(IReturnCode returnCode) {
        this.bizCode = returnCode.getBizCode();
    }
    /**
     * 通过返回码枚举类设置Msg
     * @param returnCode
     */
    public void setMsg(IReturnCode returnCode) {
        this.msg = returnCode.getMsg();
    }
    /**
     * 通过返回码枚举类设置BizMsg
     * @param returnCode
     */
    public void setBizMsg(IReturnCode returnCode) {
        this.bizMsg = returnCode.getBizMsg();
    }
    /**
     * 通过返回码枚举类设置所有属性（Code、Msg、BizCode、BizMsg）
     * @param returnCode
     */
    public void setCompose(IReturnCode returnCode) {
        this.code = returnCode.getCode();
        this.msg = returnCode.getMsg();
        this.bizCode = returnCode.getBizCode();
        this.bizMsg = returnCode.getBizMsg();
    }
    /**
     * 通过返回码枚举类设置Code相关属性（Code、BizCode）
     * @param returnCode
     */
    public void setCodeCompose(IReturnCode returnCode) {
        this.code = returnCode.getCode();
        this.bizCode = returnCode.getBizCode();
    }
}
