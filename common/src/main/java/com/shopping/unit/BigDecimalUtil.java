package com.shopping.unit;

import java.math.BigDecimal;

public class BigDecimalUtil {
    private static final int DEF_DIV_SCALE = 10;

    //相加
    public static double add(double d1, double d2, Integer num) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        if (num == null) {
            return b1.add(b2).doubleValue();
        } else {
            return b1.add(b2).setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
        }


    }

    //相减
    public static double sub(double d1, double d2, Integer num) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        if (num == null) {
            return b1.subtract(b2).doubleValue();
        } else {
            return b1.subtract(b2).setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

    }

    //相乘
    public static double mul(double d1, double d2, Integer num) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));

        if (num == null) {
            return b1.subtract(b2).doubleValue();
        } else {
            return b1.multiply(b2).setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }

    //相除
    public static double div(double d1, double d2, Integer num) {

        return div(d1, d2, DEF_DIV_SCALE, num);

    }

    public static double div(double d1, double d2, int scale, Integer num) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        if (num == null) {
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        } else {
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }

    /**
     * 精确除法
     *
     * @param scale
     *            精度
     */
    public static double div(double value1, double value2, int scale) throws IllegalAccessException {
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = BigDecimal.valueOf(value1);
        BigDecimal b2 = BigDecimal.valueOf(value2);
        // return b1.divide(b2, scale).doubleValue();
        return b1.divide(b2, scale, BigDecimal.ROUND_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * @param value 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(BigDecimal value,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal one = new BigDecimal(1);
        return value.divide(one,scale,BigDecimal.ROUND_HALF_UP);
    }


}
