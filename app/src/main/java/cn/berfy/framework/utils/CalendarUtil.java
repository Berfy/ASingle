package cn.berfy.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CalendarUtil {

    /**
     * 获取上一个月几天的日期
     *
     * @param calendar
     * @return
     */
    public static List<String> getLastMonthDays(Calendar calendar) {
        int firstMonthDay = getFirstMonthDay(calendar);
        // System.out.println("firstMonthDay:::"+firstMonthDay);
        List<String> lastMonthDays = new ArrayList<String>();
        // System.out.println("currentMonth::::"+calendar.get(Calendar.MONTH));
        calendar.add(Calendar.MONTH, -1);
        // System.out.println("LastMonth::::"+calendar.get(Calendar.MONTH));
        // calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
        List<String> lastMonthAllDays = getDaysFromCalendar(calendar);
        int length = lastMonthAllDays.size();
        if (firstMonthDay != Calendar.SUNDAY) {// 锟斤拷一锟届不锟斤拷锟斤拷锟斤拷
            for (int i = 0; i < firstMonthDay - 1; i++) {
                lastMonthDays.add(lastMonthAllDays.get(length - 1 - i));
            }
        }
        calendar.add(Calendar.MONTH, 1);
        Collections.reverse(lastMonthDays);
        return lastMonthDays;
    }

    /**
     * 获取第一天为周几 周一到周日 分别对应1-7
     *
     * @param calendar
     * @return
     */
    public static int getFirstMonthDay(Calendar calendar) {
        // TODO Auto-generated method stub
        Calendar tempCalendar = (Calendar) calendar.clone();
        tempCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return tempCalendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 根据给定的Calendar，计算当前日历的天数
     *
     * @param calendar
     * @return
     */
    public static List<String> getDaysFromCalendar(Calendar calendar) {
        List<String> daysFromCalendar = new ArrayList<String>();

        int daysNum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= daysNum; i++) {
            daysFromCalendar.add(i + "");
        }
        return daysFromCalendar;
    }

    /**
     * 获取下个月的几天日期
     *
     * @param calendar
     */
    public static List<String> getNextMonthDays(Calendar calendar) {
        List<String> nextMonthDays = new ArrayList<String>();

        System.out.println(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        System.out.println(calendar.getTime());
        // System.out.println("NextMonth:::::"+calendar.get(Calendar.MONTH));
        // 计算下一个月第一天为周几
        int nextMonthFirstDay = getFirstMonthDay(calendar);
        // System.out.println("nextMonthFirstDay:::::"+nextMonthFirstDay);
        calendar.add(Calendar.MONTH, -1);
        int nextNum = 1;
        if (nextMonthFirstDay != Calendar.SUNDAY) {// 锟斤拷一锟斤拷锟铰诧拷锟角达拷锟斤拷锟秸匡拷始锟斤拷锟斤拷锟斤拷锟铰革拷锟铰碉拷锟斤拷锟斤拷
            for (int i = 0; i < 7 - nextMonthFirstDay + 1; i++) {
                nextMonthDays.add(nextNum + "");
                nextNum++;
            }
        }
        return nextMonthDays;
    }

    /**
     * 获取总共的天数
     *
     * @param calendar
     * @return
     */
    public static List<String> getAllDays(Calendar calendar) {

        List<String> allDays = getLastMonthDays(calendar);
        allDays.addAll(getDaysFromCalendar(calendar));
        allDays.addAll(getNextMonthDays(calendar));
        return allDays;

    }

    public static void getCurrentDays(Calendar calendar, List<String> weekDays,
                                      List<String> weekCalendars) {
        // 清空
        weekDays.clear();
        weekCalendars.clear();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // int currentMonthDays =
        // calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        switch (dayOfWeek) {
            case Calendar.SUNDAY:// 星期日0
                getWeekDays(calendar, weekDays, day, 0);
                getWeekCalendar(calendar, weekCalendars, 0);
                break;
            case Calendar.MONDAY:// 星期一1
                getWeekDays(calendar, weekDays, day, 1);
                getWeekCalendar(calendar, weekCalendars, 1);
                break;
            case Calendar.TUESDAY:// 星期二2
                getWeekDays(calendar, weekDays, day, 2);
                getWeekCalendar(calendar, weekCalendars, 2);
                break;
            case Calendar.WEDNESDAY:// 星期三3
                getWeekDays(calendar, weekDays, day, 3);
                getWeekCalendar(calendar, weekCalendars, 3);
                break;
            case Calendar.THURSDAY:// 星期四4
                getWeekDays(calendar, weekDays, day, 4);
                getWeekCalendar(calendar, weekCalendars, 4);
                break;
            case Calendar.FRIDAY:// 星期五5
                getWeekDays(calendar, weekDays, day, 5);
                getWeekCalendar(calendar, weekCalendars, 5);
                break;
            case Calendar.SATURDAY:// 星期六6
                getWeekDays(calendar, weekDays, day, 6);
                getWeekCalendar(calendar, weekCalendars, 6);
                break;

            default:
                break;
        }
    }

    private static void getWeekDays(Calendar calendar,
                                    List<String> currentWeekDays, int day, int dayOfWeek) {
        currentWeekDays.clear();
        for (int i = 0; i < dayOfWeek; i++) {
            calendar.add(Calendar.DATE, -dayOfWeek + i);
            int temp = calendar.get(Calendar.DATE);
            currentWeekDays.add("" + temp);
            calendar.add(Calendar.DATE, dayOfWeek - i);
        }
        currentWeekDays.add("" + day);
        for (int i = 1; i <= 6 - dayOfWeek; i++) {
            calendar.add(Calendar.DATE, i);
            int temp = calendar.get(Calendar.DATE);
            currentWeekDays.add("" + temp);
            calendar.add(Calendar.DATE, -i);
        }
        // List<Calendar> calendarList = getWeekCalendar(calendar,dayOfWeek);
        // for (int i = 0; i < calendarList.size(); i++) {
        // currentWeekDays.add(calendarList.get(i).get(Calendar.DAY_OF_MONTH)+"");
        // }
    }

    // private static List<String> getWeekDaysFromCalendar(){}

    /**
     * @param calendar
     * @param dayOfWeek 0--6分别对应 日——六
     * @return
     */
    private static void getWeekCalendar(Calendar calendar,
                                        List<String> calendarList, int dayOfWeek) {

        calendarList.clear();
        for (int i = 0; i < dayOfWeek; i++) {
            calendar.add(Calendar.DATE, -dayOfWeek + i);
            // int temp = calendar.get(Calendar.DATE);
            calendarList.add(calendar.get(Calendar.YEAR) + "-"
                    + (calendar.get(Calendar.MONTH) + 1) + "-"
                    + calendar.get(Calendar.DATE));
            calendar.add(Calendar.DATE, dayOfWeek - i);
        }
        calendarList.add(calendar.get(Calendar.YEAR) + "-"
                + (calendar.get(Calendar.MONTH) + 1) + "-"
                + calendar.get(Calendar.DATE));
        for (int i = 1; i <= 6 - dayOfWeek; i++) {
            calendar.add(Calendar.DATE, i);
            // int temp = calendar.get(Calendar.DATE);
            calendarList.add(calendar.get(Calendar.YEAR) + "-"
                    + (calendar.get(Calendar.MONTH) + 1) + "-"
                    + calendar.get(Calendar.DATE));
            calendar.add(Calendar.DATE, -i);
        }
    }

    public static Calendar getSelectCalendar(int mPageNumber) {
        Calendar calendar = Calendar.getInstance();
        if (mPageNumber > 1000) {
            calendar.add(Calendar.DATE, 7 * (mPageNumber - 1000));
        } else if (mPageNumber < 1000) {
            calendar.add(Calendar.DATE, 7 * (mPageNumber - 1000));
        } else {

        }
        return calendar;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param starDate 开始日期
     * @param endDate  结束日期
     * @return
     */
    public static int getGapCount(String starDate, String endDate) {
        long to = 0;
        long from = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            to = df.parse(endDate).getTime();
            from = df.parse(starDate).getTime();
        } catch (Exception e) {
            // TODO: handle exception
        }
        int day = (int) ((to - from) / (1000 * 60 * 60 * 24));
        LogUtil.e("比对天数", day + "");
        return day;
    }

    /**
     * 获取两个日期之间的天数
     * @param starDate
     * @param endDate
     * @param pattern
     * @return
     */
    public static int getGapCount(String starDate,String endDate, String pattern)
    {
        long to = 0;
        long from = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            to = df.parse(endDate).getTime();
            from = df.parse(starDate).getTime();

        } catch (Exception e) {
            // TODO: handle exception
        }

        return (int) ((to - from) / (1000 * 60 * 60 * 24));
    }

    public static float getGapHour(String starDate, String endDate) {
        long to = 0;
        long from = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            to = df.parse(endDate).getTime();
            from = df.parse(starDate).getTime();

        } catch (Exception e) {
            // TODO: handle exception
        }

        return (float) ((to - from) / (1000 * 60 * 60));
    }

    /**
     * 获取两个时间段的分钟差
     *
     * @param startDate 年月日时分秒
     * @param endDate
     * @return
     */
    public static int getGapMinutes(String startDate, String endDate) {
        long start = 0;
        long end = 0;
        LogUtil.e("开始结束时间", startDate + "  " + endDate);
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            start = df.parse(startDate).getTime();
            end = df.parse(endDate).getTime();
        } catch (Exception e) {
            // TODO: handle exception
        }
