package com.hashtech.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.common.*;
import com.hashtech.config.validate.BusinessParamsValidate;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.entity.TableSettingEntity;
import com.hashtech.factory.DatasourceFactory;
import com.hashtech.factory.DatasourceSync;
import com.hashtech.feign.DataApiFeignClient;
import com.hashtech.feign.DatasourceFeignClient;
import com.hashtech.feign.ServeFeignClient;
import com.hashtech.feign.request.*;
import com.hashtech.feign.result.*;
import com.hashtech.feign.vo.InternalUserInfoVO;
import com.hashtech.mapper.ResourceTableMapper;
import com.hashtech.mapper.TableSettingAppsMapper;
import com.hashtech.mapper.TableSettingMapper;
import com.hashtech.service.OauthApiService;
import com.hashtech.service.ResourceTableService;
import com.hashtech.service.RomoteDataSourceService;
import com.hashtech.service.TableSettingService;
import com.hashtech.service.bo.TableFieldsBO;
import com.hashtech.utils.AddressUtils;
import com.hashtech.utils.RandomUtils;
import com.hashtech.utils.druid.DataApiDruidDataSourceService;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    private static final int MAX_IMUM = 10000;
    private static final String INTERFACE_PATH = "/resource/table/getResourceData/";
    private static final String DEFAULT_RESP_INFO = "*";
    @Resource
    private TableSettingMapper tableSettingMapper;
    @Resource
    private ResourceTableService resourceTableService;
    @Resource
    private OauthApiService oauthApiService;
    @Resource
    private DatasourceFeignClient datasourceFeignClient;
    @Resource
    private RomoteDataSourceService romoteDataSourceService;
    @Value("${server.port}")
    private int serverPort;
    @Resource
    private ResourceTableMapper resourceTableMapper;
    @Resource
    private DataApiFeignClient dataApiFeignClient;
    @Resource
    private TableSettingAppsMapper tableSettingAppsMapper;
    @Resource
    private ServeFeignClient serveFeignClient;

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
        if (StringUtils.isEmpty(tableSettingEntity.getRespInfo()) || DEFAULT_RESP_INFO.equals(tableSettingEntity.getRespInfo())) {//所有字段
            result.setOutParamInfo(structureList);
        } else {
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
        //查询应用列表
        BusinessResult<List<AuthListResult>> auths = dataApiFeignClient.queryApiAuthListByPath(result.getRequestUrl());
        /*List<TableSettingAppsEntity> tableSettingApps = tableSettingAppsMapper.queryByTableSettingId(id);
        List<TableSettingAppsResult> appList = new ArrayList<>();
        if(null!=tableSettingApps && !tableSettingApps.isEmpty()){
            tableSettingApps.stream().forEach(tableSettingApp->{
                TableSettingAppsResult app = new TableSettingAppsResult();
                app.setAppGroupId(tableSettingApp.getAppGroupId());
                app.setAppId(tableSettingApp.getAppId());
                appList.add(app);
            });
        }*/
        List<String[]> appList = new ArrayList<>();
        AppAuthResult authResult = new AppAuthResult();
        //赋默认值
        authResult.setCallCountType(1);
        authResult.setAuthEffectiveTime(1);
        //authResult.setAllowCall(0);
        if(null!=auths && null!=auths.getData() && !auths.getData().isEmpty()){
            auths.getData().stream().forEach(auth->{
                String appId = auth.getAppId();
                String appGroupId = auth.getAppGroupId();
                String[] appArr = {appGroupId, appId};
                appList.add(appArr);
            });
            AuthListResult auth = auths.getData().get(auths.getData().size()-1);
            authResult.setAllowCall(auth.getAuthAllowCall());
            authResult.setAuthEffectiveTime(null!=auth.getAuthPeriodBegin() && auth.getAuthPeriodBegin().longValue()>-1?0:1);
            authResult.setCallCountType(null!=auth.getAuthAllowCall()&&auth.getAuthAllowCall().intValue()>-1?0:1);
            authResult.setPeriodEnd(auth.getAuthPeroidEnd());
            authResult.setPeriodBegin(auth.getAuthPeriodBegin());
            authResult.setTokenType(auth.getAppTokenType());
        }
        result.setAuthResult(authResult);
        result.setAppList(appList);
        result.setInterfaceName(tableSettingEntity.getInterfaceName());
        result.setUpdateTime(resourceTableEntity.getUpdateTime());
        result.setCreateTime(resourceTableEntity.getCreateTime());
        result.setRequestWay(tableSettingEntity.getRequestWay());
        return BusinessResult.success(result);
    }

    private void handleReqParamInfo(String reqParamStr, List<Structure> structureList) {
        List<String> reqParamList = Arrays.asList(reqParamStr.split(","));
        for (Structure structure : structureList) {
            for (String reqParam : reqParamList) {
                if (reqParam.equals(structure.getFieldEnglishName())) {
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
                if (respParam.equals(structure.getFieldEnglishName())) {
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
        if (StringUtils.isNotBlank(request.getInterfaceName())){
            checkExistInterfaceName(new ExistInterfaceNamelRequest(request.getInterfaceName(), request.getId()));
        }
        resourceTableEntity.setUpdateTime(new Date());
        resourceTableEntity.setUpdateBy(user.getUsername());
        resourceTableEntity.setRequestUrl(request.getRequestUrl());
        resourceTableService.updateById(resourceTableEntity);

        //更新资源表设置
        TableSettingEntity entity = tableSettingMapper.getByResourceTableId(request.getId());
        if (entity.getRequestWay() == null) {
            entity.setRequestWay(request.getRequestWay());
        }
        if (!request.getRequestWay().equals(entity.getRequestWay())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000049.getCode());
        }
        entity.setParamInfo(StringUtils.join(request.getParamInfo(), ","));
        entity.setRespInfo(StringUtils.join(request.getRespInfo(), ","));
        entity.setInterfaceName(request.getInterfaceName());
        String desc = "";
        BusinessResult<ApiSaveResult> result;
        //这里组装参数,调用数据服务
        DatasourceApiSaveRequest dataApiRequest = getDatasourceApiSaveRequest(request, resourceTableEntity, entity);
        //更新APP关联信息
        if(null!=request.getAppList()){
            AuthSaveApiRequest saveApi = new AuthSaveApiRequest();
            List<String[]> appList = request.getAppList();
            if(appList.isEmpty()){
                //为空，表示取消所有应用授权
                //给一个空集合，防止空指针异常
                saveApi.setAppIds(new HashSet<>(0));
            }else{
                if(null==request.getAuthResult()){
                    throw new AppException("auth result is null");
                }
                Set<String> appIds = new HashSet<>();
                appList.stream().forEach(app->{
                    appIds.add(app[1]);
                });
                saveApi.setAppIds(appIds);
                saveApi.setAllowCall(request.getAuthResult().getAllowCall());
                saveApi.setTokenType(request.getAuthResult().getTokenType());
                saveApi.setPeriodBegin(request.getAuthResult().getPeriodBegin());
                saveApi.setPeriodEnd(request.getAuthResult().getPeriodEnd());
            }

            saveApi.setApiSaveRequest(dataApiRequest);
            result = dataApiFeignClient.saveOuterApi(userId, saveApi);//为避免出现分布式bug，dataApiFeignClient.createAndPublish操作整合到saveOuterApi内部完成
        }else{
            result = dataApiFeignClient.createAndPublish(userId, dataApiRequest);
        }
        if (!result.isSuccess() || Objects.isNull(result.getData())) {
            throw new AppException(result.getReturnCode());
        }
        desc = result.getData().getDesc();
        //数据服务获取接口地址
        entity.setExplainInfo(desc);
        tableSettingMapper.updateById(entity);
        serveFeignClient.asyncSourceDirInfo(request.getId());
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
        dataApiRequest.setRequestType(RquestWayEnum.find(entity.getRequestWay()).getDesc());
        dataApiRequest.setResponseType("JSON");
        dataApiRequest.setPath(resourceTableEntity.getRequestUrl());
        dataApiRequest.setDesc(entity.getExplainInfo());
        DatasourceApiGenerateSaveRequest apiGenerateSaveRequest = new DatasourceApiGenerateSaveRequest();
        apiGenerateSaveRequest.setModel(0);
        apiGenerateSaveRequest.setDatasourceId(resourceTableEntity.getDatasourceId());
        apiGenerateSaveRequest.setTableName(resourceTableEntity.getName());
        DatasourceDetailResult datasource = romoteDataSourceService.getDatasourceDetail(resourceTableEntity.getDatasourceId());
        apiGenerateSaveRequest.setDatabaseType(datasource.getType());
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
            if (params.contains(fieldName)) {
                save.setRequired(0);
                save.setIsRequest(0);
            }
            save.setDesc(structure.getFieldChineseName());
            if (resps.contains(fieldName)) {
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
        try (Connection conn = DataApiDruidDataSourceService.getInstance()
                .getOrCreateConnectionWithoutUsername(uri, type)){
            String tableEnglishName = request.getTableName();
            DatasourceSync factory = DatasourceFactory.getDatasource(type);
            baseInfo = factory.getBaseInfoByType(request, baseInfo, datasource, conn, tableEnglishName);
            return baseInfo;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("表基本信息接口异常：", e);
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000009.getCode());
        }
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
        try (Connection conn = DataApiDruidDataSourceService.getInstance()
                .getOrCreateConnectionWithoutUsername(uri, datasource.getType())){
            String schema = DatasourceSync.getSchema(uri);
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tableResultSet = metaData.getTables(null, schema, request.getTableName(),
                    new String[]{"TABLE", "SYSTEM TABLE", "VIEW", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM"});
            while (tableResultSet.next()) {
                String tableEnglishName = request.getTableName();
                String tableChineseName = tableResultSet.getString("REMARKS");
                baseInfo.setDescriptor(tableChineseName);
                baseInfo.setName(tableEnglishName);
                ResultSet columnResultSet = metaData.getColumns(null, schema, tableEnglishName, "%");
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
        }
        return structureList;
    }

    @Override
    public BusinessPageResult<Object> getSampleList(ResourceTablePreposeRequest request) throws AppException {
        //只展示前10000条数据
        DatasourceDetailResult datasource = romoteDataSourceService.getDatasourceDetail(request.getDatasourceId());
        String uri = datasource.getUri();
        DatasourceSync factory = DatasourceFactory.getDatasource(datasource.getType());
        BusinessPageResult result = null;
        try (Connection conn = DataApiDruidDataSourceService.getInstance()
                .getOrCreateConnectionWithoutUsername(uri, datasource.getType())){
            result = factory.getSampleList(conn, request, datasource);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("采样数据接口异常：", e);
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000009.getCode());
        }
        return result;
    }

    @Override
    public BusinessPageResult<Object> getResourceDataList(ResourceTablePreposeRequest request, String fields) throws AppException {
        //只展示前10000条数据
        ResourceTableEntity resourceTableEntity = resourceTableMapper.getByDatasourceIdAndName(new ResourceTableNameRequest(request.getTableName(), request.getDatasourceId()));
        if (!Objects.isNull(resourceTableEntity)) {
            TableSettingEntity tableSettingEntity = tableSettingMapper.getByResourceTableId(resourceTableEntity.getId());
            request.setDesensitizeFields(null == tableSettingEntity ? "" : tableSettingEntity.getDesensitizeFields());
        }
        request.setFields(fields);
        //只支持前10条数据
        request.setPageNum(1);
        request.setPageSize(10);
        return getSampleList(request);
    }

    @Override
    public TableFieldsBO getTableColumnChineseName(String tableName, String datasourceId) {
        TableFieldsBO tableFieldsBO = new TableFieldsBO();
        DatasourceDetailResult datasource = romoteDataSourceService.getDatasourceDetail(datasourceId);
        String uri = datasource.getUri();
        List<String> columnNameList = new LinkedList<>();
        ResourceTableEntity resourceTableEntity = resourceTableMapper.getByDatasourceIdAndName(new ResourceTableNameRequest(tableName, datasourceId));
        StringBuilder fieldSb = new StringBuilder();
        String fields = DEFAULT_RESP_INFO;
        if (!Objects.isNull(resourceTableEntity)) {
            TableSettingEntity tableSettingEntity = tableSettingMapper.getByResourceTableId(resourceTableEntity.getId());
            if (!Objects.isNull(tableSettingEntity) && StringUtils.isNotBlank(tableSettingEntity.getRespInfo())) {
                fields = tableSettingEntity.getRespInfo();
            }
        }
        String[] fieldArr = null;
        if (!DEFAULT_RESP_INFO.equals(fields)) {
            fieldArr = fields.split(",");
        }
        try (Connection conn = DataApiDruidDataSourceService.getInstance()
                .getOrCreateConnectionWithoutUsername(uri, datasource.getType())){
            ResultSet rs = conn.getMetaData().getColumns(conn.getCatalog(), null, tableName, "%");
            while (rs.next()) {
                if (null == fieldArr && DEFAULT_RESP_INFO.equals(fields)) {
                    columnNameList.add(rs.getString(12));
                    fieldSb.append(rs.getString(4)).append(",");
                } else {
                    for (String field : fieldArr) {
                        if (rs.getString(4).equals(field)) {
                            columnNameList.add(rs.getString(12));
                            fieldSb.append(rs.getString(4)).append(",");
                        }
                    }
                }
            }
            tableFieldsBO.setFieldChineseNameList(columnNameList);
            fields = String.valueOf(fieldSb);
            tableFieldsBO.setFields(fields.substring(0, fields.lastIndexOf(",")));
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("采样数据接口异常：", e);
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000009.getCode());
        }
        return tableFieldsBO;
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

    @Override
    public List<AppGroupListResult> getAppGroups() {
        AppQueryListRequest appQueryListRequest = new AppQueryListRequest();
        appQueryListRequest.setIsEnable(1);//只返回已启用应用
        BusinessResult<List<AppGroupListResult>> feignResult = dataApiFeignClient.appGroupList(null, appQueryListRequest);
        return (null!=feignResult&&feignResult.isSuccess()&&null!=feignResult.getData()?feignResult.getData():new ArrayList<>(0));
    }

    /*@Override
    public BusinessResult<Boolean> appAuthSave(String userId, AppAuthSaveRequest request) {
        return dataApiFeignClient.appAuthSave(userId, request);
    }

    @Override
    public AppAuthInfoResult appAuthInfo(AppAuthInfoRequest request) {
        BusinessResult<AppAuthInfoResult> result = dataApiFeignClient.appAuthInfo(request);
        if (null==result || !result.isSuccess() || null==result.getData()) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000043.getCode());
        }
        return result.getData();
    }*/
}