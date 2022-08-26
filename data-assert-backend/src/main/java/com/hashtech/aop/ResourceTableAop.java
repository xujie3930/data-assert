package com.hashtech.aop;

import com.alibaba.fastjson.JSON;
import com.hashtech.common.BusinessResult;
import com.hashtech.feign.ServeFeignClient;
import com.hashtech.feign.result.AppAuthResult;
import com.hashtech.service.ServeService;
import com.hashtech.service.TableSettingService;
import com.hashtech.service.impl.ServeServiceImpl;
import com.hashtech.web.request.ResourceTableUpdateRequest;
import com.hashtech.web.request.TableSettingUpdateRequest;
import com.hashtech.web.result.Structure;
import com.hashtech.web.result.TableSettingResult;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class ResourceTableAop {

    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private TableSettingService tableSettingService;
    @Resource
    private ServeFeignClient serveFeignClient;
    @Autowired
    private ApplicationContext applicationContext;
    @Resource
    private ServeService serveService;

    @Pointcut("execution(* com.hashtech.service.ResourceTableService.updateResourceTable(..))")
    public void updateResourceTablePointcut() {
    }

    @AfterReturning(value = "updateResourceTablePointcut()", returning = "result")
    public void updateResourceTableAfter(JoinPoint joinPoint, Object result){
        logger.info("ResourceTableAop updateResourceTableAfter start");
        final Object[] args = joinPoint.getArgs();
        final BusinessResult<Boolean> businessResult = (BusinessResult<Boolean>) result;
        if(null==businessResult || null==businessResult.getData() || !businessResult.getData().booleanValue()){
            logger.error("updateResourceTableAfter error, businessResult is false;", businessResult==null?"{}":JSON.toJSONString(businessResult));
            return;//方法没成功
        }
        String userId = (String) args[0];
        ResourceTableUpdateRequest request = (ResourceTableUpdateRequest) args[1];
        String id = request.getId();
        BusinessResult<TableSettingResult> tableSettingResult = tableSettingService.getTableSetting(id);
        if(null==tableSettingResult || null==tableSettingResult.getData() || StringUtils.isEmpty(tableSettingResult.getData().getInterfaceName())){
            logger.error("updateResourceTableAfter error, tableSettingInfoResult or interfaceName is null;", null==tableSettingResult?"{}":JSON.toJSONString(tableSettingResult));
            return;
        }
        TableSettingResult tableSetting = tableSettingResult.getData();
        String explainInfo = tableSetting.getExplainInfo();
        Integer formats = tableSetting.getFormats();
        String interfaceName = tableSetting.getInterfaceName();
        String requestUrl = tableSetting.getRequestUrl();
        Integer requestWay = tableSetting.getRequestWay();
        List<Structure> paramInfoList = tableSetting.getParamInfo();
        List<Structure> outParamInfoList = tableSetting.getOutParamInfo();
        List<String[]> appList = tableSetting.getAppList();
        AppAuthResult authResult = tableSetting.getAuthResult();
        TableSettingUpdateRequest updateRequest = new TableSettingUpdateRequest();

        updateRequest.setId(id);
        updateRequest.setExplainInfo(explainInfo);
        updateRequest.setRequestUrl(requestUrl);
        updateRequest.setRequestWay(requestWay);
        updateRequest.setInterfaceName(interfaceName);
        updateRequest.setAppList(appList);
        updateRequest.setAuthResult(authResult);
        updateRequest.setFormats(tableSetting.getFormats());
        String[] paramInfos = new String[0], respInfos = new String[0];
        if(null!=paramInfoList && !paramInfoList.isEmpty()){
            paramInfos = new String[paramInfoList.size()];
            for(int i=0;i<paramInfoList.size();i++){
                paramInfos[i]=paramInfoList.get(i).getFieldEnglishName();
            }
        }
        if(null!=outParamInfoList && !outParamInfoList.isEmpty()){
            respInfos = new String[outParamInfoList.size()];
            for(int i=0;i<outParamInfoList.size();i++){
                respInfos[i]=outParamInfoList.get(i).getFieldEnglishName();
            }
        }
        updateRequest.setRespInfo(respInfos);
        updateRequest.setParamInfo(paramInfos);
        BusinessResult<Boolean> tableSettingUpdateResult = tableSettingService.updateTableSetting(userId, updateRequest);
        if(null==tableSettingUpdateResult || null==tableSettingUpdateResult.getData() || !tableSettingUpdateResult.getData().booleanValue()){
            logger.error("updateResourceTableAfter error, updateTableSetting is false; userId:{}, updateRequest:{}", userId, JSON.toJSONString(updateRequest));
        }
        //通知开放平台，实时更新数据
        serveFeignClient.asyncSourceDirInfo(request.getId());
//        ((ServeServiceImpl)applicationContext.getBean("ServeServiceImpl")).asyncSourceDirInfo(request.getId());
//        serveService.asyncSourceDirInfo(request.getId());
    }
}
