package com.kitchen.market.common.verify;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 主机地址（IP）检查器
 *
 * @date 2017-04-20
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class KitHostChecker {
    public static boolean isIPv4(String ip) {
        if(ip.length() < 7 || ip.length() > 15 || "".equals(ip)) {
            return false;
        }
        String rexp =
                "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                        +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                        +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                        +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(ip);

        return mat.find();
    }
}
