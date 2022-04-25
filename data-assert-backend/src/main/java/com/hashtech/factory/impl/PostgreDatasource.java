package com.hashtech.factory.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hashtech.common.BusinessPageResult;
import com.hashtech.common.StateEnum;
import com.hashtech.factory.DatasourceSync;
import com.hashtech.feign.result.DatasourceDetailResult;
import com.hashtech.utils.ResultSetToListUtils;
import com.hashtech.web.request.ResourceTablePreposeRequest;
import com.hashtech.web.result.BaseInfo;
import com.hashtech.web.result.Structure;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xujie
 * @description postgresql
 * @create 2022-04-18 15:25
 **/
public class PostgreDatasource implements DatasourceSync {

    /**
     * 取得数据库名称
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
        String schema = DatasourceSync.getSchema(datasource.getUri());
        conn.setSchema(schema);
        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = metaData.getTables(conn.getCatalog(), schema, tableEnglishName,
                new String[]{"TABLE"});
        while (rs.next()){
            String tableChineseName = rs.getString("REMARKS");
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
        }
        return baseInfo;
    }

    @Override
    public BusinessPageResult getSampleList(Connection conn, ResourceTablePreposeRequest request, DatasourceDetailResult datasource) throws Exception {
        int pageNum = Math.min(request.getPageNum(), MAX_IMUM / request.getPageSize());
        int index = (pageNum - 1) * request.getPageSize();
        String sql = new StringBuilder("select ").append(getFilelds(request.getFields())).append(" from ").append(request.getTableName())
                .append(" limit ").append(Math.min(MAX_IMUM - index, request.getPageSize())).append(" offset ").append(index).toString();
        BusinessPageResult result = null;
        Long dataSize = 0L;
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        //TODO:select count(*)大表有性能问题
        String getCountSql = new StringBuilder("select COUNT(*) from ").append(request.getTableName()).toString();
        ResultSet countRs = stmt.executeQuery(getCountSql);
        if (countRs.next()) {
            //rs结果集第一个参数即为记录数，且其结果集中只有一个参数
            dataSize = countRs.getLong(1);
        }
        ResultSet pagingRs = stmt.executeQuery(sql);
        if (pagingRs.next()) {
            List list = ResultSetToListUtils.convertList(pagingRs, request.getDesensitizeFields());
            Page<Object> page = new Page<>(pageNum, request.getPageSize());
            page.setTotal(dataSize);
            result = BusinessPageResult.build(page.setRecords(list), request);
            //这里按前端要求返回pageCount
            result.setPageCount(getPageCountByMaxImum(dataSize, request.getPageSize()));
        }
        return result;
    }

    @Override
    public String getUri(String uri) {
        return uri;
    }

    public List<Structure> getStructureListLocal(Connection conn, String tableName) {
        List<Structure> structureList = new LinkedList<>();
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tableResultSet = metaData.getTables(null, null, tableName,
                    new String[]{"TABLE", "SYSTEM TABLE", "VIEW", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM"});
            while (tableResultSet.next()) {
                String tableEnglishName = tableName;
                String tableChineseName = tableResultSet.getString("REMARKS");
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

    @Override
    public String getFilelds(String fields){
        if (StringUtils.isBlank(fields)){
            return " * ";
        }
        //对字段做处理
        //HTBH,AZRXM,AZRSFZ,AZFWDD,FWDJ,FWZJ,GLFWQQH
        //pg数据库需要对所有返回字符按字段加上""
        String[] split = fields.split(",");
        StringBuilder builder = new StringBuilder();
        for (String key : split) {
            if (key.contains("\"") || (key.contains("(") && key.contains(")"))){
                builder.append(key).append(",");
            }else {
                builder.append("\"" + key + "\"" + ",");
            }
        }
        String s = builder.toString();
        return s.substring(0, s.length() -1);
    }
}
