package cn.plou.common.utils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文字列编号
 * 
 */
public final class StringUtil {

	public static final String EMPTY = "";

	private StringUtil() {
	}

	/**
	 * 返回最初文字列
	 */
	public static String coalesce(String... values) {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (isNotNull(values[i])) {
					return values[i];
				}
			}
		}
		return null;
	}

	public static String coalesceEmptyValue(String... values) {
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				if (hasValue(values[i])) {
					return values[i];
				}
			}
		}
		return null;
	}

	public static boolean isNull(String value) {
		return value == null;
	}

	public static boolean isNotNull(String value) {
		return value != null;
	}

	public static boolean isNullOrEmpty(String value) {
		return value == null || value.length() == 0;
	}

	public static boolean isNullOrEmptyAnyOf(String... strings) {
		for (String str : strings) {
			if (isNullOrEmpty(str)) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasValue(String str) {
		return !isNullOrEmpty(str);
	}

	public static boolean hasValueAllOf(String... strings) {
		if (strings == null) {
			return false;
		}
		boolean result = true;
		for (String s : strings) {
			result &= hasValue(s);
		}
		return result;
	}

	public static boolean hasValueAnyOf(String... strings) {
		if (strings == null) {
			return false;
		}
		boolean result = false;
		for (String s : strings) {
			result |= hasValue(s);
		}
		return result;
	}

	public static boolean isCorrectRange(String from, String to) {
		if (isNullOrEmpty(from) || isNullOrEmpty(to)) {
			return false;
		}
		return from.compareTo(to) <= 0;
	}

	public static String toHiragana(String str) {
		if (isNullOrEmpty(str)) {
			return str;
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= '\u30A1' && c <= '\u30F6') {
				c -= (char) 0x60;
			}
			builder.append(c);
		}
		return builder.toString();
	}

	public static String joint(char delim, String... args) {
		if (args.length > 1) {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < args.length; i++) {
				if (i > 0) {
					builder.append(delim);
				}
				builder.append(args[i]);
			}
			return builder.toString();
		} else if (args.length == 1) {
			return args[0];
		}
		return EMPTY;
	}

	public static String toPascalFormat(String str) {
		return toPascalOrCamel(str, false);
	}

	public static String toCamelFormat(String str) {
		return toPascalOrCamel(str, true);
	}

	public static String toUnderScoreDelimitedFormat(String str) {

		if (StringUtil.isNullOrEmpty(str)) {
			return str;
		}

		StringBuffer buffer = new StringBuffer();
		Matcher m = Pattern.compile("(IO|[A-Z])").matcher(str);
		while (m.find()) {
			m.appendReplacement(buffer, "_" + m.group().toLowerCase(Locale.getDefault()));
		}
		m.appendTail(buffer);
		if (buffer.toString().startsWith("_")) {
			buffer.deleteCharAt(0);
		}
		return buffer.toString().toUpperCase(Locale.getDefault());
	}

	private static String toPascalOrCamel(String str, boolean isCamel) {
		if (hasValue(str)) {
			String[] tokens = str.split("_");
			StringBuilder builder = new StringBuilder();
			for (String t : tokens) {
				if (isNullOrEmpty(t)) {
					continue;
				} else if (t.equalsIgnoreCase("io")) {
					builder.append(t.toUpperCase(Locale.getDefault()));
				} else if (isCamel) {
					builder.append(t.substring(0, 1).toLowerCase(Locale.getDefault()));
					builder.append(t.substring(1).toLowerCase(Locale.getDefault()));
				} else {
					builder.append(t.substring(0, 1).toUpperCase(Locale.getDefault()));
					builder.append(t.substring(1).toLowerCase(Locale.getDefault()));
				}
				isCamel = false;
			}
			return builder.toString();
		} else {
			return str;
		}
	}

	private static final String HALFWIDTH_SPACE = " ";

	private static final String FULLWIDTH_SPACE = "　";

	public static List<String> getSearchWordStringList(String searchWord) {
		if (hasValue(searchWord)) {
			String str = searchWord.replace(FULLWIDTH_SPACE, HALFWIDTH_SPACE).trim();
			if (hasValue(str)) {
				return Arrays.asList(str.split(HALFWIDTH_SPACE));
			}
		}
		return Collections.emptyList();
	}

	public static List<String> getSearchWordStringList(String searchWord, int maxLength) {
		if (hasValue(searchWord) && searchWord.length() > maxLength) {
			searchWord = searchWord.substring(0, maxLength);
		}
		return getSearchWordStringList(searchWord);
	}

	public static String addZero(String no, int cnt) {
		if (no == null) {
			return EMPTY;
		}

		String result = no;
		int setCnt = cnt - no.length();
		if (hasValue(no)) {
			for (int i = 0; i < setCnt; i++) {
				result = "0" + result;
			}
			return result;
		} else {
			return EMPTY;
		}
	}

	public static String convertEncoding(String value, String srcEncoding, String destEncoding) {
		String newString = EMPTY;
		if (hasValue(value)) {
			try {
				newString = new String(value.getBytes(srcEncoding), destEncoding);
			} catch (UnsupportedEncodingException ueEx) {
				newString = EMPTY;
			}
		} else {
			newString = value;
		}
		return newString;

	}

	public static String capitalize(String value) {
		if (hasValue(value)) {
			char[] chars = value.toLowerCase(Locale.getDefault()).toCharArray();

			chars[0] = Character.toUpperCase(chars[0]);
			return new String(chars);
		}
		return value;
	}

	public static String initCaps(String input) {
		return initCaps(input, "[A-Za-z0-9]+");
	}

	public static String initCaps(String input, String pattern) {
		if (hasValue(input)) {
			Matcher m = Pattern.compile(pattern).matcher(input);
			StringBuffer sb = new StringBuffer();
			while (m.find()) {
				String x = m.group();
				m.appendReplacement(sb, capitalize(x));
			}
			m.appendTail(sb);
			return sb.toString();
		}
		return input;
	}

	public static String getHeadline(String str, int size) {
		return getHeadline(str, size, "...");
	}

	public static String getHeadline(String str, int size, String tail) {
		if (hasValue(str) && str.length() > size) {
			return str.substring(0, size) + tail;
		} else {
			return str;
		}
	}

	public static String convertForEmailString(String str) {
		if (hasValue(str)) {
			StringBuilder builder = new StringBuilder();
			for (int cc : str.toCharArray()) {
				switch (cc) {
				case 0xff5e: // FULLWIDTH TILDE
					cc = 0x301c; // WAVE DASH
					break;
				case 0x2225: // PARALLEL TO
					cc = 0x2016; // DOUBLE VERTICAL LINE
					break;
				case 0xff0d: // FULLWIDTH HYPHEN-MINUS
					cc = 0x2212; // MINUS SIGN
					break;
				case 0xffe0: // FULLWIDTH CENT SIGN
					cc = 0x00a2; // CENT SIGN
					break;
				case 0xffe1: // FULLWIDTH POUND SIGN
					cc = 0x00a3; // POUND SIGN
					break;
				case 0xffe2: // FULLWIDTH NOT SIGN
					cc = 0x00ac; // NOT SIGN
					break;
				case 0x2015: // EM DASH
					cc = 0x2014; // HORIZONTAL BAR
					break;
				default:
					break;
				}
				builder.append((char) cc);
			}
			return builder.toString();
		}
		return str;
	}

	public static String times(String value, int times) {
		StringBuilder builder = new StringBuilder();
		if (times < 1) {
			return value;
		}
		while (--times > 0) {
			builder.append(value);
		}
		return builder.toString();
	}

	public static String toFormatString(String strValue, int strLength, boolean formatFlg, String formtaValue) {

		int i = 0;

		int intSize = 0;

		String newStrvalue = "";

		if (strValue != null && strValue.getBytes().length > strLength) {

			for (i = 0; i < strValue.toCharArray().length; i++) {

				if ((newStrvalue + strValue.toCharArray()[i]).toString().getBytes().length > strLength) {

					newStrvalue = newStrvalue + " ";

				} else {

					newStrvalue = newStrvalue + strValue.toCharArray()[i];

				}

				if (newStrvalue.getBytes().length == strLength) {

					break;
				}
			}

			strValue = newStrvalue;

		} else if (strValue != null && strValue.getBytes().length == strLength) {

			return strValue;

		} else if (strValue != null && strValue.getBytes().length < strLength) {

			intSize = strValue.getBytes().length;

			for (i = 0; i < strLength - intSize; i++) {

				if (formatFlg == true) {
					strValue = strValue + formtaValue;

				} else {

					strValue = formtaValue + strValue;
				}
			}

		} else {

			for (i = 0; i < strLength; i++) {

				if (formatFlg == true) {

					newStrvalue = newStrvalue + formtaValue;

				} else {

					newStrvalue = formtaValue + newStrvalue;
				}
			}

			strValue = newStrvalue;
		}

		return strValue;
	}

	/**
	 * 转换为十六进制
	 * 
	 * @param str
	 * @return
	 * 16  0x16
	 */
	public static String toHexString(String str) {
		return toHexString(NumUtil.toInt(str));
	}

	//0x21--21   21--15
	public static String toHexString(int param) {
		String hex = Integer.toHexString(param);
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		return hex.toUpperCase();
	}

	/**
	 * 十进制转换为字节
	 * 
	 * @param str
	 * @return
	 * 234--0xEA 0x00 0x00 0x00 
	 */
	public static byte[] decString2Bytes(String str) {
		return HexString2Bytes(toHexString(str));
	}
	//234--0xEA 0x00 0x00 0x00 
	public static byte[] decString2Bytes(int param) {
		return HexString2Bytes(toHexString(param));
	}

	/**
	 * 两位十六进制数转换为一个字节
	 * 
	 * @param src0
	 * @param src1
	 * @return
	 */
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	/* 字符串转比特 逆序*/
	//161718  0x18 0x17 0x16
	public static byte[] HexString2Bytes(String src) {
		if (src.length() % 2 != 0)
			src = "0" + src;
		/* 这里针对长度可变要求做的调整 */
		int len = src.length() / 2;
		byte[] ret = new byte[(src.length() / 2) > 4 ? src.length() / 2 : 4];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = (0x00);
		}
		byte[] tmp = src.getBytes();
		/* 这里是逆序 */
		for (int i = 0; i < len; i++) {
			ret[len - i - 1] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}
	
	/* 字符串转比特 正序*/
	//161718  0x16 0x17 0x18
	public static byte[] HexString2BytesNormal(String src) {
		src = src.replace(" ", "");
		if (src.length() % 2 != 0)
			src = "0" + src;
		/* 这里针对长度可变要求做的调整 */
		int len = src.length() / 2;
		byte[] ret = new byte[(src.length() / 2) > 4 ? src.length() / 2 : 4];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = (0x00);
		}
		
		byte[] tmp = src.getBytes();
		/* 这里是正序*/
		for (int i = 0; i < len; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	/* 比特转字符串 */
	//0x68 0x21 0x22  --  68 21 22
	public static String Bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase() + " ";
		}
		return ret;
	}

	// Hex转换为char类型
	//21--15   0x21--21
	public static String convertCharHex(int by) {
		String hex = Integer.toHexString(by & 0xFF);
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		return hex.toUpperCase();

	}
	// 2进制字符串转成字节
	//00011111---0x1f
	public static byte convertChar2Hex(String by) {
		return (byte)Integer.parseInt(by,2);
	}
	//21--00010101  0x21--00100001
	public static String convertCharBinary(int by) {
		String binary = Integer.toBinaryString(by & 0xFF);
		 int length = binary.length();
     if(binary.length()<8){   
       for(int i=0;i<8-length;i++){
      	 binary = "0"+binary; // 在不足的位数前都加“0”
       }
     }
   return binary;
	}

	

	/**
	 * 字节转换为Int
	 * @param src
	 * @return
	 * 0x16--22
	 */
	public static int decode(byte src) {
		int i;

		i = src;
		i &= 0xFF;

		return i;
	}

	/**
	 * 字节转换为Int
	 * @param src
	 * @return
	 * 0x16--22
	 */
	public static String decodeStr(byte src) {
		int i;

		i = src;
		i &= 0xFF;

		return String.valueOf(i);
	}

	/**
	 * 多字节十六进制数转换为10进制
	 * @param src
	 * @return
	 * 0x12 0x34---13330
	 */
	public static int hexToDec(byte... src){
		return hexToDec(src,ByteOrder.BIG_ENDIAN);
	}
	/**
	 * 多字节十六进制数转换为10进制
	 * @param src
	 * @param endian
	 * @return
	 */
	public static int hexToDec(byte[] src, ByteOrder endian) {
		int result;

		if (src.length < 1 || src.length > 4) {
			throw new IllegalArgumentException("src.length == " + src.length);
		}

		result = 0;

		if (endian == ByteOrder.BIG_ENDIAN) {
			for (int i = 0; i < src.length; i++) {
				result |= decode(src[i]) << (i * 8);
			}
		} else if (endian == ByteOrder.LITTLE_ENDIAN) {
			for (int i = 0, j = src.length - 1; i < src.length; i++, j--) {
				result |= decode(src[i]) << (j * 8);
			}
		} else {
			throw new NullPointerException("endian == null");
		}

		return result;
	}
	
	/**
	 * 截取数组
	 * 
	 * @param data
	 * @param start
	 * @param length
	 * @return
	 * 0x12 0x34 0x56 0x78-1-2- 0x34 0x56
	 */
	public static byte[] getSubArr(byte[] data, int start, int length) {
		byte[] temp = new byte[length];
		for (int i = 0; i < length; i++) {
			temp[i] = data[start + i];
		}
		return temp;
	}
	
	public static byte[] getSubArr(ArrayList<Byte> data, int start, int length) {
		byte[] temp = new byte[length];
		for (int i = 0; i < length; i++) {
			temp[i] = data.get(start + i);
		}
		return temp;
	}


	// 字符串数组排序(由大到小)
	public static String[] sort(String[] array) {
		int length = array.length;
		for (int out = 1; out < length; out++) {
			String temp = array[out];
			int in = out;
			while (in > 0 && array[in - 1].compareTo(temp) < 0) {
				array[in] = array[in - 1];
				--in;
			}
			array[in] = temp;
		}
		return array;
	}

	/**
	 * 日期型填充（由1401填充为2014-01-01）
	 * 
	 * @param param
	 * @return
	 */
	public static String addZeroToYear(String param) {
		String returnStr;
		returnStr = param;
		if (returnStr.length() == 3) {
			returnStr = "0" + returnStr;
		}
		returnStr = "20" + returnStr.substring(0, 2) + "-" + returnStr.substring(2, 4) + "-01";
		return returnStr;
	}
	/**
	 * 计算和
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte getSum(byte[] bytes) {
		byte SUM = (byte) 0x00;
		for (int i = 4; i < bytes.length - 2; ++i) {
			SUM += bytes[i];
		}
		return SUM;
	}

	public static byte getSum(byte[] bytes, int length, int start) {
		byte SUM = (byte) 0x00;
		for (int i = start; i < start + length ; i++) {
			SUM += bytes[i];
			// System.out.println(bytes[i]);
		}
		return SUM;
	}

	public static byte getSum(ArrayList<Byte> bytes, int length, int start) {
		byte SUM = (byte) 0x00;
		for (int i = start + 4; i < start + length + 4; i++) {
			SUM += bytes.get(i);
		}
		return SUM;
	}


	
	// type 1 左侧补零 2 右侧补零
	public static String convertFullZero(String str, int len, int type) {
		String str0 = "0";
		for (int i = 0; i < len; i++) {
			str0 += "0";
		}
		if (type == 1) {
			str = str0 + str;
			str = str.substring(str.length() - len, str.length());
		} else if (type == 2) {
			str = str + str0;
			str = str.substring(0, len);
		}
		return str;
	}

	// 把字节转成16进制字符串 0x25 0x26--2526
	public static String ByteToHexString(byte... values) {
		String id = "";
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				id += StringUtil.convertCharHex(values[i]);
			}
		}
		return id;
	}

	// 把字节转成10进制字符串0x25 0x26--9510
	public static String ByteToString(byte... values) {
		String id = "";
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				id += StringUtil.convertCharHex(values[i]);
			}
		}
		return String.valueOf(Integer.valueOf(id, 16));
	}

	/**
	 * byte数组转换为二进制字符串,每个byte 8个字节，不足补零 0x25 --00100101
	 **/
	public static String conver2HexStr(byte... b) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String str = Long.toString(b[i] & 0xff, 2);
			str = convertFullZero(str, 8, 1);
			result.append(str);
		}
		return result.toString();
    }

    /* 字符串转一个比特 */
    public static byte HexString2OneByte(String src) {
		try{
		    return (byte) ((Integer.parseInt(src)));
		}catch(Exception ex){
		    return 0;
		}
    }
}
