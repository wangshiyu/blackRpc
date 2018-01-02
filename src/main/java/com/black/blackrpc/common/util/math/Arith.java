package com.black.blackrpc.common.util.math;

import java.math.BigDecimal;

/***
 * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精 确的浮点数运算，包括加减乘除和四舍五入。
 * 
 */
public class Arith {

	// 默认除法运算精度
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1
	 *            被加数
	 * @param v2
	 *            加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供除法运算（四舍五入），当发生除不尽的情况时，精确到 小数点以后10位。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @return 两个参数的商
	 * @throws Exception
	 */
	public static double div(double v1, double v2) throws Exception {
		return divHalfDown(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供除法运算（四舍五入）。当发生除不尽的情况时，由scale参数指 定精度。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 * @throws Exception
	 */
	public static double divHalfDown(double v1, double v2, int scale)
			throws Exception {
		if (scale < 0) {
			throw new Exception("精确位数必须大于0");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_DOWN).doubleValue();
	}

	/**
	 * 提供除法运算（去尾）。当发生除不尽的情况时，由scale参数指 定精度。
	 * 
	 * @param v1
	 *            被除数
	 * @param v2
	 *            除数
	 * @param scale
	 *            表示表示需要精确到小数点以后几位。
	 * @return 两个参数的商
	 * @throws Exception
	 */
	public static double divRoundDown(double v1, double v2, int scale)
			throws Exception {
		if (scale < 0) {
			throw new Exception("精确位数必须大于0");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_DOWN).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 * @throws Exception
	 */
	public static double round(double v, int scale) throws Exception {
		if (scale < 0) {
			throw new Exception("精确位数必须大于0");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 比较两个double类型数字是否相等，相等返回true
	 * 
	 * @param v1
	 *            参数1
	 * @param v2
	 *            参数2
	 * @return
	 */
	public static boolean compareDouble(double v1, double v2) {
		BigDecimal d1 = new BigDecimal(v1);
		BigDecimal d2 = new BigDecimal(v2);
		if (d1.compareTo(d2) == 0) {// 两double 数字相同
			return true;
		}
		return false;
	}
};
