package com.kitchen.market.common.verify;

/**
 * 密码等级
 *
 * @date 2016-12-29
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public enum PasswordType {
    EMPTY(0, "密码为空"),
    SIX_CHAR(1, "密码长度小于6位"),
    ONLY_ONE_TYPE(2, "密码只包含一种类型的字符"),
    NORMAL(99, "正常密码");

    private int _levelCode;     //等级
    private String _levelDesc;  //密码等级描述

    PasswordType(int level, String desc) {
        this._levelCode = level;
        this._levelDesc = desc;
    }

    public int getLevelCode() {
        return _levelCode;
    }

    public void setLevelCode(int _levelCode) {
        this._levelCode = _levelCode;
    }

    public String getLevelDesc() {
        return _levelDesc;
    }

    public void setLevelDesc(String _levelDesc) {
        this._levelDesc = _levelDesc;
    }
}
