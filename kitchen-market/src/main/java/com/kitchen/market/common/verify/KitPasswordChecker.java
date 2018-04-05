package com.kitchen.market.common.verify;

/**
 * 密码安全等级检查器
 *
 * @date 2016-12-29
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class KitPasswordChecker {
    /**
     * 校验密码安全等级。
     * 数字越大安全等级约高，在使用时，根据密码类型判断检查结果，决定是否通过验证即可
     * @return
     * 99：正常密码
     * 0：密码为空
     * 1：密码长度小于6
     * 2: 密码只包含一种类型的字符
     */
    public static PasswordType checkPasswordLevel(String password) {
        // 空
        if (password.isEmpty()) {
            return PasswordType.EMPTY;
        }
        // 不足6位
        if (password.length() < 6) {
            return PasswordType.SIX_CHAR;
        }
        // 只包含一种
        String regLowNumber = "[\\d]+";
        String regLowChar = "[a-zA-Z]+";
        String regLow = "[@_]+";
        if (password.matches(regLowNumber) || password.matches(regLowChar) || password.matches(regLow)) {
            return PasswordType.ONLY_ONE_TYPE;
        }

        return PasswordType.NORMAL;
    }
}
