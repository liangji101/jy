package com.renren.ntc.video.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * 检查日期合法性
     * 
     * @param s String
     * @return boolean
     */
    public static Date parseDate(String s, String format) throws ParseException {
      //TODO DataUtils.parseDate算法有问题，年数可以5位数，更换新方法。
        Date date = null;
        DateFormat df = new SimpleDateFormat(format);
        df.setLenient(false);
        date = df.parse(s);
        return date;
    }
    
    /**
     * 将时间转换为固定格式 yyyy-MM-dd HH:mm:ss
     * 
     * @param date
     * @param format
     * @return
     */
    public static String getTime(Date date, String format) {
        SimpleDateFormat sim = new SimpleDateFormat(format);
        try {
            return sim.format(date);
        } catch (Exception e) {
            //          e.printStackTrace(System.err);
        }
        return null;
    }


    public static String dateFormatPattern = "yyyy-MM-dd HH:mm"; //"yyyy-MM-dd HH:mm:ss";

    //modified end 
    /**
     * 获得当前时间
     * 
     * @return String
     */

    public static String getDate() {
        Calendar date = Calendar.getInstance();
        SimpleDateFormat sim = new SimpleDateFormat(dateFormatPattern);
        String str = sim.format(date.getTime());
        return str;

    }

    /**
     * 字符串转换为时间
     *
     * @param date String
     * @return Date
     */
    public static Date getDate(String date) {

        try {
            SimpleDateFormat localTime = new SimpleDateFormat(dateFormatPattern);
            Date date1 = localTime.parse(date);
            return date1;
        }

        catch (ParseException ex) {
            ex.printStackTrace();
        }
        return null;

    }

    /**
     * @param timeMillis 毫秒级的时间
     * @return
     */
    public static String getTime(String timeMillis) {
        try {
            Date date = new Date(Long.valueOf(timeMillis));
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern);
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将时间转换为固定格式 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String getTime(Date date) {
        SimpleDateFormat sim = new SimpleDateFormat(dateFormatPattern);
        try {
            return sim.format(date);
        } catch (Exception e) {
            //          e.printStackTrace(System.err);
        }
        return null;
    }

    /**
     * 取得秒数
     */
    public static Long getDateDiff_Second(Date d1, Date d2) {
        return (d2.getTime() - d1.getTime()) / 1000;
    }

    /**
     * 取得分钟
     *
     * @param d1 Date
     * @param d2 Date
     * @return Long
     */
    public static Long getDateDiff_Minute(Date d1, Date d2) {
        return (d2.getTime() - d1.getTime()) / (1000 * 60);
    }

    /**
     * 取得小时
     *
     * @param d1 Date
     * @param d2 Date
     * @return Long
     */
    public static Long getDateDiff_Hour(Date d1, Date d2) {
        return (d2.getTime() - d1.getTime()) / (1000 * 3600);
    }

    public static Long getDateDiff_Hour(String d1, String d2) {
        return (getDate(d2).getTime() - getDate(d1).getTime()) / (1000 * 3600);
    }

    /**
     * 取得天数
     *
     * @param d1 Date
     * @param d2 Date
     * @return Long
     */
    public static Long getDateDiff_Day(Date d1, Date d2) {
        return (d2.getTime() - d1.getTime()) / (1000 * 3600 * 24);
    }

    public static Long getDateDiff_Day(String d1, String d2) {
        return (getDate(d2).getTime() - getDate(d1).getTime()) / (1000 * 3600 * 24);
    }

    /**
     * 取得星期间隔
     *
     * @param d1 Date
     * @param d2 Date
     * @return Long
     */
    public static Long getDateDiff_Week(Date d1, Date d2) {
        return (d2.getTime() - d1.getTime()) / (1000 * 3600 * 24 * 7);
    }

    /**
     * 取得当前时间的 间隔多少小时之后的时间
     *
     * @param hour int
     * @return String
     */
    public static String getDateAdd(int hour) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar.set(Calendar.HOUR, hour + calendar.get(Calendar.HOUR));
        String enddate = sd.format(calendar.getTime());
        return enddate;

    }

    /**
     * 取得当前时间的 间隔多少小时之后的时间
     *
     * @param hour int
     * @return String
     */
    public static String getDateAdd(String starttime, int hour) {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sd = new SimpleDateFormat(dateFormatPattern);
        calendar.setTime(getDate(starttime));
        calendar.set(Calendar.HOUR, hour + calendar.get(Calendar.HOUR));
        String date = sd.format(calendar.getTime());
        return date;

    }

    /**
     * 获得时间格式的文件名称
     *
     * @return String
     */
    public static String getFileName() {
        Calendar date = Calendar.getInstance();
        SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd_hhmmss");
        String str = sim.format(date.getTime());
        return str;
    }

    // 获得月日
    @SuppressWarnings("finally")
    public static String get_MM_DD(String s) {
        SimpleDateFormat sim = new SimpleDateFormat(dateFormatPattern);
        Date date;
        String str = "";
        try {
            date = sim.parse(s);
            sim = new SimpleDateFormat("[MM-dd]");
            str = sim.format(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return str;
        }
    }

    // 获得年月日

    @SuppressWarnings("finally")
    public static String get_YYYY_MM_DD(String s) {
        SimpleDateFormat sim = new SimpleDateFormat(dateFormatPattern);
        Date date;
        String str = "";
        try {
            date = sim.parse(s);
            sim = new SimpleDateFormat("[yyyy-MM-dd]");
            str = sim.format(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return str;
        }
    }
    
    /**
     * 返回以秒为单位的当前时间的Unix时间戳
     * 
     * @return
     */
    public static long getUnixEpoch() {
        return System.currentTimeMillis() / 1000;
    }
    private static final long ONE_MINITE = 60*1000;
    private static final long ONE_HOUR = 60*60*1000;
    private static final long ONE_DAY = 24*60*60*1000;
    
    public static String convertTime(Date date){
    	String time = "";
		long createTime = date.getTime();
		long now = System.currentTimeMillis();
		long length = now - createTime;
		//小于一小时,按分钟算
		if(length < ONE_HOUR){
			int n = (int) (length/ONE_MINITE);
			if(n <= 0){
				n = 1;
			}
			time = n + "分钟前";
			//小于24小时，按小时算
		}else if(length < 24*ONE_HOUR){
			int n = (int) (length/(ONE_HOUR));
			time = n + "小时前";
			//3天内，用天数算
		}else if(length < 4*ONE_DAY){
			int n = (int) (length/ONE_DAY);
			time = n + "天前";
			//大于3天用"n天前"
		}else{
			time = "n天前";
		}
		return time;
    }

    public static String getYesterdayBeginToString() {
        Calendar now = getYesterdayBegin();
        return dateFormat.format(now.getTime());
    }

    private static Calendar getYesterdayBegin() {
        Calendar now = Calendar.getInstance();
        //昨天
        now.add(Calendar.DAY_OF_YEAR, -1);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        return now;
    }

    public static long getYesterdayBeginInLong() {
        Calendar now = getYesterdayBegin();
        return now.getTimeInMillis();
    }

    public static long getYesterdayEndInLong() {
        Calendar now = getYesterdayEnd();
        return now.getTimeInMillis();
    }

    private static Calendar getYesterdayEnd() {
        Calendar now = Calendar.getInstance();
        //昨天
        now.add(Calendar.DAY_OF_YEAR, -1);
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);
        return now;
    }

    public static String getYesterdayEndToString() {
        Calendar now = getYesterdayEnd();
        return dateFormat.format(now.getTime());
    }

    public static int fromNowTo24InSecond() {
        Calendar calendar = Calendar.getInstance();
        long now = calendar.getTimeInMillis();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return Long.valueOf((calendar.getTimeInMillis() - now) / 1000).intValue();
    }
    
    public static String fromDateToString(Date date){
    	return dateFormat.format(date);
    }
}
