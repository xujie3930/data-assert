package com.hashtech.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.businessframework.exception.interval.AppException;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.sequence.api.SequenceService;
import com.hashtech.businessframework.utils.CollectionUtils;
import com.hashtech.businessframework.validate.BusinessParamsValidate;
import com.hashtech.common.ResourceCodeBean;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.entity.TableSettingEntity;
import com.hashtech.mapper.ResourceTableMapper;
import com.hashtech.mapper.ThemeResourceMapper;
import com.hashtech.service.ResourceTableService;
import com.hashtech.service.TableSettingService;
import com.hashtech.utils.DatabaseProperty;
import com.hashtech.utils.ResultSetToListUtils;
import com.hashtech.web.ResourceTableInfoRequest;
import com.hashtech.web.request.ResourceTablePageListRequest;
import com.hashtech.web.request.ResourceTablePreposeRequest;
import com.hashtech.web.request.ResourceTableSaveRequest;
import com.hashtech.web.request.ResourceTableUpdateRequest;
import com.hashtech.web.result.BaseInfo;
import com.hashtech.web.result.ResourceTableInfoResult;
import com.hashtech.web.result.ResourceTablePreposeResult;
import com.hashtech.web.result.Structure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 主题资源表 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2021-11-23
 */
@Service
public class ResourceTableServiceImpl extends ServiceImpl<ResourceTableMapper, ResourceTableEntity> implements ResourceTableService {

    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private ThemeResourceMapper themeResourceMapper;
    @Autowired
    private ResourceTableMapper resourceTableMapper;
    @Autowired
    private TableSettingService tableSettingService;

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> saveResourceTable(String userId, ResourceTableSaveRequest request) {
        BusinessResult<ResourceTablePreposeResult> result = getTablaInfo(new ResourceTablePreposeRequest(request.getName()));
        if (result == null || result.getData() == null) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000006.getCode());
        }
        ResourceTableEntity entity = getResourceTableEntitySave(userId, request, result.getData());
        save(entity);
        TableSettingEntity tableSettingEntity = getTableSettingSaveEntity(result, entity);
        tableSettingService.save(tableSettingEntity);
        return BusinessResult.success(true);
    }

    private TableSettingEntity getTableSettingSaveEntity(BusinessResult<ResourceTablePreposeResult> result, ResourceTableEntity entity) {
        String resourceTableId = entity.getId();
        TableSettingEntity tableSettingEntity = new TableSettingEntity();
        tableSettingEntity.setId(sequenceService.nextValueString());
        tableSettingEntity.setResourceTableId(resourceTableId);
        List<Structure> structureList = result.getData().getStructureList();
        tableSettingEntity.setColumnsInfo(structureList.toString());
        //TODO:表结构信息未存储于hdfs中
        return tableSettingEntity;
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> updateResourceTable(String userId, ResourceTableUpdateRequest request) {
        ResourceTableEntity entity = BeanCopyUtils.copyProperties(request, new ResourceTableEntity());
        entity.setUpdateBy(userId);
        entity.setUpdateTime(new Date());
        return BusinessResult.success(updateById(entity));
    }

    @Override
    public BusinessResult<ResourceTableInfoResult> getResourceTableInfo(ResourceTableInfoRequest request) {
        ResourceTableEntity entity = getById(request.getId());
        ResourceTableInfoResult result = BeanCopyUtils.copyProperties(entity, new ResourceTableInfoResult());
        ResourceTablePreposeRequest preposeRequest = BeanCopyUtils.copyProperties(request, new ResourceTablePreposeRequest());
        preposeRequest.setTableName(entity.getName());
        BusinessResult<ResourceTablePreposeResult> tablaInfo = getTablaInfo(preposeRequest);
        if (tablaInfo.isSuccess() && tablaInfo.getData() != null) {
            BaseInfo baseInfo = tablaInfo.getData().getBaseInfo();
            Integer dataSize = baseInfo.getDataSize();
            result.setBaseInfo(baseInfo);
            result.setStructureList(tablaInfo.getData().getStructureList());
            result.setSampleList(tablaInfo.getData().getSampleList());
            //更新表数据量
            entity.setDataSize(dataSize);
            updateById(entity);
            result.setDataSize(dataSize);
        }
        return BusinessResult.success(result);
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> deleteResourceTable(String userId, String[] ids) {
        if (ids.length <= 0) {
            return BusinessResult.success(true);
        }
        List<ResourceTableEntity> list = new ArrayList<>();
        for (String id : ids) {
            ResourceTableEntity entity = new ResourceTableEntity();
            entity.setId(id);
            entity.setUpdateTime(new Date());
            entity.setUpdateBy(userId);
            entity.setDelFlag("Y");
            list.add(entity);
        }
        return BusinessResult.success(saveOrUpdateBatch(list));
    }

    @Override
    @BusinessParamsValidate
    public BusinessPageResult pageList(ResourceTablePageListRequest request) {
        Page<ResourceTableEntity> page = new Page<>(request.getPageNum(), request.getPageSize());
        List<ResourceTableEntity> list = resourceTableMapper.queryPage(page, request);
        return BusinessPageResult.build(page.setRecords(list), request);
    }

    @Override
    public BusinessResult<List<String>> getTablaList() {
        List<String> list = new LinkedList<>();
        Connection conn = null;
        DatabaseProperty instance = DatabaseProperty.getInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    instance.getUrl(), instance.getUsername(), instance.getPassword());
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tableResultSet = metaData.getTables(null, null, null,
                    new String[]{"TABLE", "SYSTEM TABLE", "VIEW", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM"});
            while (tableResultSet.next()) {
                String tableName = tableResultSet.getString("TABLE_NAME");
                list.add(tableName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return BusinessResult.success(list);
    }

    @Override
    public BusinessResult<ResourceTablePreposeResult> getTablaInfo(ResourceTablePreposeRequest request) {
        ResourceTablePreposeResult result = new ResourceTablePreposeResult();
        BaseInfo baseInfo = new BaseInfo();
        List<Structure> structureList = new LinkedList<>();
        Integer columnsCount = 0;
        Connection conn = null;
        DatabaseProperty instance = DatabaseProperty.getInstance();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    instance.getUrl(), instance.getUsername(), instance.getPassword());
            baseInfo.setSource(instance.getUrl());
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tableResultSet = metaData.getTables(null, null, request.getTableName(),
                    new String[]{"TABLE", "SYSTEM TABLE", "VIEW", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM"});
            while (tableResultSet.next()) {
                String tableEnglishName = request.getTableName();
                String tableChineseName = tableResultSet.getString("TABLE_CAT");
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
                result.setSampleList(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        baseInfo.setColumnsCount(columnsCount);
        result.setBaseInfo(baseInfo);
        result.setStructureList(structureList);
        return BusinessResult.success(result);
    }

    private ResourceTableEntity getResourceTableEntitySave(String userId, ResourceTableSaveRequest request, ResourceTablePreposeResult result) {
        ResourceTableEntity entity = BeanCopyUtils.copyProperties(request, new ResourceTableEntity());
        Date date = new Date();
        BaseInfo baseInfo = result.getBaseInfo();
        entity.setCreateTime(date);
        entity.setUpdateTime(date);
        entity.setCreateBy(userId);
        entity.setUpdateBy(userId);
        entity.setId(sequenceService.nextValueString());
        entity.setResourceId(request.getId());
        entity.setColumnsCount(baseInfo.getColumnsCount());
        entity.setDataSize(baseInfo.getDataSize());
        //TODO:生成与表唯一对应的url
        entity.setRequestUrl("");
        return entity;
    }
}
