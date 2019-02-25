package com.barran.lib.utils.time;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author tanwei
 * 
 */
public class SelectTimeUtils {
    
    /** yyyy-MM-dd */
    public static final String FORMAT_KEY = "yyyy-MM-dd";
    /** MM月dd日 */
    public static final String FORMAT_MONTH_DAY = "MM月dd日";
    /** yyyy年M月d日 */
    public static final String FORMAT_YEAR_MONTH_DAY = "yyyy年M月d日";
    /** yy年MM月d日HH:mm */
    public static final String FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE = "yy年MM月d日HH:mm";
    
    private static Calendar cal = Calendar.getInstance();
    
    private static SimpleDateFormat ymdTitle;
    
    private static SimpleDateFormat ymd;
    
    private static SimpleDateFormat ymdhm;
    
    public static String formatDateForLog(final Date date) {
        if (ymdhm == null) {
            ymdhm = new SimpleDateFormat(FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE, Locale.CHINA);
        }
        
        return ymdhm.format(date);
    }
    
    public static String formatDateForLog(final long date) {
        return formatDateForLog(new Date(date));
    }
    
    /** 获取星期 */
    public static WeekDay getWeekDay(final Date date) {
        cal.clear();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK) - 2;
        if (week < 0) {
            week += 7;
        }
        return WeekDay.valueOf(week);
    }
    
    /** 将WeekDay对应的week转化为Calendar对应的week */
    public static int weekDayToCalendarWeek(int weekday) {
        int week = weekday + 2;
        if (week > 7) {
            week -= 7;
        }
        
        return week;
    }
    
    /** 将Calendar对应的week转化为WeekDay对应的week */
    public static int calendarWeekToWeekDay(int week) {
        
        int weekday = week - 2;
        if (weekday < 0) {
            weekday += 7;
        }
        return weekday;
    }
    
    /** 获取 天 */
    public static int getDayOfMonth(final Date date) {
        cal.clear();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    
    /** 获取日期所在星期的星期一 */
    public static Date getMondayOfWeek(final Date date) {
        cal.clear();
        cal.setTime(date);
        int week = cal.get(Calendar.DAY_OF_WEEK) - 2;
        if (week < 0) {
            week += 7;
        }
        if (week > 0) {
            
            cal.add(Calendar.DAY_OF_MONTH, 0 - week);
            return cal.getTime();
        }
        else {
            return date;
        }
    }
    
    /** 根据日期计算出星期的下标对应的日期 */
    public static Date getDateByIndex(final Date date, int index) {
        int week = SelectTimeUtils.getWeekDay(date).value();
        int delt = index - week;
        cal.clear();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, delt);
        return cal.getTime();
    }
    
    /** 获取指定日期之后的指定星期对应的日期（含指定日期） */
    public static Date getNextDateByIndex(Date date, int week) {
        Date nextDate = getDateByIndex(date, week);
        if (diffDaysOf2Dates(date, nextDate) == 1) {
            cal.clear();
            cal.setTime(nextDate);
            cal.add(Calendar.WEEK_OF_YEAR, 1);
            return cal.getTime();
        }
        else {
            return nextDate;
        }
    }
    
    /** 获取指定日期之后的指定星期对应的日期(不含指定的日期) */
    public static Date getNextDateByIndexExclusive(Date date, int week) {
        Date nextDate = getDateByIndex(date, week);
        if (diffDaysOf2Dates(date, nextDate) == -1) {
            return nextDate;
        }
        else {
            cal.clear();
            cal.setTime(nextDate);
            cal.add(Calendar.WEEK_OF_YEAR, 1);
            return cal.getTime();
        }
    }

    public static Date addDay(final Date date, int value) {
        cal.clear();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, value);
        return cal.getTime();
    }
    
    /** 展示日期 形如2015年6月1号 */
    public static String getTimeTitle(final Date date) {
        
        if (ymdTitle == null) {
            ymdTitle = new SimpleDateFormat(FORMAT_YEAR_MONTH_DAY, Locale.CHINA);
        }
        
        return ymdTitle.format(date);
    }
    
    /** yyyy-MM-dd 用作区分时间段的key值 */
    public static String getDateFormatYMD(final Date date) {
        if (ymd == null) {
            ymd = new SimpleDateFormat(FORMAT_KEY, Locale.CHINA);
        }
        
        return ymd.format(date);
    }
    
    /** 将格式化日期yyyy-MM-dd 解析为具体的Date */
    public static Date parseDateByFormatYMD(String dateString) {
        if (ymd == null) {
            ymd = new SimpleDateFormat(FORMAT_KEY, Locale.CHINA);
        }
        
        Date date = null;
        if (!TextUtils.isEmpty(dateString)) {
            
            try {
                return ymd.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        
        if (date == null) {
            date = new Date(System.currentTimeMillis());
        }
        
        return date;
    }
    
    /** 比较2个日期是否为同一天 */
    public static boolean isDateEquals(final Date date1, final long date2) {
        
        return diffDaysOf2Dates(date1, date2) == 0;
    }
    
    /** 比较2个日期是否为同一天 */
    public static boolean isDateEquals(final Date date1, final Date date2) {
        
        return diffDaysOf2Dates(date1, date2) == 0;
    }
    
    /** 比较2个日期相差的天数，1表示date1比date2晚一天以上，-1表示早一天以上, 0表示同一天 */
    public static int diffDaysOf2Dates(final Date date, long time) {
        
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(time);
        
        return diffDays(cal1, cal2);
    }
    
    /** 比较2个日期相差的天数，1表示date1比date2晚一天以上，-1表示早一天以上, 0表示同一天 */
    public static int diffDaysOf2Dates(final Date date1, final Date date2) {
        
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        
        return diffDays(cal1, cal2);
    }
    
    private static int diffDays(final Calendar cal1, final Calendar cal2) {
        
        int diff;
        
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 == year2) {
            int day1 = cal1.get(Calendar.DAY_OF_YEAR);
            int day2 = cal2.get(Calendar.DAY_OF_YEAR);
            if (day1 == day2) {
                diff = 0;
            }
            else if (day1 > day2) {
                diff = 1;
            }
            else {
                diff = -1;
            }
        }
        else if (year1 > year2) {
            diff = 1;
        }
        else {
            diff = -1;
        }
        
        return diff;
    }
    
    /** 判断指定时间是否在今年 */
    public static boolean isTimeInCurrentYear(Date date) {
        
        return isTimeInSameYear(System.currentTimeMillis(), date);
    }
    
    /** 判断指定时间是否在同一年 */
    public static boolean isTimeInSameYear(long currentTimeMills, Date date) {
        
        cal.clear();
        cal.setTime(date);
        
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeInMillis(currentTimeMills);
        return cal.get(Calendar.YEAR) == currentTime.get(Calendar.YEAR);
    }
}
