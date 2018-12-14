package com.java.utils.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>时间格式化
 * <p>@author DRAGON
 * <p>@date 2015年6月10日
 * <p>@version 1.0
 */
public class DateUtils {

    public static final String YYYYMMddHHmmss = "yyyyMMddHHmmss";
    public static final String YYYYMMdd = "yyyyMMdd";
    public static final String HHmmss = "HHmmss";
    public static final String YYYY_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_dd = "yyyy-MM-dd";
    public static final String HH_mm_ss = "HH:mm:ss";


    /**
     * 时间
     *
     * @return
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 时间戳
     *
     * @return
     */
    public static long timestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 1970年参考的毫秒时间转换成为字符串
     *
     * @param date
     * @param format
     * @return
     */
    public static String Long2String(Long date, String format) {
        if (null == format || null == date) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String result = sdf.format(date);
        return result;
    }

    /**
     * 获取当前时间
     *
     * @param format
     * @return
     */
    public static String nowaday(String format) {
        if (null == format) {
            return "";
        }
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat(format);
        String result = df.format(date);
        return result;
    }

    /**
     * 获取当前时间
     *
     * @param format
     * @return
     */
    public static Date str2date(String date, String format) {
        if (null == format || null == date) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(format);

        Date dat;
        try {
            dat = df.parse(format);
            return dat;
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取想个日期
     *
     * @param dayOffect
     * @return
     */
    public static String offectDay(int dayOffect) {
        DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_dd);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, dayOffect);
        String date = dateFormat.format(calendar.getTime());
        return date;
    }

    /**
     * 获取相隔日期时间
     *
     * @param dayOffect
     * @return
     */
    public static Date offectTime(int dayOffect) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, dayOffect);
        return calendar.getTime();
    }


    /**
     * 两个时间之间相差距离多少天
     *
     * @param str1 时间参数1：
     * @param str2 时间参数2：
     * @return 相差天数
     */
    public static long getDistanceDays(String str1, String str2) throws Exception {
        DateFormat df = new SimpleDateFormat(YYYY_MM_dd);
        Date one;
        Date two;
        long days = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }


    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数1格式：1990-01-01 12:00:00
     * @param str2 时间参数2格式：2009-01-01 12:00:00
     * @return long[]返回值为：{天,时,分,秒}
     */
    public static long[] getDistanceTimes(String str1, String str2) {
        DateFormat df = new SimpleDateFormat(YYYY_MM_dd_HH_mm_ss);
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long[] times = {day, hour, min, sec};
        return times;
    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     *
     * @param str1 时间参数1格式：1990-01-01 12:00:00
     * @param str2 时间参数2格式：2009-01-01 12:00:00
     * @return String返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat(YYYY_MM_dd_HH_mm_ss);
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day + "天" + hour + "小时" + min + "分" + sec + "秒";
    }


}
