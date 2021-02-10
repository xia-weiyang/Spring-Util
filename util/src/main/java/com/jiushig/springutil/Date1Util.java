package com.jiushig.springutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xia-weiyang on 2017/6/2.
 * 时间处理工具类
 * 所有的默认时间都是秒为单位
 */
public class Date1Util {

    private static final Logger logger = LoggerFactory.getLogger(Date1Util.class);

    public static final String FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DEFAULT_YMD = "yyyy-MM-dd";

    private static final SimpleDateFormat dateFormatDefault = new SimpleDateFormat(FORMAT_DEFAULT);

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
        return dateFormatDefault.format(new Date(System.currentTimeMillis() + (offset * 1000)));
    }

    public static long getCurrentTimestamp() {
        return System.currentTimeMillis() / 1000;
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
        return convertTime(calendar.getTimeInMillis() / 1000, FORMAT_DEFAULT);
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
        return convertTime(calendar.getTimeInMillis() / 1000, FORMAT_DEFAULT);
    }


    /**
     * 将 yyyy-MM-dd HH:mm:ss 时间转换成时间戳
     *
     * @param time
     * @return 如果失败 则返回0
     */
    public static long convertTime(String time) {
        if (CommonUtil.isEmpty(time)) return 0;
        Date date = null;
        try {
            date = dateFormatDefault.parse(time);
        } catch (ParseException e) {
            logger.error(String.format("time pares error %s", time), e);
        }
        final var t = date != null ? date.getTime() : 0;
        return t / 1000;
    }


    /**
     * 将时间戳转换成字符串
     *
     * @param time
     * @param format
     * @return
     */
    public static String convertTime(long time, String format) {
        SimpleDateFormat mDateFormat = new SimpleDateFormat(format);
        return mDateFormat.format(new Date(time * 1000));
    }

    public static String convertTime(long time) {
        return convertTime(time, FORMAT_DEFAULT);
    }

    /**
     * 与当前时间进行比较
     *
     * @param time
     * @return
     */
    public static long compareCurrentTime(long time) {
        if (time < 0) throw new RuntimeException("time value error");
        return System.currentTimeMillis() / 1000 - time;
    }

    /**
     * 得到当前时间之前的时间
     *
     * @param minutes
     * @return
     */
    public static String getPreviousTime(int minutes) {
        long currentTime = System.currentTimeMillis();
        currentTime -= (minutes * 60 * 1000);
        return dateFormatDefault.format(new Date(currentTime));
    }
}

