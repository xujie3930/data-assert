package com.hashtech.common;


import org.apache.commons.lang3.StringUtils;

/**
 * Project：business-build
 * Package：com.jinninghui.datasphere.framework.result
 * ClassName: SQLFilter
 * Description:  SQLFilter类
 * Date: 2021/3/24 3:25 上午
 *
 * @author liyanhui
 */
public class SQLFilter {

    /**
     * SQL注入过滤
     *
     * @param str 待验证的字符串
     */
    public static String sqlInject(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        //去掉'|"|;|\字符
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");

        //转换成小写
        str = str.toLowerCase();

        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop"};

        //判断是否包含非法字符
        for (String keyword : keywords) {
            if (str.contains(keyword)) {
                throw new IllegalArgumentException("包含非法字符");
            }
        }

        return str;
    }
}
