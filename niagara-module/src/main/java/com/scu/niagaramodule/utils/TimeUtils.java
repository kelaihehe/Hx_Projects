package com.scu.niagaramodule.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtils {

    /**
     * 获取当天零点时间戳
     * @return
     */
    public static Long getTodayZeroPoint() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long time = calendar.getTimeInMillis();
        return time;
    }

    /**
     * 获取N天前(包括当天)的零点时间戳
     * @return
     */
    public static Long getNDayAgoZeroPoint(int n) {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.add(Calendar.DAY_OF_MONTH,-(n-1));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long time = calendar.getTimeInMillis();
        return time;
    }

    /**
     * 获取一个月前(30天，包括当天)的零点时间戳
     * @return
     */
    public static Long getAMonthAgoZeroPoint() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.add(Calendar.DAY_OF_MONTH,-29);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long time = calendar.getTimeInMillis();
        return time;
    }

    /**
     * 获取当月零点时间戳
     * @return
     */
    public static Long getTheMondayZeroPoint() {
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Long time = calendar.getTimeInMillis();
        return time;
    }

    /**
     * 根据timestamp获取该时间点所在小时段的前端点
     * @param timestamp
     * @return
     */
    public static Long getHourPre(Long timestamp){
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeInMillis(timestamp); //将时间戳转换成calendar对象
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 根据timestamp获取该时间点所在小时段的后端点
     * @param timestamp
     * @return
     */
    public static Long getHourTail(Long timestamp){
        Calendar calendar = Calendar.getInstance();// 获取当前日期
        calendar.setTimeInMillis(timestamp); //将时间戳转换成calendar对象
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTimeInMillis();
    }

}
