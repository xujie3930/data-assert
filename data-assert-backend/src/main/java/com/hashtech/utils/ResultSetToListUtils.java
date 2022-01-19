package com.hashtech.utils;

import org.apache.poi.ss.formula.functions.T;

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
    public static List<? extends T> convertList(ResultSet rs) throws SQLException {
        List list = new ArrayList();
        //获取键名
        ResultSetMetaData md = rs.getMetaData();
        //获取行的数量
        int columnCount = md.getColumnCount();
        rs.beforeFirst();
        while (rs.next()) {
            Map rowData = new LinkedHashMap();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }
}
