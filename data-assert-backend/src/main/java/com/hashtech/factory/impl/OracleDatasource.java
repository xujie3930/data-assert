package com.hashtech.factory.impl;

import com.hashtech.common.BusinessPageResult;
import com.hashtech.factory.DatasourceSync;
import com.hashtech.feign.result.DatasourceDetailResult;
import com.hashtech.web.request.ResourceTablePreposeRequest;
import com.hashtech.web.result.BaseInfo;

import java.sql.Connection;

/**
 * @author xujie
 * @description mysql
 * @create 2021-08-25 15:25
 **/
public class OracleDatasource implements DatasourceSync {

    /**
     * 取得数据库名称
     *
     * @param uri
     * @return
     */
    @Override
    public String getDatabaseName(String uri) {
//        String oracleUri = uri.split("\\|")[0];
//        int index = oracleUri.lastIndexOf(":") + 1;
//        return oracleUri.substring(index);
        return DatasourceSync.getUsername(uri);
    }

    @Override
    public BaseInfo getBaseInfoByType(ResourceTablePreposeRequest request, BaseInfo baseInfo, DatasourceDetailResult datasource, Connection conn, String tableEnglishName) throws Exception{
        return null;
    }

    @Override
    public BusinessPageResult getSampleList(Connection conn, ResourceTablePreposeRequest request, DatasourceDetailResult datasource) throws Exception {
        return null;
    }

    @Override
    public String getUri(String uri) {
        if (uri.contains(SPLIT_URL_FLAG)) {//url包含？ -- jdbc:mysql://192.168.0.193:3306/data_source?username=root
            return String.valueOf(new StringBuffer(uri.substring(0, uri.indexOf(SPLIT_URL_FLAG))).append(SPLIT_URL_FLAG).append(SQL_CHARACTER));
        } else if (uri.contains(SEPARATOR)) {//url不包含？但包含|  -- jdbc:mysql://192.168.0.193:3306/data_source|username=root
            return String.valueOf(new StringBuffer(uri.substring(0, uri.indexOf(SEPARATOR))).append(SPLIT_URL_FLAG).append(SQL_CHARACTER));
        } else {//url不包含？和| -- jdbc:mysql://192.168.0.193:3306/daas
            return String.valueOf(new StringBuffer(uri).append(SPLIT_URL_FLAG).append(SQL_CHARACTER));
        }
    }
}
