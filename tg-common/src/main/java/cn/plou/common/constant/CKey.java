package cn.plou.common.constant;

import java.util.regex.Pattern;

/**
 * @Project : tg-services
 * @File : CKey.java
 * @Author : WangJiWei
 * @Date : 2018年1月16日下午2:51:43
 *
 * @Comments :
 * 
 */
public interface CKey {
    /** Date pattern */
    public static final String Y = "yyyy";
    public static final String YM = "yyyyMM";
    public static final String YM_ = "yyyy-MM";
    public static final String YMD = "yyyyMMdd";
    public static final String MD = "MMdd";
    public static final String YMD_ = "yyyy-MM-dd";
    public static final String HMS = "HH:mm:ss";
    public static final String YMDHMS__ = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD_T_HMS = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String YMDH00 = "yyyy-MM-dd HH:00:00";
    public static final String YMD000 = "yyyy-MM-dd 00:00:00";

    /** common string identifier **/
    public static final Pattern COMMA_P = Pattern.compile(",");
    public static final String COMMA = ",";
    public static final Pattern COLON_P = Pattern.compile(":");
    public static final String COLON = ":";
    public static final Pattern SPACE_P = Pattern.compile(" ");
    public static final String SPACE = " ";
    public static final Pattern TAB_P = Pattern.compile("\t");
    public static final String TAB = "\t";
    public static final Pattern UNDERLINE_P = Pattern.compile("_");
    public static final String UNDERLINE = "_";

}
