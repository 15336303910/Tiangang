package cn.plou.web.common.utils.a1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期
 * 
 */
public final class DateUtil {

    public static final String DATE_FORMAT_WITHOUT_DAY = "yyyy-MM";

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String BANK_DEFAULT_DATE_FORMAT = "yyyy/MM/dd";

    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_DATETIME_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";

    public static final String DEFAULT_DATETIME_HOUR_FORMAT = "yyyy-MM-dd HH";

    public static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";

    public static final String TIMESTAMP_TIME_FORMAT = "yyyyMMddHHmmssSSS";

    public static final String DEFAULT_DATE_FORMAT1 = "yyyyMMdd";

    public static final String DEFAULT_TIME_FORMAT1 = "HHmmss";
    public static final String DEFAULT_TIME_FORMAT2 = "ddHHmmss";

    public static Date getMin() {
	// 1970/01/01 00:00:00,000 JST
	return new Date(0L);
    }

    public static Date getMax() {
	// 2100/01/01 00:00:00,000 JST
	return new Date(4102412400000L);
    }

    public static Date getTimeMax() {
	// 2100/01/01 01:00:00,000 JST
	return new Date(4102416000000L);
    }

    private DateUtil() {
    }

    public static Date immutableCopy(Date date) {
	Date result = null;
	if (date != null) {
	    result = new Date(date.getTime());
	}
	return result;
    }

    public static Date getSysdate1() {
	String dateString = getSysdateString();
	return fromString(dateString);
    }

    public static String toDateString(Date date) {
	String result;
	if (date == null) {
	    result = "";
	} else {
	    result = toDateTimeString(date, DEFAULT_DATE_FORMAT);
	}
	return result;
    }

    public static String toDateMonthString(Date date) {
	String result;
	if (date == null) {
	    result = "";
	} else {
	    result = toDateTimeString(date, DATE_FORMAT_WITHOUT_DAY);
	}
	return result;
    }

    public static String toDateString(Long offset) {
	return toDateTimeString(offset, DEFAULT_DATE_FORMAT);
    }

    public static String toDateTimeString(Date date) {
	String result;
	if (date == null) {
	    result = "";
	} else {
	    result = toDateTimeString(date, DEFAULT_DATETIME_FORMAT);
	}
	return result;
    }

    public static String toDateTimeMinuteString(Date date) {
	String result;
	if (date == null) {
	    result = "";
	} else {
	    result = toDateTimeString(date, DEFAULT_DATETIME_MINUTE_FORMAT);
	}
	return result;
    }

    public static String toDateTimeString(Long offset) {
	return toDateTimeString(offset, DEFAULT_DATETIME_FORMAT);
    }

    public static String toDateHourString(Long offset) {
	return toDateTimeString(offset, DEFAULT_DATETIME_HOUR_FORMAT);
    }

    public static String toDateTimeString(Long offset, String format) {
	return (new SimpleDateFormat(format)).format(new Date(offset));
    }

    public static String toDateTimeString(Date d, String format) {
	return (new SimpleDateFormat(format)).format(d);
    }

    public static long toLong(String dateString) {
	Date date = fromString(dateString, true);
	if (date == null) {
	    return 0;
	}
	Calendar cal1 = Calendar.getInstance();
	cal1.setTime(date);
	return cal1.getTimeInMillis();
    }

    public static Date fromString(String dateString) {
	Date result;

	if (StringUtil.isNullOrEmpty(dateString)) {
	    result = null;
	} else {
	    result = fromString(dateString, false);
	}
	return result;
    }

    public static Date fromString(String dateString, boolean isDateTime) {
	String formatString = DEFAULT_DATE_FORMAT;
	if (isDateTime) {
	    formatString = DEFAULT_DATETIME_FORMAT;
	}
	return parseString(dateString, formatString);
    }

