package cn.plou.common.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public final class NumUtil {

    private NumUtil() {
    }

    public static Long toLong(String value) {
	return toLong(value, 0L);
    }

    public static Long toLong(String value, Long defaultValue) {
	Long result = null;
	try {
	    result = Long.parseLong(value);
	} catch (Exception e) {
	    result = defaultValue;
	}
	return result;
    }

    public static int toInt(String value) {
	return toInt(value, 0);
    }

    public static int toInt(String value, int defaultValue) {
	int result;
	try {
	    result = Integer.parseInt(value);
	} catch (Exception e) {
	    result = defaultValue;
	}
	return result;
    }

    public static double toDouble(String value) {
	return toDouble(value, 0.0);
    }

	public static Double toDoubleNull(String value) {
		Double result;
		try {
			result = Double.parseDouble(value);
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

    public static double toDouble(String value, double defaultValue) {
	double result;
	try {
	    result = Double.parseDouble(value);
	} catch (Exception e) {
	    result = defaultValue;
	}
	return result;
    }
  

    public static float toFloat(String value) {
	return toFloat(value, 0);
    }

    public static float toFloat(String value, float defaultValue) {
	float result;
	try {
	    result = Float.parseFloat(value);
	} catch (Exception e) {
	    result = defaultValue;
	}
	return result;
    }

    public static long toPrimitiveLong(Long value) {
	return toPrimitiveLong(value, 0L);
    }

    public static long toPrimitiveLong(Long value, long defaultValue) {
	long num = 0L;
	if (isNull(value)) {
	    num = defaultValue;
	} else {
	    num = value.longValue();
	}
	return num;
    }

    public static boolean isNull(Long num) {
	return num == null;
    }

    public static boolean isNum(String value) {
	boolean result = false;
	try {
	    Long.parseLong(value);
	    result = true;
	} catch (Exception e) {
	    result = false;
	}
	return result;
    }

    public static String toString(Long num) {
	if (isNull(num)) {
	    return "";
	}
	return num.toString();
    }

    public static boolean isCorrectRange(String from, String to) {
	if (!isNum(from) || !isNum(to)) {
	    return false;
	}

	return toLong(from) <= toLong(to);
    }

    public static String summary(String... quantitys) {
	Long summary = 0L;
	for (String quantity : quantitys) {
	    summary += toLong(quantity);
	}
	return toString(summary);
    }

    public static long parseLong(Object obj) {
	if (obj != null && obj instanceof Number) {
	    Number numObj = (Number) obj;
	    return numObj.longValue();
	}
	return 0L;
    }

    public static Long coalesce(Long... values) {
	if (values != null && values.length > 0) {
	    for (int i = 0; i < values.length; i++) {
		if (values[i] != null) {
		    return values[i];
		}
	    }
	}
	return null;
    }

    public static String formatNumber(Long value) {
	if (isNull(value)) {
	    return null;
	}
	NumberFormat format = NumberFormat.getInstance(Locale.CHINA);
	return format.format(value);
    }

    public static String formatNumber(String value) {
	if (isNum(value)) {
	    return formatNumber(toLong(value));
	}
	return null;
    }

    public static boolean isNegative(Long num) {
	if (isNull(num) || num >= 0L) {
	    return false;
	}
	return true;
    }

    public static boolean isPositive(Long num) {
	if (isNull(num) || num <= 0L) {
	    return false;
	}
	return true;
    }

    /**
     * 计算误差
     * 
     */
    public static String computerParam(double param) {
	double wc = param / 100.0;
	if (Double.isNaN(wc)) {
	    wc = 0.0;
	}
	if (wc == -1.0) {
	    wc = -1.00001;
	}

	double flow = 1.0 / (1.0 + wc);

	int nResult = 0;

	if (flow > 1.0) {
	    nResult = 0x1000;
	    while (flow > 1.0) {
		flow -= 1.0;
	    }
	} else {
	    nResult = 0x0000;
	}

	flow *= 16 * 16 * 16;
	nResult = nResult + (int) flow;
	String result = Integer.toHexString(nResult);
	while (result.length() < 4) {
	    result = "0" + result;
	}
	return result;
    }

    /**
     * 反算
     * 
     */
    public static double computerDiffer(String param) {
	int param1 = Integer.parseInt(param, 16);
	double error = 0;
	double flow = 0;
	flow = param1 * 1.0 / Math.pow(2, 12);
	error = (1 - flow) / flow;
	return error * 100;
    }

    /**
     * 字符串数组转换为double数组
     * 
     * @param param
     * @return
     */
    public static double[] toDouble(String[] param) {

	if (param == null) {
	    return null;
	}
	double temp[] = new double[param.length];
	for (int i = 0; i < param.length; i++) {
	    temp[i] = toDouble(param[i]);
	}
	return temp;

    }

    /**
     * 时间数组转doulbe数组
     * 
     * @param params
     * @return
     */
    public static double[] timeToDouble(String[] params) {

	if (params == null) {
	    return null;
	}
	String timeStrings[];
	String timestr = "0";
	double temp[] = new double[params.length];
	for (int i = 0; i < params.length; i++) {
	    timeStrings = params[i].split(":");
	    if (timeStrings.length >= 2) {
		timestr = timeStrings[0] + "." + new BigDecimal((toInt(timeStrings[1]) * 10) / 60)
			.setScale(0, BigDecimal.ROUND_HALF_UP);
	    }
	    temp[i] = toDouble(timestr);
	}

	return temp;

    }

    // 把字节转成10进制整数0x25 0x26--9510
    public static Integer ByteToInt(byte... values) {
	String id = "";
	if (values != null && values.length > 0) {
	    for (int i = 0; i < values.length; i++) {
		id += StringUtil.convertCharHex(values[i]);
	    }
	}
	return Integer.valueOf(id, 16);
    }

    // 把字节转成16进制整数0x25 0x26--2526
    public static Integer ByteToHexInt(byte... values) {
	String id = "";
	if (values != null && values.length > 0) {
	    for (int i = 0; i < values.length; i++) {
		id += StringUtil.convertCharHex(values[i]);
	    }
	}
	return Integer.valueOf(id, 10);
    }

    // int转换为byte[4]数组
    public static byte[] getByteArray(int i) {
	byte[] b = new byte[4];
	b[0] = (byte) ((i & 0xff000000) >> 24);
	b[1] = (byte) ((i & 0x00ff0000) >> 16);
	b[2] = (byte) ((i & 0x0000ff00) >> 8);
	b[3] = (byte) (i & 0x000000ff);
	return b;
    }
    // int转换为byte[2]数组
    public static byte[] getByteArray2(int i) {
	byte[] b = new byte[2];
	b[0] = (byte) ((i & 0x0000ff00) >> 8);
	b[1] = (byte) (i & 0x000000ff);
	return b;
    }
    // float转换为byte[4]数组
    public static byte[] getByteArray(float f) {
	int intbits = Float.floatToIntBits(f);// 将float里面的二进制串解释为int整数
	return getByteArray(intbits);
    }

    // 从byte数组的index处的连续4个字节获得一个int
    public static int getInt(byte[] arr, int index) {
	return (0xff000000 & (arr[index] << 24)) | (0x00ff0000 & (arr[index + 1] << 16))
		| (0x0000ff00 & (arr[index + 2] << 8)) | (0x000000ff & arr[index + 3]);
    }
    // 从byte数组的index处的连续4个字节获得一个int
    public static int getInt2(byte[] arr, int index) {
	return (0xff00 & (arr[index] << 8)) | (0x00ff & (arr[index + 1]));
    }
    // 从byte数组的index处的连续4个字节获得一个float
    public static float getFloat(byte[] arr, int index) {
	return Float.intBitsToFloat(getInt(arr, index));
    }

    public static Float getXiaoshu(Integer inFlow2) {
	float step = 0.1f;
	while (inFlow2 * step > 1) {
	    step *= 0.1f;
	}
	return inFlow2 * step;
    }

}