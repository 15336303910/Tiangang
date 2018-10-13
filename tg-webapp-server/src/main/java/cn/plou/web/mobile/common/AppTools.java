package cn.plou.web.mobile.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.util.Assert;

/**
 * @Project : tg-webapp-server
 * @File : AppTools.java
 * @Author : WangJiWei
 * @Date : 2018年9月19日下午4:24:08
 *
 * @Comments :
 * 
 */
public class AppTools {

    public static final String TIME_LEFT = " 00:00:00";
    public static final String TIME_RIGHT = " 23:59:59";

    public static <T, R> List<String> objectExtractIds(Collection<T> s,
	    Function<? super T, String> mapper) {
	Assert.notNull(s, "The collection must not be null");
	return s.stream().map(mapper).collect(Collectors.toList());
    }

    public static <R> Map<String, List<R>> groupBy(Collection<R> s,
	    Function<? super R, ? extends String> classifier) {
	Assert.notNull(s, "The collection must not be null");
	Map<String, List<R>> result = s.stream().collect(Collectors.groupingBy(classifier));
	return result;
    }

    /**
     * TODO web中部分工具类与common中重复,待删除
     * 
     * @throws ParseException
     */
    public static Date formatDate(String pattern) throws ParseException {
	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	String result_date = sdf.format(new Date());
	return sdf.parse(result_date);
    }

    public static String curDateStr(String pattern) throws ParseException {
	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	return sdf.format(new Date());
    }

    public static Date formatDate(String dateStr, String pattern) throws ParseException {
	SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	return sdf.parse(dateStr);
    }

}
