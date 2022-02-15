package com.hashtech.service;

import com.hashtech.common.AppException;
import com.hashtech.common.DatasourceTypeEnum;
import com.hashtech.common.ResourceCodeBean;
import com.hashtech.utils.sm4.SM4Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public interface DatasourceSync {

    Logger logger = LoggerFactory.getLogger(DatasourceSync.class);
    String SEPARATOR = "|";
    //整个数据库的表结构json格式
    String DATASOURCEINFO = "datasourceInfo";
    //整个数据库的表数量
    String TABLESCOUNT = "tablesCount";

    /**
     * 根据uri获取jdbc连接
     *
     * @param uri
     * @return
     */
    static String geturi(String uri) {
        //根据uri获取jdbc连接
        return uri.substring(0, uri.indexOf(SEPARATOR));
    }

    /**
     * 获取用户名
     *
     * @param uri
     * @return
     */
    static String getUsername(String uri) {
        //根据uri获取username
        String temp = uri.substring(uri.indexOf("username=") + "username=".length());
        String username = temp.substring(0, temp.indexOf(SEPARATOR));
        return username;
    }

    /**
     * 获取密码
     *
     * @param uri
     * @return
     */
    static String getPassword(String uri) {
        //根据uri获取password
        String temp = uri.substring(uri.indexOf("password=") + "password=".length());
        String password;
        if (!temp.endsWith(SEPARATOR)) {
            password = temp;
        } else {
            password = temp.substring(0, temp.indexOf(SEPARATOR));
        }
        SM4Utils sm4 = new SM4Utils();
        return sm4.decryptData_ECB(password);
    }

    /**
     * 获取连接url
     *
     * @param uri
     * @return
     */
    static String getConnUrl(String uri) {
        String[] split = uri.split("\\|");
        if (Objects.nonNull(split)) {
            return split[0];
        }
        return null;
    }

    String getDatabaseName(String uri);

    static Connection getConn(Integer type, String uri, String username, String password) throws AppException {
        String driver = DatasourceTypeEnum.findDatasourceTypeByType(type).getDriver();
        Connection connection = null;
        try {
            Class.forName(driver);
            String jdbcUri = geturi(uri);
            connection = DriverManager.getConnection(jdbcUri, username, password);
        } catch (Exception e) {
            logger.error("获取连接失败", e);
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000036.getCode());
        }
        return connection;
    }
}
