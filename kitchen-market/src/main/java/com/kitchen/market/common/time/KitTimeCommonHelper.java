package com.kitchen.market.common.time;

import org.javatuples.Pair;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/**
 * 日期时间相关的处理
 *
 * @date 2016-12-29
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class KitTimeCommonHelper {

    /**
     * 获取当天的开始时间和结束时间
     * @return yyyy-MM-dd 00:00:01 and yyyy-MM-dd 23:59:59
     */
    public static Pair<Date, Date> getTodayRange() {
        String todayDate = LocalDate.now().toString();

        LocalDateTime todayBegin = LocalDateTime.parse(todayDate + " 00:00:01", KitTimeFormatter.getFormatter(KitTimeFormatter.YYYY$MM$DD_HH$MM$SS));
        LocalDateTime todayEnd = LocalDateTime.parse(todayDate + " 23:59:59", KitTimeFormatter.getFormatter(KitTimeFormatter.YYYY$MM$DD_HH$MM$SS));

        Date startDate = null;
        Date endDate = null;
        if (todayBegin != null && todayEnd != null) {
            startDate = Date.from(todayBegin.atZone(ZoneId.systemDefault()).toInstant());
            endDate = Date.from(todayEnd.atZone(ZoneId.systemDefault()).toInstant());
        }

        return Pair.with(startDate, endDate);
    }

    /**
     * 获取两个日期之间相差的天数
     * @return
     */
    public static int getDifferDays(Date date1, Date date2) {
        Date smallDate;
        Date bigDate;
        if (date1.getTime() > date2.getTime()) {
            smallDate = date2;
            bigDate = date1;
        } else {
            smallDate = date1;
            bigDate = date2;
        }

        LocalDate smallLocalDate = smallDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate bigLocalDate = bigDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return Period.between(smallLocalDate, bigLocalDate).getDays();
    }

    /**
     * 判断当前时间是否在指定的时间段内
     * @param beginTime 开始时间 HH:mm:ss
     * @param endTime 结束时间 HH:mm:ss
     * @return
     */
    public static boolean isInTimeRange(String beginTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(new Date());
        // 截取当前时间时分秒
        int strDateH = Integer.parseInt(strDate.substring(11, 13));
        int strDateM = Integer.parseInt(strDate.substring(14, 16));
        int strDateS = Integer.parseInt(strDate.substring(17, 19));
        int dateSum = strDateH * 3600 + strDateM * 60 + strDateS;
        // 截取开始时间时分秒
        int strDateBeginH = Integer.parseInt(beginTime.substring(0, 2));
        int strDateBeginM = Integer.parseInt(beginTime.substring(3, 5));
        int strDateBeginS = Integer.parseInt(beginTime.substring(6, 8));
        int dateBeginSum = strDateBeginH * 3600 + strDateBeginM * 60 + strDateBeginS;
        // 截取结束时间时分秒
        int strDateEndH = Integer.parseInt(endTime.substring(0, 2));
        int strDateEndM = Integer.parseInt(endTime.substring(3, 5));
        int strDateEndS = Integer.parseInt(endTime.substring(6, 8));
        int dateEndSum = strDateEndH * 3600 + strDateEndM * 60 + strDateEndS;

        if (dateSum == dateBeginSum || dateSum == dateEndSum) {
            // 验证边界
            return true;
        } else {
            if (dateSum > dateBeginSum && dateSum < dateEndSum) {
                return true;
            } else {
                return false;
            }
        }
    }
}
