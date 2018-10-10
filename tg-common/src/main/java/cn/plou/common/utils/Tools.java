package cn.plou.common.utils;

import cn.plou.common.constant.CKey;
import cn.plou.common.constant.FuncKey;

import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import cn.plou.common.custom.exception.InvalidValueException;

/**
 * 
 *
 * @Author : WangJiWei
 * @Date : 2017年11月25日上午8:52:58
 *
 * @Comments :杂七杂八
 *
 */
public class Tools {

    private static final String ID_KEY = "10000";
    private static NumberFormat numf = new DecimalFormat(ID_KEY);
    private static Integer INCR_STEP = 1;// 递增梯度

    /**
     * 字符串处理
     */
    public static String strOpt(UnaryOperator<String> fun, String str) {
	return fun.apply(str);
    }

    /**
     * 字符判断
     */
    public static Boolean is(Predicate<String> fun, String str) {
	return fun.test(str);
    }

    /**
     * 字符编码转换
     */
    public static String changeCharset(String str, Charset oldCharset, Charset newCharset)
	    throws UnsupportedEncodingException {
	return isNull(str) ? null : new String(str.getBytes(oldCharset), newCharset);
    }

    public static String isoToUtf(String str) throws UnsupportedEncodingException {
	return changeCharset(str, Charsets.ISO_8859_1, Charsets.UTF_8);
    }

    /**
     * 时间转化函数
     */
    public static String timeFunc(UnaryOperator<String> timeFormat, String format) {
	return timeFormat.apply(format);
    }

    /**
     * 时间转化函数
     */
    public static Date timeFunc(BiFunction<String, String, Date> timeToDate, String timeStr,
	    String format) {
	return timeToDate.apply(timeStr, format);
    }

    public static String timeFunc(BiFunction<Date, String, String> dateFunc, Date time,
	    String format) {
	return dateFunc.apply(time, format);
    }

    /**
     * 字符是否为NULL或"",如果是空,返回true
     */
    public static Boolean isNull(String str) {
	return is(FuncKey.IS_BLANK, str);
    }

    /**
     * 字符是否相等
     */
    public static Boolean equals(String str1, String str2) {
	return StringUtils.equals(str1, str2);
    }

    /**
     * 集合是否有元素
     */
    public static <T> Boolean isNotEmpty(Collection<T> cons) {
	return cons != null && cons.size() != 0;
    }

    /**
     * <pre>
     * 返回当前时间
     * Format 默认:yyyy-MM-dd HH:mm:ss
     * </pre>
     */
    public static String curTime(String format) {
	return timeFunc(FuncKey.DATE_FORMAT, isNull(format) ? CKey.YMDHMS__ : format);
    }

    public static Date curDate(String format) {
	return timeStrToDate(Tools.curTime(format), format);
    }

    public static Date getDate() {
	return new Date();
    }

    /**
     * 返回当前时间戳
     */
    public static long currentTimeMillis() {
	return Instant.now().toEpochMilli();
    }

    /**
     * 时间字符串转日期
     */
    public static Date timeStrToDate(String timeStr, String timeStrFormat) {
	return isNull(timeStr) ? null
		: timeFunc(FuncKey.TIME_STR_TO_DATE, timeStr,
			timeStrFormat = isNull(timeStrFormat) ? CKey.YMD_ : timeStrFormat);
    }

    /**
     * 日期转字符串
     */
    public static String dateToTimeStr(Date time, String timeStrFormat) {
	return timeFunc(FuncKey.DATE_TO_TIME_STR, time,
		isNull(timeStrFormat) ? CKey.YMDHMS__ : timeStrFormat);
    }

    public static String timeTF(String dateTime, long millisecond) {
	Date t = timeStrToDate(dateTime, CKey.YMDHMS__);
	return DateFormatUtils.format(t.getTime() + millisecond, CKey.YMDHMS__);
    }

    /**
     * 当前时间字符串 <br>
     * yyyy-MM-dd HH:mm:ss
     */
    public static String currentTime() {
	return dateToTimeStr(getDate(), CKey.YMDHMS__);
    }

    /**
     * 日期字符串转指定格式字符串
     */
    public static String timeFormat(String dateTime, String oldFormat, String newFormat) {
	Date date = timeStrToDate(dateTime, oldFormat);
	return dateToTimeStr(date, newFormat);
    }

