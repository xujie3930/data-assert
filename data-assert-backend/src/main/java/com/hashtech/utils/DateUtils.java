package com.hashtech.utils;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

/**
 * @author
 */
public final class DateUtils {

    protected final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT);

    private DateUtils() {
        //
    }

    /**
     * 当前日期转为String
     *
     * @param date    日期
     * @param pattern 格式
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * 获得日期相差天数
     *
     * @param date1
     * @param date2
     * @return 相差天数
     */
    public static int getDateDifferent(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return -1;
        }
        long intervalMilli = date1.getTime() - date2.getTime();
        return (int) (intervalMilli / (24 * 60 * 60 * 1000));
    }

    /**
     * 获得往后推迟amount天的时间 如果填入负数，则往前推
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addDate(Date date, int amount) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, amount);
        date = calendar.getTime();
        return date;
    }

    public static Date addMonth(Date date, int monthCnt) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, monthCnt);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获得往后推迟amount的时间
     *
     * @param date
     * @param amount 分钟数
     * @return
     */
    public static Date addDateWithMin(Date date, int amount) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, amount);
        date = calendar.getTime();
        return date;
    }

    /**
     * 以入参构造一个新的Date，时、分、秒、毫秒均设置为0
     *
     * @param date
     * @return
     */
    public static Date getFirstSecondOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getLastSecondOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * @return
     * @author king
     * @create 2008-1-21 下午02:16:21
     */
    public static Timestamp getNow() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 使用指定的格式将字符串解析成日期
     *
     * @param dateString
     * @param format
     * @return
     * @modify Dec 26, 2006 11:06:48 AM
     */
    public static Date parseDate(String dateString, String format) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }
        try {
            return new SimpleDateFormat(format).parse(dateString);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将字符串解析成日期
     *
     * @param dateString
     * @return
     * @modify Dec 26, 2006 11:06:27 AM
     */
    public static Date parseDate(String dateString) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }
        try {
            if (dateString.length() <= 10) {
                return dateFormat.parse(dateString);
            } else {
                return dateTimeFormat.parse(dateString);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 解析日期时间
     *
     * @param dateString
     * @return
     * @modify Dec 28, 2006 9:31:26 AM
     */
    public static Timestamp parseDatetime(String dateString) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }
        Date date = null;
        try {
            date = dateTimeFormat.parse(dateString);
        } catch (Exception e) {
            date = parseDate(dateString);
        }
        return date == null ? null : new Timestamp(date.getTime());
    }

    public static int get(Date date, Field field) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Field.toInt(field));
    }

    /**
     * 获取日期中的年份
     *
     * @param date
     * @return
     * @modify Dec 26, 2006 11:05:34 AM
     */
    public static int getYear(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date is null!");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取日期所在的季度
     *
     * @param date
     * @return
     * @modify Dec 29, 2006 11:13:26 AM
     */
    public static Season getSeason(Date date) {
        Month m = getMonth(date);
        int mi = Month.toInt(m);
        return Season.valueOf(mi / 3);
    }

    /**
     * 获取日期中的月份
     *
     * @param date
     * @return
     * @modify Dec 26, 2006 11:05:50 AM
     */
    public static Month getMonth(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date is null!");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return Month.valueOf(cal.get(Calendar.MONTH));
    }

    /**
     * 获取日期中的天 从1开始
     *
     * @param date
     * @return
     * @modify Dec 26, 2006 11:06:04 AM
     */
    public static int getDay(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date is null!");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DATE);
    }

    /**
     * 计算获取每年的第几天
     *
     * @param year
     * @param days 1表示年的第一天
     * @return 那一天的日期类型
     * @modify Dec 25, 2006 2:40:39 PM
     */
    public static Date getDate(int year, int days) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, 0);
        cal.add(Calendar.DATE, days);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 根据年月日获取日期
     *
     * @param year
     * @param month 0表示一月
     * @param day   1表示月的第一天
     * @return
     * @modify Dec 26, 2006 1:04:12 PM
     */
    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, day);
        return getDate(cal.getTime());
    }

    /**
     * 同上
     *
     * @param year
     * @param month
     * @param day
     * @return
     * @modify Dec 29, 2006 10:17:36 AM
     */
    public static Date getDate(int year, Month month, int day) {
        return getDate(year, Month.toInt(month), day);
    }

    /**
     * 取得季度的的一个月
     *
     * @param season
     * @return
     * @modify Dec 29, 2006 10:27:28 AM
     */
    public static Month getFirstMonthOfSeason(Season season) {
        return Month.valueOf(Season.toInt(season) * 3);
    }

    /**
     * 获取那个季度的第几天
     *
     * @param year
     * @param season
     * @param days
     * @return
     * @modify Dec 29, 2006 10:24:39 AM
     */
    public static Date getDate(final int year, final Season season, final int days) {
        Month firstMonth = getFirstMonthOfSeason(season);
        Date firstDate = getDate(year, firstMonth, 1);
        Date date = add(firstDate, Field.DATE, days - 1);
        int y = getYear(date);
        Season s = getSeason(date);
        if (y != year || s != season) {
            throw new IllegalArgumentException("day is too large!");
        }
        return date;
    }

    /**
     * 获取日期部分
     *
     * @param date
     * @return
     * @modify Dec 29, 2006 9:18:08 AM
     */
    public static Date getDate(final Date date) {
        return setFields(date, new Field[]{Field.HOUR, Field.MINUTE,
                Field.SECOND, Field.MILLISECOND}, new int[]{0, 0, 0, 0});
    }

    /**
     * 根据给定值获取日期时间
     *
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param min
     * @param seconds
     * @return
     * @modify Dec 29, 2006 11:13:02 AM
     */
    public static Date getDate(int year, int month, int day, int hour, int min, int seconds) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, seconds);
        return cal.getTime();
    }

    /**
     * 计算年度中一月的天数 0:是一月
     *
     * @param year
     * @param month
     * @return
     * @modify Dec 26, 2006 11:03:46 AM
     */
    public static int getMonthDays(int year, Month month) {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.YEAR, year);
        cal1.set(Calendar.MONTH, Month.toInt(month));
        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.YEAR, year);
        cal2.set(Calendar.MONTH, Month.toInt(month) + 1);
        long time = cal2.getTimeInMillis() - cal1.getTimeInMillis();
        time = time / (24 * 3600 * 1000);
        return (int) time;
    }

    /**
     * 一个星期的第几天 1:周日，2：周一
     *
     * @param date
     * @return
     * @modify Dec 26, 2006 1:01:08 PM
     */
    public static Weekday getWeekDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return Weekday.valueOf(cal.get(Calendar.DAY_OF_WEEK));
    }

    /**
     * 获取一个月第几周的所有日期
     *
     * @param year
     * @param month
     * @param index 从0开始计数
     * @return
     * @modify Jan 4, 2007 10:01:10 PM
     */
    public static Date[] getWeekDays(int year, Month month, int index) {
        Date[] result = new Date[7];
        Date date = getDate(year, month, index * 7);
        setFields(date, new Field[]{Field.HOUR, Field.MINUTE, Field.SECOND}, new int[]{0, 0, 0});
        date = add(date, Field.DATE, Weekday.toInt(getWeekDay(date)));
        for (int i = 0; i < 7; i++) {
            result[i] = add(date, Field.DATE, i);
        }
        return result;
    }

    /**
     * 找到最近的一个周天,这一天与给定的天在同一周
     *
     * @param date
     * @param day  0～6，分别取周日到周六
     * @return
     * @modify Dec 28, 2006 5:30:30 PM
     */
    public static Date getNearestWeekDay(Date date, Weekday week) {
        Weekday weekday = getWeekDay(date);
        return add(date, Field.DATE, Weekday.toInt(week) - Weekday.toInt(weekday));
    }

    /**
     * 获取当月的第几天
     *
     * @param date
     * @param day
     * @return
     * @modify Dec 28, 2006 11:08:50 PM
     */
    public static Date getNearestMonthDay(Date date, int day) {
        if (date == null) {
            throw new IllegalArgumentException("Date is null!");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        return getDate(year, month, day);
    }

    /**
     * 比较两个日期的大小
     *
     * @param date1
     * @param date2
     * @return
     * @modify Nov 7, 2006 10:37:49 PM
     */
    public static int compareTo(Date date1, Date date2) {
        if (date1 == null && date2 == null) {
            return 0;
        }
        if (date1 == null && date2 != null) {
            return -1;
        }
        if (date1 != null && date2 == null) {
            return 1;
        }
        return (int) (date1.compareTo(date2));
    }

    /**
     * 判断指定日期是否在指定范围内
     *
     * @param input
     * @param from
     * @param to
     * @return
     * @modify Dec 28, 2006 3:12:21 PM
     */
    public static boolean between(Date input, Date from, Date to) {
        return compareTo(input, from) >= 0 && compareTo(input, to) <= 0;
    }

    /**
     * 使用缺省的格式格式化日期
     *
     * @param date
     * @return
     * @modify Dec 25, 2006 2:41:25 PM
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        if (date instanceof java.sql.Date) {
            return dateFormat.format(date);
        } else {
            return dateTimeFormat.format(date);
        }
    }

    /**
     * 使用指定的格式将日期格式化
     *
     * @param date
     * @param format
     * @return
     * @modify Dec 26, 2006 11:07:16 AM
     */
    public static String formatDate(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 向日期增加若干部分
     *
     * @param date
     * @param type
     * @param amount
     * @return
     * @modify Dec 28, 2006 4:00:42 PM
     */
    public static Date add(final Date date, final Field field, final int amount) {
        if (date == null) {
            throw new IllegalArgumentException("Date is null!");
        }
        Field type = field;
        if (type == null) {
            type = Field.DATE;
        }
        //
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int calType = Field.toInt(field);
        if (calType != -1) {
            cal.add(calType, amount);
        }
        return cal.getTime();
    }

    /**
     * 设置一个字段的值
     *
     * @param date
     * @param field
     * @param amount
     * @return
     * @modify Dec 28, 2006 5:14:00 PM
     */
    public static Date setField(final Date date, final Field field, final int amount) {
        if (date == null) {
            throw new IllegalArgumentException("Date is null!");
        }
        //
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int calType = Field.toInt(field);
        if (calType != -1) {
            cal.set(calType, amount);
        }
        return cal.getTime();
    }

    public static Date setFields(final Date date, final Field[] fields, final int[] amounts) {
        if (date == null) {
            throw new IllegalArgumentException("Date is null!");
        }
        //
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        for (int i = 0; i < fields.length; i++) {
            int calType = Field.toInt(fields[i]);
            if (calType != -1) {
                cal.set(calType, amounts[i]);
            }
        }
        return cal.getTime();
    }

    public static String toGMT(Date date) {
        Calendar cal = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
        format.setCalendar(cal);
        return format.format(date);
    }

    public static String longToString(Long lt) {
        Date date = new Date(lt);
        return dateFormat.format(date);
    }

    /**
     * 时间段类型
     *
     * @author
     * @create Dec 28, 2006 3:59:27 PM
     */
    public static enum Field {
        YEAR, MONTH, DATE, HOUR, MINUTE, SECOND, MILLISECOND;

        public static int toInt(final Field type) {
            int calType = -1;
            if (type == Field.YEAR) {
                calType = Calendar.YEAR;
            } else if (type == Field.MONTH) {
                calType = Calendar.MONTH;
            } else if (type == Field.DATE) {
                calType = Calendar.DATE;
            } else if (type == Field.HOUR) {
                calType = Calendar.HOUR_OF_DAY;
            } else if (type == Field.MINUTE) {
                calType = Calendar.MINUTE;
            } else if (type == Field.SECOND) {
                calType = Calendar.SECOND;
            } else if (type == Field.MILLISECOND) {
                calType = Calendar.MILLISECOND;
            }
            return calType;
        }
    }

    /**
     * 季度枚举
     *
     * @author
     * @create Dec 29, 2006 9:24:04 AM
     */
    public static enum Season {
        SPRING, SUMMER, AUTUMN, WINTER;

        public static Season valueOf(final int i) {
            if (i >= 0 && i <= 3) {
                return Season.values()[i];
            } else {
                return null;
            }
        }

        public static int toInt(final Season season) {
            Season[] seasons = Season.values();
            for (int i = 0; i < seasons.length; i++) {
                if (seasons[i] == season) {
                    return i;
                }
            }
            return -1;
        }
    }

    /**
     * 枚举月 0:一月; 11：12月
     *
     * @author
     * @create Dec 29, 2006 9:28:06 AM
     */
    public static enum Month {
        JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, OCT, NOV, DEC;

        /**
         * 取值0～11
         *
         * @param i
         * @return
         * @modify Dec 29, 2006 11:09:33 AM
         */
        public static Month valueOf(final int i) {
            if (i >= 0 && i <= 11) {
                return Month.values()[i];
            } else {
                return null;
            }
        }

        /**
         * 返回值0～11
         *
         * @param month
         * @return
         * @modify Dec 29, 2006 11:09:16 AM
         */
        public static int toInt(final Month month) {
            Month[] months = Month.values();
            for (int i = 0; i < months.length; i++) {
                if (months[i] == month) {
                    return i;
                }
            }
            return -1;
        }
    }

    /**
     * 枚举周 1:周日， 7：周六
     *
     * @author
     * @create Dec 29, 2006 9:29:07 AM
     */
    public static enum Weekday {
        SUN, MON, TUE, WEN, THR, FRI, SAT;

        /**
         * 从1开始获取
         *
         * @param i
         * @return
         * @modify Dec 29, 2006 10:05:20 AM
         */
        public static Weekday valueOf(final int i) {
            if (i >= 1 && i <= 7) {
                return Weekday.values()[i - 1];
            } else {
                return null;
            }
        }

        public static int toInt(final Weekday week) {
            Weekday[] weeks = Weekday.values();
            for (int i = 0; i < weeks.length; i++) {
                if (weeks[i] == week) {
                    return i;
                }
            }
            return -1;
        }
    }
}
