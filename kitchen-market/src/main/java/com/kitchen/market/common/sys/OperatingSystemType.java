package com.kitchen.market.common.sys;

/**
 * 枚举操作系统类型
 *
 * @date 2017-04-19
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public enum OperatingSystemType {
    WINDOWS("Windows", 1),
    LINUX("Linux", 2),
    UNKNOWN("未知系统", 99);

    private int _code;
    private String _desc;

    OperatingSystemType(String desc, int code) {
        this._desc = desc;
        this._code = code;
    }

    public int getCode() {
        return this._code;
    }
    public String getDesc() {
        return this._desc;
    }

    @Override
    public String toString() {
        return String.valueOf(this._code);
    }
}
