package com.hashtech.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author xujie
 * @description jdbc工具类
 * @create 2021-12-06 11:39
 **/
public class JdbcUtils {

    public static String getCommentByTableName(String tableName, Connection conn) throws Exception {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + tableName);
            if (rs != null && rs.next()) {
                String createDDL = rs.getString(2);
                String comment = parse(createDDL);
                return comment;
            }
            return null;
        } finally {
            stmt.close();
        }
    }

    public static String parse(String all) {
        String comment = null;
        int index = all.indexOf("COMMENT='");
        if (index < 0) {
            return "";
        }
        comment = all.substring(index + 9);
        comment = comment.substring(0, comment.length() - 1);
        return comment;
    }
}
