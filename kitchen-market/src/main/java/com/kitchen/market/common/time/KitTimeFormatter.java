package com.kitchen.market.common.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日期时间格式化
 *
 * @date 2016-12-29
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class KitTimeFormatter {

    /**
     * 命名规则
     * s：分隔符
     * _：空格
     */
    public static String YYYY$MM$DD_HH$MM$SS_SSS = "yyyy-MM-dd HH:mm:ss SSS";
    public static String YYYY$MM$DD_HH$MM$SS = "yyyy-MM-dd HH:mm:ss";
    public static String YYYY$MM$DD = "yyyy-MM-dd";
    public static String HH$MM$SS = "HH:mm:ss";

    public static String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * DateTimeFormatter是线程安全的，可以提供给多线程并发使用
     */
    private static DateTimeFormatter CommonDateTimeMsFormatter = DateTimeFormatter.ofPattern(YYYY$MM$DD_HH$MM$SS_SSS);
    private static DateTimeFormatter CommonDateTimeFormatter = DateTimeFormatter.ofPattern(YYYY$MM$DD_HH$MM$SS);
    private static DateTimeFormatter CommonDateFormatter = DateTimeFormatter.ofPattern(YYYY$MM$DD);
    private static DateTimeFormatter CommonTimeFormatter = DateTimeFormatter.ofPattern(HH$MM$SS);

    /**
     * 缓存DateTimeFormatter，使用线程安全的ConcurrentHashMap，支持并发访问
     */
    private static Map<String, DateTimeFormatter> formatterCache = new ConcurrentHashMap<>();

    static {
        formatterCache.put(YYYY$MM$DD_HH$MM$SS_SSS, CommonDateTimeMsFormatter);
        formatterCache.put(YYYY$MM$DD_HH$MM$SS, CommonDateTimeFormatter);
        formatterCache.put(YYYY$MM$DD, CommonDateFormatter);
        formatterCache.put(HH$MM$SS, CommonTimeFormatter);
    }

    public static DateTimeFormatter getFormatter(String pattern) {
        DateTimeFormatter formatter = null;
        if (!formatterCache.containsKey(pattern)) {
            // 不存在此时间格式，则动态缓存至本地
            formatter = DateTimeFormatter.ofPattern(pattern);
            formatterCache.put(pattern, formatter);
        } else {
            formatter = formatterCache.get(pattern);
        }
        return formatter;
    }

    public static String formatNowToCommonDateTime() {
        return formatToCommonDateTime(new Date());
    }
    public static String formatToCommonDateTime(Date date) {
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime.format(CommonDateTimeFormatter);
    }

    public static String formatNowToCommonDate() {
        return formatToCommonDate(new Date());
    }
    public static String formatToCommonDate(Date date) {
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime.format(CommonDateFormatter);
    }

    public static String formatNowToCommonTime() {
        return formatToCommonTime(new Date());
    }
    public static String formatToCommonTime(Date date) {
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime.format(CommonTimeFormatter);
    }

    public static String formatNowToCommonDateTimeMs() {
        return formatToCommonDateTimeMs(new Date());
    }
    public static String formatToCommonDateTimeMs(Date date) {
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime.format(CommonDateTimeMsFormatter);
    }

    public static String formatNowToCustom(String pattern) {
        return formatToCustom(pattern, new Date());
    }

    public static String formatToCustom(String pattern, Date date) {
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        String result = "";
        if (formatterCache.containsKey(pattern)) {
            result = localDateTime.format(formatterCache.get(pattern));
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            formatterCache.put(pattern, formatter);
            result = localDateTime.format(formatter);
        }
        return result;
    }

}
