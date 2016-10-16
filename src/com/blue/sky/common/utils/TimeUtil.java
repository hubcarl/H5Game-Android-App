package com.blue.sky.common.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2014/7/27.
 */
public class TimeUtil {


    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
     *
     * @param dateDate
     * @return
     */
    public static String dateToStrLong(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate
     * @return
     */
    public static String dateToStr(java.util.Date dateDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将短时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date strtodate = null;
        try {
            strtodate = formatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strtodate;
    }

    public static long strToTimeSpan(String strDate)
    {
        return strToDate(strDate).getTime();
    }

    public static String getFormatTime(Date date, String Sdf) {
        return (new SimpleDateFormat(Sdf)).format(date);
    }

    public static String transDate(String strDate) {

        Date date = strToDateLong(strDate);
        if(date==null)return strDate;

        //定义最终返回的结果字符串。
        String interval = null;

        long millisecond = new Date().getTime() - date.getTime();

        long second = millisecond / 1000;

        if (second <= 0) {
            second = 0;
        }

        if (second < 60) {
            interval = "刚刚";
        } else if (second >= 60 && second < 60 * 60) {
            long minute = second / 60;
            interval = minute + "分钟前";
        } else if (second >= 60 * 60 && second < 60 * 60 * 24) {
            long hour = (second / 60) / 60;
            if (hour <= 3) {
                interval = hour + "小时前";
            } else {
                interval = "今天" + getFormatTime(date, "hh:mm");
            }
        } else if (second >= 60 * 60 * 24 && second <= 60 * 60 * 24 * 2) {
            interval = "昨天" + getFormatTime(date, "hh:mm");
        } else if (second >= 60 * 60 * 24 * 2 && second <= 60 * 60 * 24 * 7) {
            long day = ((second / 60) / 60) / 24;
            interval = day + "天前";
        } else if (second >= 60 * 60 * 24 * 7) {
            interval = getFormatTime(date, "MM-dd hh:mm");
        } else if (second >= 60 * 60 * 24 * 365) {
            interval = getFormatTime(date, "YYYY-MM-dd hh:mm");
        } else {
            interval = "0";
        }
        // 最后返回处理后的结果。
        return interval;
    }
}
