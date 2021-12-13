package com.hashtech.utils;

import java.math.BigDecimal;

/**
 * @author xujie
 * @description 四舍五入Double变量
 * @create 2021-11-10 14:31
 **/
public class DoubleUtils {
    private static final BigDecimal PERCENT = new BigDecimal(100);

    /**
     * 四舍五入，获取百分比返回
     * @param d
     * @return
     */
    public static double doublePercent(double d) {
        BigDecimal bg = new BigDecimal(d).setScale(4, BigDecimal.ROUND_HALF_UP);
        return bg.multiply(PERCENT).doubleValue();
    }

    public static void main(String[] args) {
        double value = doublePercent(4 / (double)5L);
        System.out.println(value);
    }
}
