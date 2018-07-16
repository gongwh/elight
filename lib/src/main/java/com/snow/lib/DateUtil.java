package com.snow.lib;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SNOW on 2018.02.08.
 */
public class DateUtil {

    /**
     * 将日期字符串转换成指定格式的日期
     *
     * @param dateString 日期字符串
     * @param format     格式化模型,如："yyyy-MM-dd HH:mm:ss"
     * @return date 指定格式的日期
     * @author tmc.sun
     */
    public static Date parseDate(String dateString, String format) {
        Date date = null;

        try {
            DateFormat df = new SimpleDateFormat(format);
            date = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将日期转换为指定格式的日期字符串
     *
     * @param date   日期
     * @param format 格式化模型,如："yyyy-MM-dd HH:mm:ss"
     * @return 格式化后的日期字符串
     * @author tmc.sun
     */
    public static String formatDate(Date date, String format) {
        if (date == null) {
            return "";
        }

        DateFormat df = new SimpleDateFormat(format);
        String dateString = df.format(date);
        return dateString;
    }


    /**
     * 判断两个时间戳是否在目标区间内
     *
     * @param timeTargetLong 目标时间戳
     * @param timeLowLong    起始时间戳
     * @param timeHighLong   终止时间戳
     * @return
     * @throws ParseException
     */
    private static boolean isBetweenIn(long timeTargetLong, long timeLowLong, long timeHighLong) throws ParseException {
        return timeTargetLong > timeLowLong && timeTargetLong < timeHighLong;
    }

    /**
     * 获取指定格式的时间到Date
     *
     * @param target  对应pattern的时间字符串
     * @param pattern 时间格式字符组成的字符串<BR/>
     *                例如: &#9;yyyy年MM月dd日 HH时mm分ss秒<BR/>
     *                &#9;yyyy年MM月dd日 HH时mm分ss秒 E<BR/>
     *                可用格式字符：<BR/>
     *                G&#9;年代标志符<BR/>
     *                y&#9;年<BR/>
     *                M&#9;月<BR/>
     *                d&#9;日<BR/>
     *                h&#9;时 在上午或下午 (1~12)<BR/>
     *                H&#9;时 在一天中 (0~23)<BR/>
     *                m&#9;分<BR/>
     *                s&#9;秒<BR/>
     *                S&#9;毫秒<BR/>
     *                E&#9;星期<BR/>
     *                D&#9;一年中的第几天<BR/>
     *                F&#9;一月中第几个星期几<BR/>
     *                w&#9;一年中第几个星期<BR/>
     *                W&#9;一月中第几个星期<BR/>
     *                a&#9;上午 / 下午 标记符<BR/>
     *                k&#9;时 在一天中 (1~24)<BR/>
     *                K&#9;时 在上午或下午 (0~11)<BR/>
     *                z&#9;时区<BR/>
     * @return
     * @throws ParseException
     */
    public static Date getDate(String target, String pattern) throws ParseException {
        SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
        Date result = null;
        df.applyPattern(pattern);
        result = df.parse(target);
        return result;
    }


    /**
     * 判断指定格式的日期是否在指定时间段内
     *
     * @param timeTarget 例如: 15:30
     * @param timeLow    例如：13:00
     * @param timeHigh   例如： 16:30
     * @param pattern    例如:HH:mm
     * @return
     * @throws ParseException
     */
    public static boolean isDateIn(String timeTarget, String timeLow, String timeHigh, String pattern) throws ParseException {
        SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
        df.applyPattern(pattern);
        long timeTargetLong = df.parse(timeTarget).getTime();
        long timeLowLong = df.parse(timeLow).getTime();
        long timeHighLong = df.parse(timeHigh).getTime();
        return isBetweenIn(timeTargetLong, timeLowLong, timeHighLong);
    }

    /**
     * 判断日期是否在指定格式的日期内
     *
     * @param targetTime
     * @param timeLow
     * @param timeHigh
     * @param pattern    例如:HH:mm
     * @return
     * @throws ParseException
     */
    public static boolean isDateIn(Date targetTime, Date timeLow, Date timeHigh, String pattern) throws ParseException {
        SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
        df.applyPattern(pattern);
        long timeTargetLong = df.parse(df.format(targetTime)).getTime();
        long timeLowLong = df.parse(df.format(timeLow)).getTime();
        long timeHighLong = df.parse(df.format(timeHigh)).getTime();
        return isBetweenIn(timeTargetLong, timeLowLong, timeHighLong);
    }

    /**
     * 判断日期是否在指定格式的日期内
     *
     * @param targetTime
     * @param timeLow
     * @param timeHigh
     * @param pattern    例如:HH:mm
     * @return
     * @throws ParseException
     */
    public static boolean isDateIn(Date targetTime, String timeLow, String timeHigh, String pattern) throws ParseException {
        SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
        df.applyPattern(pattern);
        long timeTargetLong = df.parse(df.format(targetTime)).getTime();
        long timeLowLong = df.parse(timeLow).getTime();
        long timeHighLong = df.parse(timeHigh).getTime();
        return isBetweenIn(timeTargetLong, timeLowLong, timeHighLong);
    }

    /**
     * 判断日期是否在指定格式的日期内
     *
     * @param targetTime
     * @param timeLow
     * @param timeHigh
     * @return
     * @throws ParseException
     */
    public static boolean isDateIn(Date targetTime, Date timeLow, Date timeHigh) throws ParseException {
        long timeTargetLong = targetTime.getTime();
        long timeLowLong = timeLow.getTime();
        long timeHighLong = timeHigh.getTime();
        return isBetweenIn(timeTargetLong, timeLowLong, timeHighLong);
    }

    /**
     * 返回当前的星期数1-7  1对应周日，2对应周一 ...
     *
     * @return
     */
    public static int getCurrentWeekDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

}
