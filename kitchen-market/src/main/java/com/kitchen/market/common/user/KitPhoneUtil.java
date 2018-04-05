package com.kitchen.market.common.user;

import org.apache.commons.lang3.StringUtils;

/**
 * 用户手机号码处理
 *
 * @date 2016-12-29
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class KitPhoneUtil {
    /**
     * 手机号码保密
     * 手机号码要求为11位（只显示前3位和后四位），隐藏中间4位
     * @param phoneNumber
     * @return
     */
    public static String hiddenPhoneNumber(String phoneNumber) {
        String hiddenPhone = "";
        if(StringUtils.isNotBlank(phoneNumber) && phoneNumber.length() == 11) {
            hiddenPhone = phoneNumber.substring(0,3) + "****" + phoneNumber.substring(7,11);
        }
        return hiddenPhone;
    }
}