//        LogUtil.e("开始结束时间1", (end - start) + "");
        int minutes = (int) ((end - start) / (1000 * 60));
        LogUtil.e("开始结束时间", minutes + "");
        return minutes;
    }

    /**
     * @param startCalendar
     * @param endCalendar
     * @return
     */
    public static int getGapMinutes(Calendar startCalendar, Calendar endCalendar) {
        long start = 0;
        long end = 0;
        try {
            start = startCalendar.getTimeInMillis();
            end = endCalendar.getTimeInMillis();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return (int) ((end - start) / (1000 * 60));
    }


    /**
     * 获取两个时间段的分钟差
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getGap(String startDate, String endDate) {
        long start = 0;
        long end = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            start = df.parse(startDate).getTime();
            end = df.parse(endDate).getTime();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return (int) ((end - start) / (1000 * 60));
    }

    /**
     * 获取两个时间段的分钟差
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getGap(String startDate, String endDate, String pattern) {
        long start = 0;
        long end = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            start = df.parse(startDate).getTime();
            end = df.parse(endDate).getTime();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return (int) ((end - start) / (1000 * 60));
    }

    /**
     * 将long类型的时间转成String类型的日期
     *
     * @param time
     * @param pattern
     * @return
     */
    public static String long2String(long time, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date(time));
    }


    /**
     * 将Calendar转换为2014-1-1模式的字符串
     *
     * @param calendar
     * @return
     */
    public static String calendarToString(Calendar calendar) {
        return calendar.get(Calendar.YEAR) + "-"
                + (calendar.get(Calendar.MONTH) + 1) + "-"
                + calendar.get(Calendar.DATE);
    }

    /**
     * 将Calendar转换为2014-01-01模式的字符串
     *
     * @param calendar
     * @return
     */
    public static String calendarToString2(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        if (month < 10) {
            if (day < 10) {
                return year + "-" + "0" + month + "-" + "0" + day;
            } else {
                return year + "-" + "0" + month + "-" + day;
            }
        } else {
            if (day < 10) {
                return year + "-" + month + "-" + "0" + day;
            } else {
                return year + "-" + month + "-" + day;
            }

        }
    }

    /**
     * 将Calendar按照一定的规则转换为字符串
     *
     * @param calendar //Calendar
     * @param pattern  //规则
     * @return
     */
    public static String calendar2string(Calendar calendar, String pattern) {
        // Calendar calendar = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(pattern);// 设置你想要的格式
        String dateStr = df.format(calendar.getTime());
        return dateStr;
    }

    /**
     * 将日期字符串转换成Calendar
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Calendar string2calendar(String dateStr, String pattern) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date time = null;
        try {
            time = format.parse(dateStr);
            calendar.setTime(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }

    /**
     * 将字符串转换为时间戳
     *
     * @param datestr 时间Str
     * @param pattern 规则
     * @return
     */
    public static String string2timestamp(String datestr, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date date;
        String result = null;
        try {
            date = df.parse(datestr);
            long l = date.getTime();
            String str = String.valueOf(l);
            result = str.substring(0, 13);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("时间戳：：" + result);
        return result;
    }

    /**
     * 格式化日期
     *
     * @param datestring
     * @param oldpattern
     * @param targetpattern
     * @return
     */
    public static String formatDate(String datestring, String oldpattern,
                                    String targetpattern) {
        SimpleDateFormat oldFormat = new SimpleDateFormat(oldpattern);
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat(targetpattern);
        try {
            date = oldFormat.parse(datestring);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return format.format(date);
    }

    /**
     * 将字符串转换成calendar
     *
     * @param str
     * @param pattern
     * @return
     */
    public static Calendar string2Calendar(String str, String pattern) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        Date date;
        try {
            date = sdf.parse(str);
            calendar.setTime(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return calendar;
    }

    /**
     * 获取患病的日期 例如"2年10天"
     * @param startDate yyyy-MM-dd HH:mm:ss
     * @param endDate yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getYearDay(String startDate, String endDate, String pattern){
        int reYear = 0;
        int reDays = 0;
        String reStr = "";
        Calendar start = string2calendar(startDate, pattern);
        Calendar end = string2calendar(endDate, pattern);
        //计算年
        int year = end.get(Calendar.YEAR) - start.get(Calendar.YEAR);
        if (year == 0){//同一年
            reYear = 0;
        }else {//不同年
            int gapMonth = end.get(Calendar.MONTH) - start.get(Calendar.MONTH);
            if (gapMonth == 0 ){//同月
                int gapDay = end.get(Calendar.DATE) - start.get(Calendar.DATE);
                if (gapDay < 0){//不到整年
                    reYear = year - 1;
                }else {//整年
                    reYear = year;
                }
            }else if (gapMonth < 0){//还不到整年 减去一年
                reYear = year - 1;
            }else {//>0
                reYear = year;
            }
        }
        //计算天
        start.add(Calendar.YEAR, reYear);
        reDays = getGapCount(calendar2string(start, pattern), calendar2string(end, pattern), pattern);
        if (reYear == 0){
            if (reDays == 0){
                reStr = 1 + "天";
            }else {
                reStr = reDays + "天";
            }
        }else {
            if (reDays == 0){
                reStr = reYear + "年";
            }else{
                reStr = reYear + "年" + reDays + "天";
            }
        }

        return  reStr;
    }
}