    public static Date parseString(String dateString, String formatString) {
	Date result = null;
	DateFormat pattern = new SimpleDateFormat(formatString);
	try {
	    pattern.setLenient(false);
	    result = pattern.parse(dateString);
	} catch (ParseException pe) {
	    result = null;
	} catch (NullPointerException npe) {
	    result = null;
	}
	return result;
    }

    public static Date fromYearMonth(String year, String month) {
	String firstDayString = year + "-" + month + "-" + "01";

	return fromString(firstDayString);
    }

    public static Date fromYear(String year) {
	String firstDayString = year + "-" + "01" + "-" + "01";

	return fromString(firstDayString);
    }

    public static Date getSysdate() {
	return new Date((System.currentTimeMillis() / 1000) * 1000);
    }

    public static String getSysdateString() {
	String sysdate = toDateString(getSysdate());
	return sysdate;
    }

    public static String getSysdateTimeString() {
	String sysdate = toDateTimeString(getSysdate());
	return sysdate;
    }

    public static String getTimeStamp() {
	return toDateTimeString(getSysdate(), TIMESTAMP_FORMAT);
    }

    public static String getDayTime() {
	return toDateTimeString(getSysdate(), DEFAULT_TIME_FORMAT2);
    }

    public static boolean isCorrect(String dateString) {
	return (fromString(dateString) != null);
    }

    public static String getYYYY(Date date) {
	DateFormat pattern = new SimpleDateFormat("yyyy");
	return pattern.format(date);
    }

    public static String getYYYYMM() {
	DateFormat pattern = new SimpleDateFormat("yyyyMM");
	return pattern.format(getSysdate());
    }

    public static String getMM(Date date) {
	DateFormat pattern = new SimpleDateFormat("MM");
	return pattern.format(date);
    }

    public static String getDD(Date date) {
	DateFormat pattern = new SimpleDateFormat("dd");
	return pattern.format(date);
    }

    public static String getHH(Date date) {
	DateFormat pattern = new SimpleDateFormat("HH");
	return pattern.format(date);
    }

    public static boolean isValidMonth(String month) {
	boolean result = false;

	if (StringUtil.isNullOrEmpty(month)) {
	    return false;
	}

	try {
	    int monthInt = Integer.parseInt(month);

	    result = monthInt >= 1 && monthInt <= 12;
	} catch (NumberFormatException e) {
	    result = false;
	}
	return result;
    }

    public static String getEndDay(Date date) {
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
	format.format(date);
	Calendar c = format.getCalendar();

	return Integer.toString(c.getActualMaximum(Calendar.DATE));
    }

    public static String getEndDay(int year, int month) {
	Calendar c = Calendar.getInstance();
	if (!isValidMonth("" + month)) {
	    return null;
	}

	c.set(year, month - 1, 1);

	return Integer.toString(c.getActualMaximum(Calendar.DATE));
    }

    public static Date addMonth(Date date, int addition) {
	return addTime(date, Calendar.MONTH, addition);
    }

    public static Date addDate(Date date, int addition) {
	return addTime(date, Calendar.DATE, addition);
    }

    public static Date addHour(Date date, int addition) {
	return addTime(date, Calendar.HOUR_OF_DAY, addition);
    }

    public static Date addMinute(Date date, int addition) {
	return addTime(date, Calendar.MINUTE, addition);
    }

    private static Date addTime(Date date, int type, int addition) {
	SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
	format.format(date);
	Calendar c = format.getCalendar();
	c.add(type, addition);
	return c.getTime();
    }

