package cn.plou.common.utils;

import java.math.BigDecimal;

/**
 * @Project : tg-services
 * @File : BigDecimalUtil.java
 * @Author : WangJiWei
 * @Date : 2018年2月28日下午3:18:50
 *
 * @Comments :
 * 
 */
public class BigDecimalUtil {
    private BigDecimalUtil() {

    }

    public static BigDecimal add(double v1, double v2) {
	BigDecimal b1 = new BigDecimal(Double.toString(v1));
	BigDecimal b2 = new BigDecimal(Double.toString(v2));
	return b1.add(b2);
    }

    public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
	return b1.add(b2);
    }

    public static BigDecimal sub(double v1, double v2) {
	BigDecimal b1 = new BigDecimal(Double.toString(v1));
	BigDecimal b2 = new BigDecimal(Double.toString(v2));
	return b1.subtract(b2);
    }

    public static BigDecimal sub(BigDecimal b1, BigDecimal b2) {
	return b1.subtract(b2);
    }

    public static BigDecimal mul(double v1, double v2) {
	BigDecimal b1 = new BigDecimal(Double.toString(v1));
	BigDecimal b2 = new BigDecimal(Double.toString(v2));
	return b1.multiply(b2);
    }

    public static BigDecimal mul(BigDecimal b1, BigDecimal b2) {
	return b1.multiply(b2);
    }

    public static BigDecimal div(double v1, double v2) {
	BigDecimal b1 = new BigDecimal(Double.toString(v1));
	BigDecimal b2 = new BigDecimal(Double.toString(v2));
	return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);// 四舍五入,保留2位小数
    }

    public static BigDecimal div(double v1, double v2, int scale) {
	BigDecimal b1 = new BigDecimal(Double.toString(v1));
	BigDecimal b2 = new BigDecimal(Double.toString(v2));
	return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);// 四舍五入,保留scale位小数
    }

    public static BigDecimal div(BigDecimal b1, BigDecimal b2) {
	return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);// 四舍五入,保留2位小数
    }

    public static BigDecimal div(BigDecimal b1, BigDecimal b2, int scale) {
	return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);// 四舍五入,保留scale位小数
    }

}