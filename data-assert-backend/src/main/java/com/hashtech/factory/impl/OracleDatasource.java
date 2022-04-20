package com.hashtech.factory.impl;

import com.hashtech.factory.DatasourceSync;
import com.hashtech.feign.result.DatasourceDetailResult;
import com.hashtech.web.request.ResourceTablePreposeRequest;
import com.hashtech.web.result.BaseInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
}
