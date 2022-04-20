package com.hashtech.factory.impl;

import cn.hutool.core.util.StrUtil;
import com.hashtech.common.StateEnum;
import com.hashtech.factory.DatasourceSync;
import com.hashtech.feign.result.DatasourceDetailResult;
import com.hashtech.utils.JdbcUtils;
import com.hashtech.web.request.ResourceTablePreposeRequest;
import com.hashtech.web.result.BaseInfo;
import com.hashtech.web.result.Structure;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xujie
 * @description mysql
 * @create 2021-08-25 15:25
 **/
public class MysqlDatasource implements DatasourceSync {

    /**
     * 取得数据库名称
     *
     * @param uri
     * @return
     */
    @Override
    public String getDatabaseName(String uri) {
        String s = StrUtil.subBefore(uri, "?", false);
        return StrUtil.subAfter(s, "/", true);
    }

    @Override
    public BaseInfo getBaseInfoByType(ResourceTablePreposeRequest request, BaseInfo baseInfo, DatasourceDetailResult datasource, Connection conn, String tableEnglishName) throws Exception{
        String tableChineseName = JdbcUtils.getCommentByTableName(tableEnglishName, conn);
        baseInfo.setDescriptor(tableChineseName);
        baseInfo.setChineseName(tableChineseName);
        baseInfo.setName(tableEnglishName);
        //TODO:select count(*)大表有性能问题
        String getCountSql = new StringBuilder("select COUNT(*) from ").append(request.getTableName()).toString();
        Statement stmt = conn.createStatement();
        ResultSet countRs = stmt.executeQuery(getCountSql);
        if (countRs.next()) {
            //rs结果集第一个参数即为记录数，且其结果集中只有一个参数
            baseInfo.setDataSize(countRs.getLong(1));
        }
        //获取表结构没有性能问题
        List<Structure> structureList = getStructureListLocal(conn, request.getTableName());
        baseInfo.setColumnsCount(structureList.size());
        return baseInfo;
    }

    private List<Structure> getStructureListLocal(Connection conn, String tableName) {
        List<Structure> structureList = new LinkedList<>();
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tableResultSet = metaData.getTables(null, null, tableName,
                    new String[]{"TABLE", "SYSTEM TABLE", "VIEW", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM"});
            while (tableResultSet.next()) {
                String tableEnglishName = tableName;
                String tableChineseName = JdbcUtils.getCommentByTableName(tableEnglishName, conn);
                ResultSet columnResultSet = metaData.getColumns(null, "%", tableEnglishName, "%");
                while (columnResultSet.next()) {
                    Structure structure = new Structure();
                    // 字段名称
                    String columnName = columnResultSet.getString("COLUMN_NAME");
                    structure.setFieldEnglishName(columnName);
                    // 数据类型
                    String columnType = columnResultSet.getString("TYPE_NAME");
                    structure.setType(columnType.toLowerCase());
                    // 描述
                    String remarks = columnResultSet.getString("REMARKS");
                    structure.setFieldChineseName(remarks);
                    structure.setTableEnglishName(tableEnglishName);
                    structure.setTableChineseName(tableChineseName);
                    structure.setDesensitize(StateEnum.NO.ordinal());
                    structure.setReqParam(StateEnum.NO.ordinal());
                    structure.setResParam(StateEnum.YES.ordinal());
                    structureList.add(structure);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return structureList;
    }

}
