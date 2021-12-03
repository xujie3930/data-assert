package com.hashtech.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.businessframework.exception.interval.AppException;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.utils.CollectionUtils;
import com.hashtech.businessframework.utils.StringUtils;
import com.hashtech.businessframework.validate.BusinessParamsValidate;
import com.hashtech.common.ResourceCodeBean;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.entity.TableSettingEntity;
import com.hashtech.mapper.TableSettingMapper;
import com.hashtech.service.ResourceTableService;
import com.hashtech.service.TableSettingService;
import com.hashtech.utils.ResultSetToListUtils;
import com.hashtech.web.request.ResourceTablePreposeRequest;
import com.hashtech.web.request.ResourceTablePreviewRequest;
import com.hashtech.web.request.TableSettingUpdateRequest;
import com.hashtech.web.result.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * <p>
 * 资源信息设置表 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2021-11-29
 */
@Service
public class TableSettingServiceImpl extends ServiceImpl<TableSettingMapper, TableSettingEntity> implements TableSettingService {

    @Autowired
    private TableSettingMapper tableSettingMapper;
    @Autowired
    private ResourceTableService resourceTableService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public BusinessResult<TableSettingResult> getTableSetting(String id) {
        TableSettingResult result = new TableSettingResult();
        ResourceTableEntity resourceTableEntity = resourceTableService.getById(id);
        result.setRequestUrl(resourceTableEntity.getRequestUrl());
        TableSettingEntity tableSettingEntity = tableSettingMapper.getByResourceTableId(id);
        BeanCopyUtils.copyProperties(tableSettingEntity, result);
        if (!StringUtils.isBlank(tableSettingEntity.getParamInfo())){
            result.setParamInfo(Arrays.asList(tableSettingEntity.getParamInfo().split(",")));
        }
        if (!StringUtils.isBlank(tableSettingEntity.getColumnsInfo())){
            List<Structure> structureList = JSONObject.parseArray(tableSettingEntity.getColumnsInfo(), Structure.class);
            result.setStructureList(structureList);
        }
        return BusinessResult.success(result);
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> updateTableSetting(String userId, TableSettingUpdateRequest request) {
        //更新资源表
        ResourceTableEntity resourceTableEntity = resourceTableService.getById(request.getId());
        if (Objects.isNull(resourceTableEntity)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000006.getCode());
        }
        resourceTableEntity.setUpdateTime(new Date());
        resourceTableEntity.setUpdateBy(userId);
        resourceTableService.updateById(resourceTableEntity);

        //更新资源表设置
        TableSettingEntity entity = tableSettingMapper.getByResourceTableId(request.getId());
        entity.setRequestWay(request.getRequestWay());
        entity.setExplainInfo(request.getExplainInfo());
        entity.setParamInfo(StringUtils.join(request.getParamInfo(), ","));
        return BusinessResult.success(true);
    }

    @Override
    public BusinessResult<TablePreviewResult> previewTableSetting(String userId, ResourceTablePreviewRequest request) {
        ResourceTableEntity resourceTableEntity = resourceTableService.getById(request.getId());
        String explainInfo = resourceTableEntity.getRequestUrl() + "?";
        if (!CollectionUtils.isEmpty(request.getParamInfo())) {
            for (String param : request.getParamInfo()) {
                explainInfo += param + "=?&";
            }
        }
        explainInfo = explainInfo.substring(0, explainInfo.length() - 1);
        TablePreviewResult result = new TablePreviewResult();
        result.setExplainInfo(explainInfo);
        return BusinessResult.success(result);
    }

    @Override
    @DS("remote")
    public BusinessResult<ResourceTablePreposeResult> getTablaInfo(ResourceTablePreposeRequest request) {
        ResourceTablePreposeResult result = new ResourceTablePreposeResult();
        BaseInfo baseInfo = new BaseInfo();
        List<Structure> structureList = new LinkedList<>();
        Integer columnsCount = 0;
        Connection conn = null;
        try {
            conn = jdbcTemplate.getDataSource().getConnection();
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tableResultSet = metaData.getTables(null, null, request.getTableName(),
                    new String[]{"TABLE", "SYSTEM TABLE", "VIEW", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM"});
            while (tableResultSet.next()) {
                String tableEnglishName = request.getTableName();
                String tableChineseName = tableResultSet.getString("TABLE_COMMENT");
                baseInfo.setName(tableEnglishName);
                ResultSet columnResultSet = metaData.getColumns(null, "%", tableEnglishName, "%");
                while (columnResultSet.next()) {
                    Structure structure = new Structure();
                    // 字段名称
                    String columnName = columnResultSet.getString("COLUMN_NAME");
                    structure.setFieldEnglishName(columnName);
                    // 数据类型
                    String columnType = columnResultSet.getString("TYPE_NAME");
                    structure.setType(columnType);
                    // 描述
                    String remarks = columnResultSet.getString("REMARKS");
                    structure.setFieldChineseName(remarks);
                    structure.setTableEnglishName(tableEnglishName);
                    structure.setTableChineseName(tableChineseName);
                    structureList.add(structure);
                    columnsCount++;
                }
            }
            String getCountSql = "select count(*) from " + request.getTableName();
            Statement stmt = conn.createStatement();
            ResultSet countRs = stmt.executeQuery(getCountSql);
            if (countRs.next()) {
                //rs结果集第一个参数即为记录数，且其结果集中只有一个参数
                baseInfo.setDataSize(countRs.getInt(1));
            }
            Integer index = (request.getPageNum() - 1) * request.getPageSize();
            Integer end = index + request.getPageSize();
            String pagingData = "select * from " + request.getTableName() + " limit " + index + " , " + end;
            ResultSet pagingRs = stmt.executeQuery(pagingData);
            if (pagingRs.next()) {
                List list = ResultSetToListUtils.convertList(pagingRs);
                Page<Object> page = new Page<>(request.getPageNum(), request.getPageSize());
                page.setTotal(baseInfo.getDataSize().longValue());
                result.setSampleList(BusinessPageResult.build(page.setRecords(list), request));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null != conn){
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        baseInfo.setColumnsCount(columnsCount);
        result.setBaseInfo(baseInfo);
        result.setStructureList(structureList);
        return BusinessResult.success(result);
    }

    @Override
    @DS("remote")
    public BusinessResult<List<Map<String, Object>>> getTablaList() {
        String schemaName = "";
        try {
            schemaName = jdbcTemplate.getDataSource().getConnection().getSchema();
        } catch (SQLException throwables) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000009.getCode());
        }
        String tableNameListSql = String.format("select table_name,table_comment from information_schema.tables where table_schema='%s'", schemaName);
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(tableNameListSql);
        return BusinessResult.success(maps);
    }
}