    private static Date setTime(Date date, int field, int value) {
	Date result = null;
	if (date != null) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(field, value);
	    result = calendar.getTime();
	}
	return result;
    }

    public static Date setHour(Date date, int value) {
	return setTime(date, Calendar.HOUR_OF_DAY, value);
    }

    public static Date setMinute(Date date, int value) {
	return setTime(date, Calendar.MINUTE, value);
    }

    public static Date setSecond(Date date, int value) {
	return setTime(date, Calendar.SECOND, value);
    }

    public static boolean isPeriodString(String startDatetime, String endDatetime) {
	return isPeriodString(startDatetime, endDatetime, getSysdateString());
    }

    public static boolean isPeriodString(String startDatetime, String endDatetime,
	    String whenString) {
	if (StringUtil.isNullOrEmpty(startDatetime) && StringUtil.isNullOrEmpty(endDatetime)) {
	    return false;
	} else if (DateUtil.isCorrect(startDatetime) && StringUtil.isNullOrEmpty(endDatetime)) {
	    endDatetime = toDateString(getMax());
	} else if (StringUtil.isNullOrEmpty(startDatetime) && isCorrect(endDatetime)) {
	    startDatetime = toDateString(getMin());
	}

	return isPeriodDate(fromString(startDatetime), fromString(endDatetime),
		fromString(whenString));

    }

    public static boolean isPeriodDate(Date startDatetime, Date endDatetime) {
	return isPeriodDate(startDatetime, endDatetime, getSysdate());
    }

    public static boolean isPeriodDate(Date startDatetime, Date endDatetime, Date when) {
	boolean result = false;

	if (startDatetime == null) {
	    startDatetime = getMin();
	}
	if (endDatetime == null) {
	    endDatetime = getMax();
	}

	if (startDatetime.after(when) || endDatetime.before(when)) {
	    result = false;
	} else {
	    result = true;
	}
	return result;
    }

    public static Date truncateDate(Date targetDate) {
	return fromString(toDateString(targetDate));
    }

    public static Date truncateHour(Date targetDate) {
	if (targetDate == null) {
	    return null;
	}
	return parseString(toDateTimeString(targetDate, "yyyy-MM-dd HH"), "yyyy-MM-dd HH");
    }

    public static String iso8601Datetime(Date d) {
	return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+09:00'").format(d);
    }

    public static String iso8601Date(Date d) {
	return new SimpleDateFormat("yyyy-MM-dd").format(d);
    }

    public static String timeFormat(Date date, String strFormat) {
	String result;
	if (date == null) {
	    result = "";
	} else {
	    result = toDateTimeString(date, strFormat);
	}

	return result;
    }

    /**
     * 两个日期型相差月数
     * 
     * @param date1
     * @param date2
     * @return
     */
    public static int calculateMonth(Date date1, Date date2) {
	Calendar cal1 = Calendar.getInstance();
	cal1.setTime(date1);

	Calendar cal2 = Calendar.getInstance();
	cal2.setTime(date2);

	int month = (cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR)) * 12
		+ cal1.get(Calendar.MONTH) - cal2.get(Calendar.MONTH);
	return month;

    }

    public static int daysBetween(Date smdate, Date bdate) {
	try {
	    SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
	    smdate = sdf.parse(sdf.format(smdate));
	    bdate = sdf.parse(sdf.format(bdate));
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(smdate);
	    long time1 = cal.getTimeInMillis();
	    cal.setTime(bdate);
	    long time2 = cal.getTimeInMillis();
	    long between_days = (time2 - time1) / (1000 * 3600 * 24);

	    return Integer.parseInt(String.valueOf(between_days));
	} catch (Exception e) {

	    return 0;
	}
    }

    public static int hoursBetween(Date smdate, Date bdate) {
	try {
	    // SimpleDateFormat sdf = new
	    // SimpleDateFormat(DEFAULT_DATETIME_FORMAT);
	    // smdate = sdf.parse(sdf.format(smdate));
	    // bdate = sdf.parse(sdf.format(bdate));
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(smdate);
	    long time1 = cal.getTimeInMillis();
	    cal.setTime(bdate);
	    long time2 = cal.getTimeInMillis();
	    long between_hours = (time2 - time1) / (1000 * 3600);

	    return Integer.parseInt(String.valueOf(between_hours));
	} catch (Exception e) {

	    return 0;
	}
    }

}
