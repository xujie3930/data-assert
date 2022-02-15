package com.hashtech.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.common.*;
import com.hashtech.config.validate.BusinessParamsValidate;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.entity.TableSettingEntity;
import com.hashtech.feign.vo.InternalUserInfoVO;
import com.hashtech.mapper.TableSettingMapper;
import com.hashtech.service.DatasourceSync;
import com.hashtech.service.OauthApiService;
import com.hashtech.service.ResourceTableService;
import com.hashtech.service.TableSettingService;
import com.hashtech.utils.*;
import com.hashtech.web.request.ExistInterfaceNamelRequest;
import com.hashtech.web.request.ResourceDataRequest;
import com.hashtech.web.request.ResourceTablePreposeRequest;
import com.hashtech.web.request.TableSettingUpdateRequest;
import com.hashtech.web.result.BaseInfo;
import com.hashtech.web.result.Structure;
import com.hashtech.web.result.TableSettingResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.*;
import java.util.Date;
import java.util.*;
import java.util.stream.Collectors;

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

    private static final int PAGESIZE_MAX = 500;
    private static final int MAX_IMUM = 10000;
    private static final String INTERFACE_PATH = "/resource/table/getResourceData/";
    @Autowired
    private TableSettingMapper tableSettingMapper;
    @Autowired
    private ResourceTableService resourceTableService;
    @Autowired
    private OauthApiService oauthApiService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${server.port}")
    private int serverPort;

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
        TableSettingServiceImpl tableSettingService = (TableSettingServiceImpl) AopContext.currentProxy();
        List<Structure> structureList = tableSettingService.getStructureList(resourceTableEntity.getName());
        result.setStructureList(structureList);
        //目前显示所有字段
        result.setOutParamInfo(structureList);
        if (!StringUtils.isBlank(tableSettingEntity.getParamInfo())) {
            List<String> params = Arrays.asList(tableSettingEntity.getParamInfo().split(","));
            List<Structure> paramsInfo = structureList.stream()
                    .filter((Structure s) -> params.contains(s.getFieldEnglishName()))
                    .collect(Collectors.toList());
            result.setParamInfo(paramsInfo);
        }
        result.setInterfaceName(tableSettingEntity.getInterfaceName());
        result.setUpdateTime(resourceTableEntity.getUpdateTime());
        result.setCreateTime(resourceTableEntity.getCreateTime());
        return BusinessResult.success(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @BusinessParamsValidate(argsIndexs = {1})
    public BusinessResult<Boolean> updateTableSetting(String userId, TableSettingUpdateRequest request) {
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        //更新资源表
        ResourceTableEntity resourceTableEntity = resourceTableService.getById(request.getId());
        if (Objects.isNull(resourceTableEntity)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000006.getCode());
        }
        checkExistInterfaceName(new ExistInterfaceNamelRequest(request.getInterfaceName(), request.getId()));
        resourceTableEntity.setUpdateTime(new Date());
        resourceTableEntity.setUpdateBy(user.getUsername());
        resourceTableEntity.setRequestUrl(request.getRequestUrl());
        resourceTableService.updateById(resourceTableEntity);

        //更新资源表设置
        TableSettingEntity entity = tableSettingMapper.getByResourceTableId(request.getId());
        entity.setRequestWay(request.getRequestWay());
        //生成接口地址
        String interfaceUrl = getInterfaceUrl(resourceTableEntity.getRequestUrl(), request.getParamInfo());
        entity.setExplainInfo(interfaceUrl);
        entity.setParamInfo(StringUtils.join(request.getParamInfo(), ","));
        entity.setInterfaceName(request.getInterfaceName());
        tableSettingMapper.updateById(entity);
        return BusinessResult.success(true);
    }

    private String getInterfaceUrl(String requestUrl, String[] paramInfo) {
        String innetIp = AddressUtils.getInnetIp();
        String interfaceUrl = "http://" + innetIp + ":" + serverPort + INTERFACE_PATH + requestUrl;
        if (paramInfo.length > 0) {
            StringBuilder builder = new StringBuilder("?");
            for (String param : paramInfo) {
                if (!StringUtils.isBlank(param)) {
                    builder.append(param).append("&");
                }
            }
            interfaceUrl += builder.toString();
        }
        return interfaceUrl;
    }

    @Override
    @DS("remote")
    /**
     * 调用方只需用isSuccess()方法判断，调用成功必有值，不会出现NPE
     */
    public BaseInfo getBaseInfo(ResourceTablePreposeRequest request) throws AppException {
        BaseInfo baseInfo = new BaseInfo();
        Connection conn = null;
        try {
            conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            String tableEnglishName = request.getTableName();
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
            List<Structure> structureList = getStructureList(tableEnglishName);
            baseInfo.setColumnsCount(structureList.size());
        } catch (Exception e) {
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
        return baseInfo;
    }

    @Override
    @DS("remote")
    /**
     * 调用方只需用isSuccess()方法判断，调用成功必有值，不会出现NPE
     */
    public List<Structure> getStructureList(String tableName) throws AppException {
        BaseInfo baseInfo = new BaseInfo();
        List<Structure> structureList = new LinkedList<>();
        Connection conn = null;
        try {
            conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tableResultSet = metaData.getTables(null, null, tableName,
                    new String[]{"TABLE", "SYSTEM TABLE", "VIEW", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM"});
            while (tableResultSet.next()) {
                String tableEnglishName = tableName;
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
                    structure.setType(columnType.toLowerCase());
                    // 描述
                    String remarks = columnResultSet.getString("REMARKS");
                    structure.setFieldChineseName(remarks);
                    structure.setTableEnglishName(tableEnglishName);
                    structure.setTableChineseName(tableChineseName);
                    structureList.add(structure);
                }
            }
        } catch (Exception e) {
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
        return structureList;
    }

    @Override
    @DS("remote")
    public BusinessPageResult<Object> getSampleList(ResourceTablePreposeRequest request) throws AppException {
        Connection conn = null;
        BusinessPageResult result = null;
        Long dataSize = 0L;
        try {
            conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            Statement stmt = conn.createStatement();
            //TODO:select count(*)大表有性能问题
            String getCountSql = new StringBuilder("select COUNT(*) from ").append(request.getTableName()).toString();
            ResultSet countRs = stmt.executeQuery(getCountSql);
            if (countRs.next()) {
                //rs结果集第一个参数即为记录数，且其结果集中只有一个参数
                dataSize = countRs.getLong(1);
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
                page.setTotal(dataSize);
                result = BusinessPageResult.build(page.setRecords(list), request);
                //这里按前端要求返回pageCount
                result.setPageCount(getPageCountByMaxImum(dataSize, request.getPageSize()));
            }
        } catch (Exception e) {
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
        return result;
    }

    private Long getPageCountByMaxImum(Long total, int pageSize) {
        if (total == 0L) {
            return 0L;
        } else if (total % (long) pageSize > 0L) {
            return Math.min((total / (long) pageSize + 1L), (MAX_IMUM / pageSize + 1L));
        } else {
            return Math.min((total / (long) pageSize), MAX_IMUM / pageSize);
        }
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
//            log.error("resource/table/prepose/getTablaList接口异常:{}", throwables.getMessage());
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
        String querySql = tempSql + " limit " + index + " , " + pageSize;
        try {
            conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
            Statement stmt = conn.createStatement();
            ResultSet pagingRs = stmt.executeQuery(querySql);
            if (pagingRs.next()) {
                List list = ResultSetToListUtils.convertList(pagingRs);
                return list;
            }
        } catch (SQLException throwables) {
//            log.error("/resource/table/getResourceData接口异常:{}", throwables.getMessage());
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

    @Override
    public Boolean hasExistInterfaceName(ExistInterfaceNamelRequest request) {
        return BooleanUtils.isTrue(tableSettingMapper.hasExistInterfaceName(request));
    }

    @Override
    public void updateTableSettingState(String[] ids, String delFlag) {
        tableSettingMapper.updateTableSettingState(ids, delFlag);
    }

    private void checkExistInterfaceName(ExistInterfaceNamelRequest request) {
        Boolean hasExist = hasExistInterfaceName(request);
        if (hasExist) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000028.getCode());
        }
    }
}
