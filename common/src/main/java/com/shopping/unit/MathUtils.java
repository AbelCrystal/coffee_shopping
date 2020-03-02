package com.shopping.unit;

import java.math.BigDecimal;

public class MathUtils {

    /**
     * 提供精确加法计算的add方法
     *
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(BigDecimal value1, BigDecimal value2) {
        return value1.add(value2).setScale(8, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确减法运算的sub方法
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static BigDecimal subtract(BigDecimal value1, BigDecimal value2) {
        return value1.subtract(value2).setScale(8, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确乘法运算的mul方法
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal multiply(BigDecimal value1, BigDecimal value2) {
        return value1.multiply(value2).setScale(8, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的除法运算方法div
     *
     * @param value1 被除数
     * @param value2 除数
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static BigDecimal divide(BigDecimal value1, BigDecimal value2) {
        return value1.divide(value2, 2, BigDecimal.ROUND_HALF_DOWN);
    }
    /**
     * 提供精确的除法运算方法div
     *
     * @param value1 被除数
     * @param value2 除数
     * @return 两个参数的商
     * @throws IllegalAccessException
     */
    public static BigDecimal divide8(BigDecimal value1, BigDecimal value2) {
        return value1.divide(value2, 8, BigDecimal.ROUND_HALF_DOWN);
    }

    public static BigDecimal divide6(BigDecimal value1, BigDecimal value2) {
        return value1.divide(value2, 6, BigDecimal.ROUND_HALF_DOWN);
    }

    public static int divideInt(BigDecimal value1, BigDecimal value2) {
        return value1.divide(value2, 0, BigDecimal.ROUND_HALF_DOWN).intValue();
    }
    
    public static BigDecimal convertDown(BigDecimal value, int scale){
    	return value.setScale(scale, BigDecimal.ROUND_FLOOR);
    }

    public static BigDecimal convert(BigDecimal value, int scale){
    	return value.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal convertUp(BigDecimal value, int scale){
    	return value.setScale(scale, BigDecimal.ROUND_CEILING);
    }


    public static void main(String[] args) {
		System.out.println(convertDown(new BigDecimal(1234.24118), 4));
	}
    
}