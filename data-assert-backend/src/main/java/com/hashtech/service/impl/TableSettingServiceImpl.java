package com.hashtech.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.common.*;
import com.hashtech.config.validate.BusinessParamsValidate;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.entity.TableSettingEntity;
import com.hashtech.feign.DataApiFeignClient;
import com.hashtech.feign.DatasourceFeignClient;
import com.hashtech.feign.request.DatasourceApiGenerateSaveRequest;
import com.hashtech.feign.request.DatasourceApiParamSaveRequest;
import com.hashtech.feign.request.DatasourceApiSaveRequest;
import com.hashtech.feign.result.ApiSaveResult;
import com.hashtech.feign.result.DatasourceDetailResult;
import com.hashtech.feign.vo.InternalUserInfoVO;
import com.hashtech.mapper.ResourceTableMapper;
import com.hashtech.mapper.TableSettingMapper;
import com.hashtech.service.OauthApiService;
import com.hashtech.service.ResourceTableService;
import com.hashtech.service.RomoteDataSourceService;
import com.hashtech.service.TableSettingService;
import com.hashtech.utils.*;
import com.hashtech.web.request.*;
import com.hashtech.web.result.BaseInfo;
import com.hashtech.web.result.Structure;
import com.hashtech.web.result.TableSettingResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(TableSettingServiceImpl.class);
    private static final int PAGESIZE_MAX = 500;
    private static final int MAX_IMUM = 10000;
    private static final String INTERFACE_PATH = "/resource/table/getResourceData/";
    private static final String REQ_PARAM = "req";
    private static final String RESP_PARAM = "resp";
    @Autowired
    private TableSettingMapper tableSettingMapper;
    @Autowired
    private ResourceTableService resourceTableService;
    @Autowired
    private OauthApiService oauthApiService;
    @Autowired
    private DatasourceFeignClient datasourceFeignClient;
    @Autowired
    private RomoteDataSourceService romoteDataSourceService;
    @Value("${server.port}")
    private int serverPort;
    private static final String DEFAULT_RESP_INFO = "*";
    @Autowired
    private ResourceTableMapper resourceTableMapper;
    @Autowired
    private DataApiFeignClient dataApiFeignClient;

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
        List<Structure> structureList = tableSettingService.getStructureList(new ResourceTableNameRequest(resourceTableEntity.getName(), resourceTableEntity.getDatasourceId()));
        result.setStructureList(structureList);
        if(StringUtils.isEmpty(tableSettingEntity.getRespInfo()) || DEFAULT_RESP_INFO.equals(tableSettingEntity.getRespInfo())){//所有字段
            result.setOutParamInfo(structureList);
        }else{
            List<String> resps = Arrays.asList(tableSettingEntity.getRespInfo().split(","));
            List<Structure> respInfoList = structureList.stream()
                    .filter((Structure s) -> resps.contains(s.getFieldEnglishName()))
                    .collect(Collectors.toList());
            result.setOutParamInfo(respInfoList);
            //对勾选的返回参数进行设置
            handleRespParamInfo(tableSettingEntity.getRespInfo(), structureList);
        }
        if (!StringUtils.isBlank(tableSettingEntity.getParamInfo())) {
            List<String> params = Arrays.asList(tableSettingEntity.getParamInfo().split(","));
            List<Structure> paramsInfo = structureList.stream()
                    .filter((Structure s) -> params.contains(s.getFieldEnglishName()))
                    .collect(Collectors.toList());
            result.setParamInfo(paramsInfo);
            //对勾选的请求参数进行设置
            handleReqParamInfo(tableSettingEntity.getParamInfo(), structureList);
        }
        result.setInterfaceName(tableSettingEntity.getInterfaceName());
        result.setUpdateTime(resourceTableEntity.getUpdateTime());
        result.setCreateTime(resourceTableEntity.getCreateTime());
        result.setRequestWay(RquestWayEnum.GET.getCode());
        return BusinessResult.success(result);
    }

    private void handleReqParamInfo(String reqParamStr, List<Structure> structureList) {
        List<String> reqParamList = Arrays.asList(reqParamStr.split(","));
        for (Structure structure : structureList) {
            for (String reqParam : reqParamList) {
                if(reqParam.equals(structure.getFieldEnglishName())){
                    structure.setReqParam(StateEnum.YES.ordinal());
                    break;
                }
            }
        }
    }

    private void handleRespParamInfo(String respParamStr, List<Structure> structureList) {
        List<String> respParamList = Arrays.asList(respParamStr.split(","));
        for (Structure structure : structureList) {
            structure.setResParam(StateEnum.NO.ordinal());
            for (String respParam : respParamList) {
                if(respParam.equals(structure.getFieldEnglishName())) {
                    structure.setResParam(StateEnum.YES.ordinal());
                    break;
                }
            }
        }
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
        entity.setParamInfo(StringUtils.join(request.getParamInfo(), ","));
        entity.setRespInfo(StringUtils.join(request.getRespInfo(), ","));
        entity.setInterfaceName(request.getInterfaceName());
        //这里组装参数,调用数据服务
        DatasourceApiSaveRequest dataApiRequest = getDatasourceApiSaveRequest(request, resourceTableEntity, entity);
        BusinessResult<ApiSaveResult> result = dataApiFeignClient.createAndPublish(userId, dataApiRequest);
        if (!result.isSuccess() || Objects.isNull(result.getData())){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000039.getCode());
        }
        //数据服务获取接口地址
        entity.setExplainInfo(result.getData().getDesc());
        tableSettingMapper.updateById(entity);
        return BusinessResult.success(true);
    }

    @NotNull
    private DatasourceApiSaveRequest getDatasourceApiSaveRequest(TableSettingUpdateRequest request, ResourceTableEntity resourceTableEntity, TableSettingEntity entity) {
        DatasourceApiSaveRequest dataApiRequest = new DatasourceApiSaveRequest();
        dataApiRequest.setSaveType(0);
        //默认mysql
        dataApiRequest.setType(1);
        dataApiRequest.setName(request.getInterfaceName());
        //0-POST,1-GET
        dataApiRequest.setRequestType("GET");
        dataApiRequest.setResponseType("JSON");
        dataApiRequest.setPath(resourceTableEntity.getRequestUrl());
        dataApiRequest.setDesc(entity.getExplainInfo());
        DatasourceApiGenerateSaveRequest apiGenerateSaveRequest = new DatasourceApiGenerateSaveRequest();
        apiGenerateSaveRequest.setModel(0);
        apiGenerateSaveRequest.setDatasourceId(resourceTableEntity.getDatasourceId());
        apiGenerateSaveRequest.setTableName(resourceTableEntity.getName());
        dataApiRequest.setApiGenerateSaveRequest(apiGenerateSaveRequest);
        List<Structure> structureList = getStructureList(new ResourceTableNameRequest(resourceTableEntity.getName(), resourceTableEntity.getDatasourceId()));
        Set<String> params = new HashSet<>(Arrays.asList(request.getParamInfo()));
        Set<String> resps = new HashSet<>(Arrays.asList(request.getRespInfo()));
        List<DatasourceApiParamSaveRequest> paramList = new LinkedList<>();
        for (Structure structure : structureList) {
            DatasourceApiParamSaveRequest save = new DatasourceApiParamSaveRequest();
            String fieldName = structure.getFieldEnglishName();
            save.setFieldName(fieldName);
            save.setFieldType(structure.getType());
            //看请求参数
            if (params.contains(fieldName)){
                save.setRequired(0);
                save.setIsRequest(0);
            }
            save.setDesc(structure.getFieldChineseName());
            if (resps.contains(fieldName)){
                save.setIsResponse(0);
            }
            paramList.add(save);
        }
        dataApiRequest.setApiParamSaveRequestList(paramList);
        return dataApiRequest;
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
    /**
     * 调用方只需用isSuccess()方法判断，调用成功必有值，不会出现NPE
     */
    public BaseInfo getBaseInfo(ResourceTablePreposeRequest request) throws AppException {
        BaseInfo baseInfo = new BaseInfo();
        DatasourceDetailResult datasource = romoteDataSourceService.getDatasourceDetail(request.getDatasourceId());
        String uri = datasource.getUri();
        Integer type = datasource.getType();
        Connection conn = DBConnectionManager.getInstance().getConnection(uri, type);
        if(conn == null){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_10000003.getCode());
        }
        try {
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
            List<Structure> structureList = getStructureList(new ResourceTableNameRequest(request.getTableName(), request.getDatasourceId()));
            baseInfo.setColumnsCount(structureList.size());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("表基本信息接口异常：", e);
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000009.getCode());
        }finally {
            DBConnectionManager.getInstance().freeConnection(uri, conn);
        }
        return baseInfo;
    }

    @Override
    /**
     * 调用方只需用isSuccess()方法判断，调用成功必有值，不会出现NPE
     */
    public List<Structure> getStructureList(ResourceTableNameRequest request) throws AppException {
        BaseInfo baseInfo = new BaseInfo();
        List<Structure> structureList = new LinkedList<>();
        DatasourceDetailResult datasource = romoteDataSourceService.getDatasourceDetail(request.getDatasourceId());
        String uri = datasource.getUri();
        Connection conn = DBConnectionManager.getInstance().getConnection(uri, datasource.getType());
        if(conn == null){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_10000003.getCode());
        }
        try {
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
            LOGGER.error("表结构接口异常：", e);
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000009.getCode());
        } finally {
            DBConnectionManager.getInstance().freeConnection(uri, conn);
        }
        return structureList;
    }

    @Override
    public BusinessPageResult<Object> getSampleList(ResourceTablePreposeRequest request) throws AppException {
        //只展示前10000条数据
        int pageNum = Math.min(request.getPageNum(), MAX_IMUM / request.getPageSize());
        int index = (pageNum - 1) * request.getPageSize();
        String pagingData = new StringBuilder("select * from ").append(request.getTableName())
                .append(" limit ").append(index).append(" , ").append(request.getPageSize()).toString();
        return getDataBySql(request, pagingData, pageNum, "");
    }

    private BusinessPageResult<Object> getDataBySql(ResourceTablePreposeRequest request, String sql, int pageNum, String desensitizeFields){
        DatasourceDetailResult datasource = romoteDataSourceService.getDatasourceDetail(request.getDatasourceId());
        String uri = datasource.getUri();
        Connection conn = DBConnectionManager.getInstance().getConnection(uri, datasource.getType());
        if(conn == null){
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_10000003.getCode());
        }
        BusinessPageResult result = null;
        Long dataSize = 0L;
        try {
            Statement stmt = conn.createStatement();
            //TODO:select count(*)大表有性能问题
            String getCountSql = new StringBuilder("select COUNT(*) from ").append(request.getTableName()).toString();
            ResultSet countRs = stmt.executeQuery(getCountSql);
            if (countRs.next()) {
                //rs结果集第一个参数即为记录数，且其结果集中只有一个参数
                dataSize = countRs.getLong(1);
            }
            ResultSet pagingRs = stmt.executeQuery(sql);
            if (pagingRs.next()) {
                List list = ResultSetToListUtils.convertList(pagingRs, desensitizeFields);
                Page<Object> page = new Page<>(pageNum, request.getPageSize());
                page.setTotal(dataSize);
                result = BusinessPageResult.build(page.setRecords(list), request);
                //这里按前端要求返回pageCount
                result.setPageCount(getPageCountByMaxImum(dataSize, request.getPageSize()));
            }
        }catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("采样数据接口异常：", e);
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000009.getCode());
        } finally {
            DBConnectionManager.getInstance().freeConnection(uri, conn);
        }
        return result;
    }

    @Override
    public BusinessPageResult<Object> getResourceDataList(ResourceTablePreposeRequest request) throws AppException {
        //只展示前10000条数据
        int pageNum = Math.min(request.getPageNum(), MAX_IMUM / request.getPageSize());
        int index = (pageNum - 1) * request.getPageSize();
        String fields = "*";
        TableSettingEntity tableSettingEntity = null;
        ResourceTableEntity resourceTableEntity = resourceTableMapper.getByDatasourceIdAndName(new ResourceTableNameRequest(request.getTableName(), request.getDatasourceId()));
        if (!Objects.isNull(resourceTableEntity)){
            tableSettingEntity = tableSettingMapper.getByResourceTableId(resourceTableEntity.getId());
            if (!Objects.isNull(tableSettingEntity) && StringUtils.isNotBlank(tableSettingEntity.getRespInfo())){
                fields = tableSettingEntity.getRespInfo();
            }
        }
        String pagingData = new StringBuilder("select ").append(fields).append(" from ").append(request.getTableName())
                .append(" limit 10").toString();
        return getDataBySql(request, pagingData, pageNum, null == tableSettingEntity ? "" : tableSettingEntity.getDesensitizeFields());
    }

    public List<String> getTableColumnChineseName(String tableName, String datasourceId) {
        DatasourceDetailResult datasource = romoteDataSourceService.getDatasourceDetail(datasourceId);
        String uri = datasource.getUri();
        Connection conn = DBConnectionManager.getInstance().getConnection(uri, datasource.getType());
        List<String> columnNameList = new LinkedList<>();
        try {
            ResultSet rs = conn.getMetaData().getColumns(null, null, tableName, "%");
            while(rs.next()) {
                columnNameList.add(rs.getString(12));
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("采样数据接口异常：", e);
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000009.getCode());
        } finally {
            DBConnectionManager.getInstance().freeConnection(uri, conn);
        }
        return columnNameList;
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
    public BusinessResult<List<Map<String, String>>> getTablaList(TableNameListRequest request) {
        return datasourceFeignClient.getTableNameList(new TableNameListRequest(request.getId()));
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