package cn.plou.web.mobile.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

import cn.plou.web.common.constant.CKey;

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

    public static <K, R> List<R> objectExtractIds(Collection<K> s, Function<? super K, R> mapper) {
	Assert.notNull(s, "The collection must not be null");
	return s.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * groupBy(sdps,StationDataPointVo::getShowdevtype)
     */
    public static <K, V> Map<K, List<V>> groupBy(Collection<V> s,
	    Function<? super V, ? extends K> classifier) {
	Assert.notNull(s, "The collection must not be null");
	Map<K, List<V>> result = s.stream().collect(Collectors.groupingBy(classifier));
	return result;
    }

    /**
     * listToMap(ss, StationShowDevType::getDevType, ssdt -> ssdt);
     */
    public static <K, V> Map<K, V> listToMap(Collection<V> s,
	    Function<? super V, ? extends K> keyMapper,
	    Function<? super V, ? extends V> valueMapper) {
	Assert.notNull(s, "The collection must not be null");
	Map<K, V> result = s.stream().collect(Collectors.toMap(keyMapper, valueMapper));
	return result;
    }

    /**
     * 最值
     */
    public static <R, U> R extremeValue(Collection<R> s, Comparator<? super R> comparator,
	    String extreme) {
	Assert.notNull(s, "The collection must not be null");
	Stream<R> stream = s.stream();
	switch (extreme.toLowerCase()) {
	case "min":
	    Optional<R> min = stream.min(comparator);
	    return min.isPresent() ? min.get() : null;
	default:
	    Optional<R> max = stream.max(comparator);
	    return max.isPresent() ? max.get() : null;
	}
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

    public static List<Date> rangeDate(Date start, Date end) throws ParseException {
	List<Date> list = Lists.newArrayList();
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(start);
	while (calendar.getTime().compareTo(end) != 0) {
	    calendar.add(Calendar.HOUR_OF_DAY, 1);
	    list.add(calendar.getTime());
	}
	calendar.add(Calendar.HOUR_OF_DAY, 1);
	list.add(calendar.getTime());
	return list;
    }

    public static <T> Boolean isNotEmpty(Collection<T> cons) {
	return cons != null && cons.size() != 0;
    }

    public static <T> Boolean isEmpty(Collection<T> cons) {
	return !isNotEmpty(cons);
    }

    public static void main(String[] args) throws Exception {
	String s = "2018-10-17 00:00:00";
	String d = "2018-10-17 23:00:00";
	Date ss = formatDate(s, CKey.YMDH00);
	Date ee = formatDate(d, CKey.YMDH00);
	System.out.println(s);
	List<Date> rangeDate = rangeDate(ss, ee);
	rangeDate.forEach(System.out::println);
//	System.out.println(formatDate(CKey.YMD000));
    }

}
