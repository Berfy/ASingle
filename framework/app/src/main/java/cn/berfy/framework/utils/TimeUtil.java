package cn.berfy.framework.utils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {

    /**
     * 获取每个月最后一天
     */
    public static int getLastDayOfMonth(int year, int month) {
        if (month == 0) {
            month = 12;
        }
        if (month == 13) {
            month = 1;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);// 设置为下个月
        cal.set(Calendar.DATE, 1); // 设置为该月第一天
        cal.add(Calendar.DATE, -1); // 再减一天即为上个月(这个月)最后一天
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 判断前后年份
     *
     * @param isPreMonth true判断上个月年份 false判断下个月年份
     */
    public static int getTrueYear(int year, int month, boolean isPreMonth) {
        if (isPreMonth) {
            if (month <= 1) {
                year--;
            }
        } else {
            if (month >= 12) {
                year++;
            }
        }
        return year;
    }

    /**
     * 判断前后月份
     *
     * @param isPreMonth true判断上个月 false判断下个月
     */
    public static int getTrueMonth(int month, boolean isPreMonth) {
        if (isPreMonth) {
            month--;
            if (month < 1) {
                month = 12;
            }
        } else {
            month++;
            if (month > 12) {
                month = 1;
            }
        }
        return month;
    }

    public static String getCurrentDate() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return year + "-" + (month < 10 ? "0" + month : month) + "-"
                + (day < 10 ? "0" + day : day);
    }

    public static String getYesterDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1;
        return year + "-" + (month < 10 ? "0" + month : month) + "-"
                + (day < 10 ? "0" + day : day);
    }

    public static String getCurrentTime() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        int second = Calendar.getInstance().get(Calendar.SECOND);
        return year + "-" + (month < 10 ? "0" + month : month) + "-"
                + (day < 10 ? "0" + day : day) + " " + (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second);
    }

    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取两个日期之间的间隔天数
     *
     * @return
     */
    public static int getGapCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() - fromCalendar.getTime()
                .getTime()) / (1000 * 60 * 60 * 24));
    }


    public static String timeFormat(long timeMillis, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.CHINA);
        return format.format(new Date(timeMillis));
    }

    public static String formatPhotoDate(long time) {
        return timeFormat(time, "yyyy-MM-dd");
    }

    public static String formatPhotoDate(String path) {
        File file = new File(path);
        if (file.exists()) {
            long time = file.lastModified();
            return formatPhotoDate(time);
        }
        return "1970-01-01";
    }

    public static String[] WEEK = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
            "星期六" };

    /** 获取时间 */
    public static String getTime(String time) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String formatTime = format.format(format.parse(time));
            String nowTime = format.format(date);
            int nowWeek;// 今天星期几
            if (formatTime.equals(nowTime)) {// 今天
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date nowDate = format.parse(time);
                format = new SimpleDateFormat("HH:mm");
                String str = format.format(nowDate);
                return str;
            } else {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DAY_OF_MONTH, -6);// 获取7天前日期
                int sevenDays = Integer.valueOf(format.format(cal.getTime())
                        .replaceAll("-", ""));
                if (sevenDays <= Integer
                        .valueOf(formatTime.replaceAll("-", ""))) {
                    // 如果在一周之内 获取星期几
                    date = format.parse(time);
                    cal = Calendar.getInstance();
                    cal.setTime(date);
                    Calendar cc = Calendar.getInstance();
                    cc.setTime(format.parse(nowTime));
                    cc.add(Calendar.DAY_OF_WEEK, -1);
                    if (cc.get(Calendar.DAY_OF_WEEK) - 1 == cal
                            .get(Calendar.DAY_OF_WEEK) - 1) {
                        return "昨天";
                    }
                    return WEEK[cal.get(Calendar.DAY_OF_WEEK) - 1];
                } else {
                    // 如果时间大于一周显示年月日
                    format = new SimpleDateFormat("yyyy-MM-dd");
                    return format.format(format.parse(time));
                }
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
}
