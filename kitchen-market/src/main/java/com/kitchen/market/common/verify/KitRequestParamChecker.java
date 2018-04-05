package com.kitchen.market.common.verify;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 请求参数验证工具集合
 *
 * @date 2016-12-29
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class KitRequestParamChecker {
    /**
     * 非零正整数
     * @param str
     * @return
     */
    public static boolean isPositiveInteger(String str){
        Pattern pattern = Pattern.compile("^\\+?[1-9][0-9]*$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    /**
     * 正负正数
     * @param str
     * @return
     */
    public static boolean isInteger(String str){
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
    /**
     * 大于零的数字
     * @param str
     * @return
     */
    public static boolean isPositiveNumber(String str){
        Pattern pattern = Pattern.compile("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
