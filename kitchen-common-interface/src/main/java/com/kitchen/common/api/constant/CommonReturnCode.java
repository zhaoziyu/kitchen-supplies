package com.kitchen.common.api.constant;

import com.kitchen.common.api.constant.code.IReturnCode;

/**
 * 通用接口返回码
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-08-18
 */
public enum CommonReturnCode implements IReturnCode {
    /**
     * 返回码规则、范围
     * code返回码使用5位数字，存储网关返回码（可以看做对错误码的分类或错误码的类型）
     * bizCode返回码使用以英文分隔符分隔的大写字符串，可以自定义规则（建议使用“模块.子模块.业务异常”），存储业务返回码
     * code + bizCode可唯一确认一个异常
     *
     * 00000-50000和99999为Kitchen提供的平台通用返回码
     * 50001-99998可作为业务系统自定义返回码
     *
     * 通用返回码的bizCode，无前缀
     */

    // 00000
    SUCCESS(CommonReturnCode.RETURN_CODE_00000, "", "SUCCESS", ""),

    // 10000 - 网关异常
    GATEWAY_PARAM_ERROR(CommonReturnCode.RETURN_CODE_10000, CommonReturnCode.RETURN_CODE_10000_DESC, "PARAM_ERROR", "请求的接口参数无效"),

    // 11000 - 签名验证失败
    GATEWAY_SIGN_PARAMS_ABSENT(CommonReturnCode.RETURN_CODE_11000, CommonReturnCode.RETURN_CODE_11000_DESC, "SIGN_PARAMS_ABSENT", "缺少签名参数"),
    GATEWAY_SIGN_FORMAT_FAIL(CommonReturnCode.RETURN_CODE_11000, CommonReturnCode.RETURN_CODE_11000_DESC, "SIGN_PARAMS_FORMAT_FAIL", "签名参数格式错误"),
    GATEWAY_CHECK_SIGN_FAIL(CommonReturnCode.RETURN_CODE_11000, CommonReturnCode.RETURN_CODE_11000_DESC, "CHECK_SIGN_FAIL", "签名验证错误"),
    GATEWAY_CHECK_TIMESTAMP_FAIL(CommonReturnCode.RETURN_CODE_11000, CommonReturnCode.RETURN_CODE_11000_DESC, "CHECK_TIMESTAMP_FAIL", "请求超时"),
    GATEWAY_CHECK_TOKEN_FAIL(CommonReturnCode.RETURN_CODE_11000, CommonReturnCode.RETURN_CODE_11000_DESC, "CHECK_TOKEN_FAIL", "Token验证错误"),
    GATEWAY_SIGN_PUBLIC_KEY_ABSENT(CommonReturnCode.RETURN_CODE_11000, CommonReturnCode.RETURN_CODE_11000_DESC, "SIGN_PUBLIC_KEY_ABSENT", "未找到对应的公钥"),
    GATEWAY_SIGN_TYPE_FAIL(CommonReturnCode.RETURN_CODE_11000, CommonReturnCode.RETURN_CODE_11000_DESC, "SIGN_TYPE_FAIL", "未找到符合条件的签名类型"),
    GATEWAY_TOKEN_ABSENT(CommonReturnCode.RETURN_CODE_11000, CommonReturnCode.RETURN_CODE_11000_DESC, "TOKEN_ABSENT", "Token不存在或已过期"),

    // 20000 - 资源异常
    OBJ_ABSENT(CommonReturnCode.RETURN_CODE_20000, CommonReturnCode.RETURN_CODE_20000_DESC, "OBJ_ABSENT", "操作的业务对象不存在"),
    SERVICE_ABSENT(CommonReturnCode.RETURN_CODE_20000, CommonReturnCode.RETURN_CODE_20000_DESC, "SERVICE_ABSENT", "服务暂不可用"),

    // 20100 - 资源权限异常
    USER_NO_PERMISSION(CommonReturnCode.RETURN_CODE_21000, CommonReturnCode.RETURN_CODE_21000_DESC, "USER_NO_PERMISSION", "用户无权限操作所请求资源"),

    // 50000 - 程序异常
    PROGRAM_ERROR(CommonReturnCode.RETURN_CODE_50000, CommonReturnCode.RETURN_CODE_50000_DESC, "500", ""),
    PROGRAM_FAILURE(CommonReturnCode.RETURN_CODE_50000, CommonReturnCode.RETURN_CODE_50000_DESC, "PROGRAM_FAILURE", "程序原因，执行失败"),

    // 99999 - 未知异常
    UNKNOWN_ERROR(CommonReturnCode.RETURN_CODE_99999, CommonReturnCode.RETURN_CODE_99999_DESC, "FAIL", "");

    private String _code;
    private String _msg;
    private String _bizCode;
    private String _bizMsg;

    CommonReturnCode(String code, String msg, String bizCode, String bizMsg) {
        this._code = code;
        this._msg = msg;
        this._bizCode = bizCode;
        this._bizMsg = bizMsg;
    }

    /**
     * 系统预留返回码
     */
    public final static String RETURN_CODE_00000 = "00000";
    public final static String RETURN_CODE_10000 = "10000";
    public final static String RETURN_CODE_10000_DESC = "网关异常";
    public final static String RETURN_CODE_11000 = "11000";
    public final static String RETURN_CODE_11000_DESC = "签名异常";
    public final static String RETURN_CODE_20000 = "20000";
    public final static String RETURN_CODE_20000_DESC = "资源异常";
    public final static String RETURN_CODE_21000 = "21000";
    public final static String RETURN_CODE_21000_DESC = "权限异常";
    public final static String RETURN_CODE_50000 = "50000";
    public final static String RETURN_CODE_50000_DESC = "程序异常";
    public final static String RETURN_CODE_99999 = "99999";
    public final static String RETURN_CODE_99999_DESC = "未知异常";

    @Override
    public String getCode() {
        return this._code;
    }

    @Override
    public String getMsg() {
        return this._msg;
    }

    @Override
    public String getBizCode() {
        return this._bizCode;
    }

    @Override
    public String getBizMsg() {
        return this._bizMsg;
    }

    @Override
    public String toString() {
        return this._code + "-" + this._bizCode;
    }
}