    public static String dateChange(Date now, int days) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(now);
	calendar.add(Calendar.DAY_OF_MONTH, days);
	return dateToTimeStr(calendar.getTime(), CKey.YMD000);
    }

    public static Date dateChange_(Date now, int days) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(now);
	calendar.add(Calendar.DAY_OF_MONTH, days);
	return calendar.getTime();
    }

    public static String dateChange(Date now, int days, String format) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(now);
	calendar.add(Calendar.DAY_OF_MONTH, days);
	return dateToTimeStr(calendar.getTime(), format);
    }

    /**
     * 
     */
    public static List<String> rangeDate(Date start, Date end) throws ParseException {
	List<String> list = Lists.newArrayList();
	list.add(dateToTimeStr(start, CKey.YMD_));
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(start);
	while (calendar.getTime().compareTo(end) != 0) {
	    calendar.add(Calendar.DAY_OF_MONTH, 1);
	    list.add(dateToTimeStr(calendar.getTime(), CKey.YMD_));
	}
	return list;
    }

    /**
     * 检查Obj是否为空
     */
    public static void checkNullOrEmpty(Object obj, String name) {
	if (obj == null) {
	    throw new InvalidValueException(name + " should not be null!");
	}
	if (isNull(obj.toString())) {
	    throw new InvalidValueException(name + " should not be empty!");
	}
    }

    public static String dateid() {
	return DateFormatUtils.format(getDate(), CKey.YMD);
    }

    public static long formatTimestamp(long source) {
	// source : Thu Apr 07 15:53:13 CST 2017
	// return : Thu Apr 07 15:53:10 CST 2017
	// 返回整10秒
	return source / (1000 * 10) * (1000 * 10);
    }

    /**
     * 转换整点日期
     */
    public static Date formatDate(Date d) {
	String dateToTimeStr = Tools.dateToTimeStr(d, CKey.YMD000);
	Date timeStrToDate = Tools.timeStrToDate(dateToTimeStr, CKey.YMD000);
	return timeStrToDate;
    }

    /**
     * 编号
     * 
     * Rule : YYYYMMDD+5位的id , 从1开始 -> 2017092700001 , 2017092700002
     */
    public static Long generateNewID() {
	return Long.parseLong(dateid() + numf.format(INCR_STEP));
    }

    /**
     * 获取本地IP地址
     * 
     * @return
     * @throws SocketException
     */
    public static String getLocalIP() throws SocketException {
	String ip = "UnknownHost";
	for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
		.hasMoreElements();) {
	    NetworkInterface intf = en.nextElement();
	    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
		    .hasMoreElements();) {
		InetAddress inetAddress = enumIpAddr.nextElement();
		if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()
			&& inetAddress.isSiteLocalAddress()) {
		    ip = inetAddress.getHostAddress();
		}
	    }
	}
	return ip;
    }

    /**
     * 获取进程ID
     * 
     * @return
     */
    public static String getProcessId() {
	return ManagementFactory.getRuntimeMXBean().getName();
    }

    public final static class JDBCUtils {
	/**
	 * 释放数据库连接
	 */
	public static void releaseResource(Connection con, PreparedStatement ps, ResultSet rs) {

	    if (null != rs) {
		try {
		    rs.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	    if (null != ps) {
		try {
		    ps.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	    if (null != con) {
		try {
		    con.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	    }
	}

	public static void releaseResource(Connection con, Statement ps, ResultSet rs) {

	    if (null != rs) {
		try {
		    rs.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	    if (null != ps) {
		try {
		    ps.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	    if (null != con) {
		try {
		    con.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	    }
	}

    }

    public final static class Utf8 {
	private static final Charset CHARSET = Charset.forName("UTF-8");

	public static byte[] encode(CharSequence string) {
	    try {
		ByteBuffer bytes = CHARSET.newEncoder().encode(CharBuffer.wrap(string));
		byte[] bytesCopy = new byte[bytes.limit()];
		System.arraycopy(bytes.array(), 0, bytesCopy, 0, bytes.limit());
		return bytesCopy;
	    } catch (CharacterCodingException e) {
		throw new IllegalArgumentException("Encoding failed", e);
	    }
	}

	public static String decode(byte[] bytes) {
	    try {
		return CHARSET.newDecoder().decode(ByteBuffer.wrap(bytes)).toString();
	    } catch (CharacterCodingException e) {
		throw new IllegalArgumentException("Decoding failed", e);
	    }
	}
    }

    public static String formatHour(String datetime) {
	return timeFormat(datetime, CKey.YMDHMS__, CKey.YMDH00);
    }

    /**
     * 两个日期之间的秒数
     */
    public static Long diffSecond(String sysReadTime, String sysReadTimeLast) {
	Date now = timeStrToDate(sysReadTime, CKey.YMDHMS__);
	Date last = timeStrToDate(sysReadTimeLast, CKey.YMDHMS__);
	return diffSecond(now, last);
    }

    public static Long diffSecond(Date now, Date last) {
	return (now.getTime() - last.getTime()) / 1000;
    }

    public static String underLineJoin(String... str) {
	return (str != null && str.length != 0) ? StringUtils.join(str, CKey.UNDERLINE) : null;
    }

    public static String TimeStampToDateStr(long ts) {
	Date d = new Date(ts);
	return dateToTimeStr(d, CKey.YMD000);
    }

    /**
     * 测试
     * 
     * @Author : WangJiwei
     * @Date : 2017年11月25日上午9:31:42
     * @Comments :
     * @param args
     *
     */
    public static void main(String[] args) throws Exception {

	System.out.println(TimeStampToDateStr(1534089600000L));
	System.out.println(underLineJoin("a", "c", "q"));

	System.out.println(isNull(""));
	System.out.println(isNull(null));
	System.out.println(isNull(" 1 "));
	System.out.println(isNull("      "));

	System.out.println(curTime(null));
	TimeUnit.SECONDS.sleep(2);
	System.out.println(curTime(null));
	System.out.println(timeStrToDate("2017-11-25 09:30:02", null));

	System.out.println(timeFormat("2017-11-25 09:30:02", CKey.YMDHMS__, CKey.YMD000));
	// System.out.println(EncodeEnum.ISO_8859_1.getName());

	System.out.println(getLocalIP());
	System.out.println(getProcessId());

	String[] arr = { "a", "b", "c" };
	System.out.println(StringUtils.join(arr, ","));

    }

    // 通过反射赋值
    public static <T> void modelTrim(T model, T modelArm) {
	Class<T> clazz = (Class<T>) model.getClass();
	// 获取所有的bean中所有的成员变量
	Field[] fields = clazz.getDeclaredFields();
	for (int j = 0; j < fields.length; j++) {
	    // 获取所有的bean中变量类型为String的变量
	    if ("String".equals(fields[j].getType().getSimpleName())) {
		try {
		    // 获取get方法名
		    String methodName = "get" + fields[j].getName().substring(0, 1).toUpperCase()
			    + fields[j].getName().replaceFirst("\\w", "");
		    Method getMethod = clazz.getDeclaredMethod(methodName);
		    // 打破封装
		    getMethod.setAccessible(true);
		    // 得到该方法的值
		    Object methodValue = getMethod.invoke(model);
		    // 判断值是否为空或者为null,非的话这过滤前后空格
		    if (methodValue != null && !"".equals(methodValue)) {
			// 获取set方法名
			String setMethodName = "set"
				+ fields[j].getName().substring(0, 1).toUpperCase()
				+ fields[j].getName().replaceFirst("\\w", "");
			// 得到get方法的Method对象,带参数
			Class<T> clazz2 = (Class<T>) modelArm.getClass();
			Method setMethod = clazz2.getDeclaredMethod(setMethodName,
				fields[j].getType());
			setMethod.setAccessible(true);
			// 赋值
			setMethod.invoke(modelArm, (Object) String.valueOf(methodValue).trim());
		    }
		} catch (NoSuchMethodException e) {
		    e.printStackTrace();
		} catch (SecurityException e) {
		    e.printStackTrace();
		} catch (IllegalAccessException e) {
		    e.printStackTrace();
		} catch (IllegalArgumentException e) {
		    e.printStackTrace();
		} catch (InvocationTargetException e) {
		    e.printStackTrace();
		}
	    }
	}
    }

    public static String getExceptionAllinformation(Exception ex) {
	String sOut = "";
	StackTraceElement[] trace = ex.getStackTrace();
	for (StackTraceElement s : trace) {
	    sOut += "\tat " + s + "\r\n";
	}
	return sOut;
    }
}
