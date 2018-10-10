package cn.plou.common.constant;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import cn.plou.common.custom.function.FiFunction;

/**
 * @Project :
 * @File :FunctionContant.java
 * @Author : wangjiwei
 * @Date :2016年11月13日下午4:39:10
 *
 * @Comments : 常量、常用函数
 * 
 */
public interface FuncKey {

    /** 自定义函数 **/

    static DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * 函数:字符串是否为空,是空返回true
     */
    static final Predicate<String> IS_BLANK = (s) -> {
	int strLen;
	if (s == null || (strLen = s.length()) == 0) {
	    return true;
	}
	for (int i = 0; i < strLen; i++) {
	    if (Character.isWhitespace(s.charAt(i)) == false) {
		return false;
	    }
	}
	return true;
    };

    /**
     * 函数:java.util.Date转指定格式日期字符串
     */
    static final UnaryOperator<String> DATE_FORMAT = (o) -> {
	SimpleDateFormat SDF = new SimpleDateFormat();
	SDF.applyPattern(o);
	return SDF.format(new Date());
    };

    /**
     * 函数:日期字符串转java.util.Date
     */
    static final BiFunction<String, String, Date> TIME_STR_TO_DATE = (o, f) -> {
	try {
	    SimpleDateFormat SDF = new SimpleDateFormat();
	    SDF.applyPattern(f);
	    return SDF.parse(o);
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	return null;
    };

    /**
     * 函数:java.util.Date转日期字符串
     */
    static final BiFunction<Date, String, String> DATE_TO_TIME_STR = (o, f) -> {
	try {
	    SimpleDateFormat SDF = new SimpleDateFormat();
	    SDF.applyPattern(f);
	    return SDF.format(o);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    };

    /**
     * 函数:字符编码转换
     */
    static final FiFunction<String, String, String, String> CHANGE_CHARSET = (o, f, p) -> {
	try {
	    return new String(o.getBytes(f), p);
	} catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
	}
	return null;
    };

}
