package com.jiushig.springutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Guowang on 2017/6/2.
 * 时间处理工具类
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static final String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取系统当前时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentTime() {
        return getCurrentTime(0);
    }

    /**
     * 获得偏移的系统当前时间
     *
     * @param offset
     * @return
     */
    public static String getCurrentTime(long offset) {
        SimpleDateFormat mDateFormat = new SimpleDateFormat(FORMAT_DEFAULT);
        return mDateFormat.format(new Date(System.currentTimeMillis() + offset));
    }

    /**
     * 获得今天的开始时间
     *
     * @return
     */
    public static String getTodayStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return convertTime(calendar.getTimeInMillis(), FORMAT_DEFAULT);
    }

    /**
     * 获得今天的结束时间
     *
     * @return
     */
    public static String getTodayEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return convertTime(calendar.getTimeInMillis(), FORMAT_DEFAULT);
    }


    /**
     * 将 yyyy-MM-dd HH:mm:ss 时间转换成long
     *
     * @param time
     * @return 如果失败 则返回0
     */
    public static long convertTime(String time) {
        SimpleDateFormat mDateFormat = new SimpleDateFormat(FORMAT_DEFAULT);
        Date date = null;
        try {
            date = mDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date != null ? date.getTime() : 0;
    }

    public static int convertTimeInt(String time) {
        return (int) (convertTime(time) / 1000);
    }

    /**
     * 将时间戳转换成字符串
     *
     * @param time
     * @param format
     * @return
     */
    public static String convertTime(long time, String format) {
        SimpleDateFormat mDateFormat = new SimpleDateFormat(FORMAT_DEFAULT);
        return mDateFormat.format(new Date(time));
    }

    /**
     * 与当前时间进行比较
     *
     * @param time
     * @return
     */
    public static long compareCurrentTime(long time) {
        return System.currentTimeMillis() - time;
    }
}

