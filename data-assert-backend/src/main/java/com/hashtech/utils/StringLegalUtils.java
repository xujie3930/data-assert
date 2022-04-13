package com.hashtech.utils;


import com.hashtech.common.AppException;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang.StringUtils;

/**
 * @author xujie
 * @description 校验输入字符串合法性
 * @create 2022-02-22 18:56
 **/
public class StringLegalUtils {

    public static Boolean checkLegalName(String name) {
        if (!name.matches("[A-Za-z0-9\u4e00-\u9fa5_]{0,50}")) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        String str = "0.0.0.0";
    }
}
