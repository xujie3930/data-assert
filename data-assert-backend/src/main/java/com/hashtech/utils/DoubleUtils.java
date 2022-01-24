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
     * 保留两位小数
     *
     * @param num
     * @param count
     * @return
     */
    public static double doublePercent(long num, long count) {
        if (count == 0L) {
            return 0.00;
        }
        double value = new BigDecimal((float) num / count).multiply(PERCENT).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return value;
    }

    public static void main(String[] args) {
        double value = doublePercent(9L, 11L);
        System.out.println(value);
    }
}
