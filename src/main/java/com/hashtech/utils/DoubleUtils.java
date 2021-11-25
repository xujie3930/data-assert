package com.hashtech.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author xujie
 * @description 四舍五入Double变量
 * @create 2021-11-10 14:31
 **/
public class DoubleUtils {
    public static double formatDouble(double d) {
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        BigDecimal bg = new BigDecimal(d).setScale(2, RoundingMode.UP);
        return bg.doubleValue();
    }
}
