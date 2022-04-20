package com.hashtech.utils;

import org.apache.commons.lang.StringUtils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xujie
 * @description ResultSetToList
 * @create 2021-11-30 11:09
 **/
public class ResultSetToListUtils {

    private static final String SYMBOL = "*";
    private static String regex = String.format("(?<=[A-Za-z0-9\u4e00-\u9fa5\\s\\-\\`\\~\\!\\@\\#\\$\\%%\\&\\*\\(\\)\\+\\=\\|\\{\\}\\'\\:\\;\\,\\[\\]\\.\\<\\>\\/\\?\\~\\！\\@\\#\\￥\\…\\（\\）\\—\\【\\】\\‘\\；\\：\\”\\“\\’\\。\\，\\、\\？\\^\\\\]{%d})[A-Za-z0-9\u4e00-\u9fa5\\s\\-\\`\\~\\!\\@\\#\\$\\%%\\&\\*\\(\\)\\+\\=\\|\\{\\}\\'\\:\\;\\,\\[\\]\\.\\<\\>\\/\\?\\~\\！\\@\\#\\￥\\…\\（\\）\\—\\【\\】\\‘\\；\\：\\”\\“\\’\\。\\，\\、\\？\\^\\\\]", 1);

    public static <T> List<? extends T> convertList(ResultSet rs, String desensitizeFields) throws SQLException {
        List list = new ArrayList();
        //获取键名
        ResultSetMetaData md = rs.getMetaData();
        //获取行的数量
        int columnCount = md.getColumnCount();
//        rs.beforeFirst();
        while (rs.next()) {
            Map rowData = new LinkedHashMap();
            for (int i = 1; i <= columnCount; i++) {
                if (StringUtils.isNotEmpty(rs.getString(i)) && StringUtils.isNotEmpty(desensitizeFields) && desensitizeFields.contains(md.getColumnName(i))) {//对数据做脱敏处理
                    rowData.put(md.getColumnName(i), rs.getString(i).replaceAll(regex, SYMBOL));
                } else {//不需要脱敏
                    rowData.put(md.getColumnName(i), rs.getString(i));
                }
            }
            list.add(rowData);
        }
        return list;
    }
}