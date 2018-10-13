package cn.plou.web.common.utils;

/**
 * @Author : WangJiWei
 * @Date : 2017年12月8日下午1:46:47
 *
 * @Comments : 驼峰格式转化工具类
 *
 */
public class CamelCaseUtil {

    private static final char SEPARATOR = '_';

    /**
     * HelloWorld -> hello_world
     */
    public static String toUnderscoreCase(String s) {
	if (s == null) {
	    return null;
	}
	StringBuilder sb = new StringBuilder();
	boolean upperCase = false;
	for (int i = 0; i < s.length(); i++) {
	    char c = s.charAt(i);
	    boolean nextUpperCase = true;
	    if (i < (s.length() - 1)) {
		nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
	    }
	    if (Character.isUpperCase(c)) {
		if (!upperCase || !nextUpperCase) {
		    if (i > 0) {
			sb.append(SEPARATOR);
		    }
		}
		upperCase = true;
	    } else {
		upperCase = false;
	    }
	    sb.append(Character.toLowerCase(c));
	}
	return sb.toString();
    }

    /**
     * hello_world -> HelloWorld
     */
    public static String toCamelCase(String s) {
	if (s == null) {
	    return null;
	}
	s = s.toLowerCase();
	StringBuilder sb = new StringBuilder(s.length());
	boolean upperCase = false;
	for (int i = 0; i < s.length(); i++) {
	    char c = s.charAt(i);
	    if (c == SEPARATOR) {
		upperCase = true;
	    } else if (upperCase) {
		sb.append(Character.toUpperCase(c));
		upperCase = false;
	    } else {
		sb.append(c);
	    }
	}
	return sb.toString();
    }

    public static String toCapitalizeCamelCase(String s) {
	if (s == null) {
	    return null;
	}
	s = toCamelCase(s);
	return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static void main(String[] args) {
	System.out.println(CamelCaseUtil.toUnderscoreCase("ISOCertifiedStaff"));
	System.out.println(CamelCaseUtil.toUnderscoreCase("CertifiedStaff"));
	System.out.println(CamelCaseUtil.toUnderscoreCase("UserID"));
	System.out.println(CamelCaseUtil.toUnderscoreCase("ABc"));
	System.out.println(CamelCaseUtil.toCamelCase("iso_certified_staff"));
	System.out.println(CamelCaseUtil.toCamelCase("certified_staff"));
	System.out.println(CamelCaseUtil.toCapitalizeCamelCase("user_id"));
    }
}