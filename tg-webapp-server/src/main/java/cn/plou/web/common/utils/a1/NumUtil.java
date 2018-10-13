package cn.plou.web.common.utils.a1;


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
    while(result.length() < 4) {
    	result = "0" + result;
    }
		return result;
	}
	/**
	 * 反算
	 * 
	 */
	public static double computerDiffer(String param) {
		int param1 = Integer.parseInt(param,16);  
		double error = 0;
		double flow = 0;
		flow = param1 * 1.0 / Math.pow(2, 12);
		error = (1 - flow) / flow;
		return error * 100;
	}

	/**
	 * 字符串数组转换为double数组
	 * @param param
	 * @return
	 */
	public static double[] toDouble(String[] param) {

		if(param == null ){
			return null;
		}
		double temp[] = new double[param.length];
		for(int i=0; i<param.length; i++){
			temp[i] = toDouble(param[i]);
		}
		return temp;
	
	}
	
	/**
	 * 时间数组转doulbe数组
	 * @param params
	 * @return
	 */
	public static double[] timeToDouble(String[] params) {

		if(params == null ){
			return null;
		}
		String timeStrings[];
		String timestr = "0";
		double temp[] = new double[params.length];
		for(int i=0; i<params.length; i++){
			timeStrings= params[i].split(":");
			if(timeStrings.length >=2){
				timestr = timeStrings[0] + "." +new BigDecimal((toInt(timeStrings[1])*10)/60).setScale(0, BigDecimal.ROUND_HALF_UP);
			}
			temp[i] = toDouble(timestr);
		}
		
		
		return temp;
	
	}
}
