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
import com.hashtech.common.StatusEnum;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.entity.TableSettingEntity;
import com.hashtech.mapper.TableSettingMapper;
import com.hashtech.service.ResourceTableService;
import com.hashtech.service.TableSettingService;
import com.hashtech.utils.JdbcUtils;
import com.hashtech.utils.RandomUtils;
import com.hashtech.utils.ResultSetToListUtils;
import com.hashtech.utils.URLProcessUtils;
import com.hashtech.web.request.ResourceDataRequest;
import com.hashtech.web.request.ResourceTablePreposeRequest;
import com.hashtech.web.request.TableSettingUpdateRequest;
import com.hashtech.web.result.BaseInfo;
import com.hashtech.web.result.ResourceTablePreposeResult;
import com.hashtech.web.result.Structure;
import com.hashtech.web.result.TableSettingResult;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Service
public class TableSettingServiceImpl extends ServiceImpl<TableSettingMapper, TableSettingEntity> implements TableSettingService {

    @Autowired
    private TableSettingMapper tableSettingMapper;
    @Autowired
    private ResourceTableService resourceTableService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final int PAGESIZE_MAX = 500;
    private static final int MAX_IMUM = 10000;

    @Override
    public BusinessResult<TableSettingResult> getTableSetting(String id) {
        TableSettingResult result = new TableSettingResult();
        ResourceTableEntity resourceTableEntity = resourceTableService.getById(id);
        if (Objects.isNull(resourceTableEntity)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000006.getCode());
        }
        result.setRequestUrl(resourceTableEntity.getRequestUrl());
        //如果url为空，则生成url返回前端，但不入库
        if (StringUtils.isBlank(resourceTableEntity.getRequestUrl())) {
            String requestUrl = RandomUtils.getRandomExcludeNumber();
            result.setRequestUrl(requestUrl);
        }
        TableSettingEntity tableSettingEntity = tableSettingMapper.getByResourceTableId(id);
        BeanCopyUtils.copyProperties(tableSettingEntity, result);
        if (!StringUtils.isBlank(tableSettingEntity.getParamInfo())) {
            result.setParamInfo(Arrays.asList(tableSettingEntity.getParamInfo().split(",")));
        }
        if (!StringUtils.isBlank(tableSettingEntity.getColumnsInfo())) {
            List<Structure> structureList = JSONObject.parseArray(tableSettingEntity.getColumnsInfo(), Structure.class);
            result.setStructureList(structureList);
        }
        return BusinessResult.success(result);
    }

    @Override
    @BusinessParamsValidate(argsIndexs = {1})
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> updateTableSetting(String userId, TableSettingUpdateRequest request) {
        //更新资源表
        ResourceTableEntity resourceTableEntity = resourceTableService.getById(request.getId());
        if (Objects.isNull(resourceTableEntity)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000006.getCode());
        }
        if (!StatusEnum.ENABLE.getCode().equals(resourceTableEntity.getState())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000020.getCode());
        }
        resourceTableEntity.setUpdateTime(new Date());
        resourceTableEntity.setUpdateBy(userId);
        resourceTableEntity.setRequestUrl(request.getRequestUrl());
        resourceTableService.updateById(resourceTableEntity);

        //更新资源表设置
        TableSettingEntity entity = tableSettingMapper.getByResourceTableId(request.getId());
        entity.setRequestWay(request.getRequestWay());
        entity.setExplainInfo(request.getExplainInfo());
        entity.setParamInfo(StringUtils.join(request.getParamInfo(), ","));
        return BusinessResult.success(tableSettingMapper.updateById(entity) == 1);
    }

    @Override
    @DS("remote")
    /**
     * 调用方只需用isSuccess()方法判断，调用成功必有值，不会出现NPE
     */
    public BusinessResult<ResourceTablePreposeResult> getTablaInfo(ResourceTablePreposeRequest request) {
        ResourceTablePreposeResult result = new ResourceTablePreposeResult();
        BaseInfo baseInfo = new BaseInfo();
        List<Structure> structureList = new LinkedList<>();
        Integer columnsCount = 0;
        Connection conn = null;
        try {
            conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tableResultSet = metaData.getTables(null, null, request.getTableName(),
                    new String[]{"TABLE", "SYSTEM TABLE", "VIEW", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM"});
            while (tableResultSet.next()) {
                String tableEnglishName = request.getTableName();
                String tableChineseName = JdbcUtils.getCommentByTableName(tableEnglishName, conn);
                baseInfo.setDescriptor(tableChineseName);
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
            String getCountSql = new StringBuilder("select COUNT(*) from ").append(request.getTableName()).toString();
            Statement stmt = conn.createStatement();
            ResultSet countRs = stmt.executeQuery(getCountSql);
            if (countRs.next()) {
                //rs结果集第一个参数即为记录数，且其结果集中只有一个参数
                baseInfo.setDataSize(countRs.getLong(1));
            }
            //只展示前10000条数据
            int pageNum = Math.min(request.getPageNum(), MAX_IMUM / request.getPageSize());
            int index = (pageNum - 1) * request.getPageSize();
            String pagingData = new StringBuilder("select * from ").append(request.getTableName())
                    .append(" limit ").append(index).append(" , ").append(request.getPageSize()).toString();
            ResultSet pagingRs = stmt.executeQuery(pagingData);
            if (pagingRs.next()) {
                List list = ResultSetToListUtils.convertList(pagingRs);
                Page<Object> page = new Page<>(pageNum, request.getPageSize());
                page.setTotal(baseInfo.getDataSize());
                request.setPageNum(Math.min(pageNum,  MAX_IMUM / request.getPageSize()));
                result.setSampleList(BusinessPageResult.build(page.setRecords(list), request));
            }
        } catch (Exception e) {
            log.error("获取表信息失败:{}", e.getMessage());
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000009.getCode(), e.getMessage());
        } finally {
            if (null != conn) {
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
    public BusinessResult<List<Map<String, String>>> getTablaList() {
        List<Map<String, String>> maps = new LinkedList<>();
        List<Map<String, Object>> tableMaps;
        Connection conn = null;
        try {
            conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            String schemaName = conn.getCatalog();
            String tableNameListSql = String.format("select table_name,table_comment from information_schema.tables where table_schema='%s'", schemaName);
            tableMaps = jdbcTemplate.queryForList(tableNameListSql);
        } catch (SQLException throwables) {
            log.error("resource/table/prepose/getTablaList接口异常:{}", throwables.getMessage());
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000009.getCode());
        } finally {
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        if (CollectionUtils.isEmpty(tableMaps)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000010.getCode());
        }
        for (Map<String, Object> m : tableMaps) {
            Map<String, String> map = new LinkedHashMap<>();
            map.put("tableName", (String) m.get("table_name"));
            map.put("comment", (String) m.get("table_comment"));
            maps.add(map);
        }
        return BusinessResult.success(maps);
    }

    @Override
    @DS("remote")
    public List<Object> getResourceData(ResourceDataRequest request, ResourceTableEntity entity) {
        Connection conn = null;
        //拼接查询sql
        StringBuilder builder = new StringBuilder("select * from " + entity.getName() + " where ");
        //解析请求参数
        if (!CollectionUtils.isEmpty(request.getParams())) {
            for (Map.Entry<String, Object> entry : request.getParams().entrySet()) {
                builder.append(entry.getKey()).append(" = ");
                builder.append(entry.getValue()).append(" and ");
            }
        }
        String tempSql = builder.toString();
        //可能URL中也会携带有参数信息
        List<Map<String, Object>> paramList = URLProcessUtils.getParamList(request.getRequestUrl());
        if (!CollectionUtils.isEmpty(paramList)) {
            StringBuilder newBuilder = new StringBuilder(tempSql);
            for (Map<String, Object> map : paramList) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    newBuilder.append(entry.getKey()).append(" = ");
                    newBuilder.append(entry.getValue()).append(" and ");
                }
            }
            tempSql = newBuilder.toString();
        }

        tempSql = tempSql.substring(0, tempSql.lastIndexOf("and"));
        int pageSize = Math.min(request.getPageSize(), PAGESIZE_MAX);
        int index = (request.getPageNum() - 1) * pageSize;
        int end = index + pageSize;
        String querySql = tempSql + " limit " + index + " , " + end;
        try {
            conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            Statement stmt = conn.createStatement();
            ResultSet pagingRs = stmt.executeQuery(querySql);
            if (pagingRs.next()) {
                List list = ResultSetToListUtils.convertList(pagingRs);
                return list;
            }
        } catch (SQLException throwables) {
            log.error("/resource/table/getResourceData接口异常:{}", throwables.getMessage());
            throwables.printStackTrace();
        } finally {
            if (null != conn) {
                try {
                    conn.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return Collections.emptyList();
    }
}
