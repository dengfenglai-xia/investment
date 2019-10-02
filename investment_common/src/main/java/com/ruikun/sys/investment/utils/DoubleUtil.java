package com.ruikun.sys.investment.utils;

import java.math.BigDecimal;

/**
 * @version V1.0
 * @desc AES 浮点数计算工具类
 */
public class DoubleUtil {
	
	/**
	 * 两个double类型的数据相减
	 */
 	public static double doubleSub(double d1,double d2){ 
         BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
         BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
         return bd1.subtract(bd2).doubleValue(); 
    } 
 	/**
	 * 两个double类型的数据相加
	 */
 	public static double doubleAdd(double d1,double d2){ 
 		BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
 		BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
 		return bd1.add(bd2).doubleValue(); 
 	}
 	/**
	 * 两个double类型的数据相乘
	 */
 	public static double doubleMultiply(double d1,double d2){ 
 		BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
 		BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
 		return bd1.multiply(bd2).doubleValue(); 
 	} 
 	/**
	 * 两个double类型的数据相除
	 */
 	public static double doubleDivide(double d1,double d2){ 
 		BigDecimal bd1 = new BigDecimal(Double.toString(d1)); 
 		BigDecimal bd2 = new BigDecimal(Double.toString(d2)); 
 		return bd1.divide(bd2).doubleValue(); 
 	}
 	
 	/**
	 * 两个BigDecimal类型的数据相减
	 */
 	public static BigDecimal bigDecimalSub(BigDecimal bd1,BigDecimal bd2){ 
         return bd1.subtract(bd2); 
    } 
 	/**
	 * 两个BigDecimal类型的数据相加
	 */
 	public static BigDecimal bigDecimalAdd(BigDecimal bd1,BigDecimal bd2){ 
 		return bd1.add(bd2); 
 	}
 	/**
	 * 两个BigDecimal类型的数据相乘
	 */
 	public static BigDecimal bigDecimalMultiply(BigDecimal bd1,BigDecimal bd2){ 
 		return bd1.multiply(bd2); 
 	} 
 	/**
	 * 两个BigDecimal类型的数据相除
	 */
 	public static BigDecimal bigDecimalDivide(BigDecimal bd1,BigDecimal bd2){ 
 		return bd1.divide(bd2); 
 	}
}