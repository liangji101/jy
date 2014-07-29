package com.renren.ntc.video.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * User: liwenjun@ikaku.com
 * Date: 11-8-23
 * Time: 下午3:56
 * .
 */
public class DateHelper {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM月dd日 HH:mm");

    /**
     * 刚刚, xxx秒以前,xxx分钟以前,xxx小时以前
     *
     * @param timestamp
     * @return
     */
    public static String format(int timestamp) {
        return format(null, timestamp, true);
    }

    /**
     * 刚刚, xxx秒以前,xxx分钟以前,xxx小时以前
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        if (date == null)
            return null;
        return format(null, (int) (date.getTime() / 1000), true);
    }

    public static String format(String dateformat, int timestamp) {
        if (dateformat == null) {
            return dateFormat.format(timestamp * 1000L);
        }
        return new SimpleDateFormat(dateformat).format(timestamp * 1000L);
    }

    public static String format(String dateformat, int timestamp,
                                boolean format) {
        String result;
        if (format) {
            int time = (int) (System.currentTimeMillis() / 1000) - timestamp;
            if (time > 86400) {
                result = format(dateformat, timestamp);
            } else if (time > 3600) {
                result = (time / 3600) + "小时以前";
            } else if (time > 60) {
                result = (time / 60) + "分钟以前";
            } else if (time > 0) {
                result = time + "秒以前";
            } else {
                result = "刚刚";
            }
        } else {
            result = format(dateformat, timestamp);
        }
        return result;
    }
}
