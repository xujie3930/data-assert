package com.hashtech.aop;

import com.alibaba.fastjson.JSON;
import com.hashtech.common.BusinessResult;
import com.hashtech.feign.result.AppAuthResult;
import com.hashtech.service.TableSettingService;
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
import org.springframework.stereotype.Component;

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

    @Pointcut("execution(* com.hashtech.service.ResourceTableService.updateResourceTable(..))")
    public void updateResourceTablePointcut() {
    }

    @AfterReturning(value = "updateResourceTablePointcut()", returning = "result")
    public void updateResourceTableAfter(JoinPoint joinPoint, Object result){
        final Object[] args = joinPoint.getArgs();
        final BusinessResult<Boolean> businessResult = (BusinessResult<Boolean>) result;
        if(null==businessResult || null==businessResult.getData() || !businessResult.getData().booleanValue()){
            return;//方法没成功
        }
        String userId = (String) args[0];
        ResourceTableUpdateRequest request = (ResourceTableUpdateRequest) args[1];
        String id = request.getId();
        BusinessResult<TableSettingResult> tableSettingInfoResult = tableSettingService.getTableSetting(id);
        if(null==tableSettingInfoResult || null==tableSettingInfoResult.getData()){
            logger.error("updateResourceTableAfter error, tableSettingInfoResult is null;", tableSettingInfoResult);
            return;
        }
        TableSettingResult tableSetting = tableSettingInfoResult.getData();
        String explainInfo = tableSetting.getExplainInfo();
        Integer formats = tableSetting.getFormats();
        String interfaceName = tableSetting.getInterfaceName();
        String requestUrl = tableSetting.getRequestUrl();
        Integer requestWay = tableSetting.getRequestWay();
        List<Structure> paramInfo = tableSetting.getParamInfo();
        List<Structure> outParamInfo = tableSetting.getOutParamInfo();
        List<Structure> structureList = tableSetting.getStructureList();
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
        List<String> paramInfoList = new ArrayList<>(), respInfoList = new ArrayList<>();
        if(null!=paramInfo && !paramInfo.isEmpty()){
            paramInfo.stream().forEach(param->{
                paramInfoList.add(param.getFieldEnglishName());
            });
        }
        if(null!=outParamInfo && !outParamInfo.isEmpty()){
            outParamInfo.stream().forEach(resp->{
                respInfoList.add(resp.getFieldEnglishName());
            });
        }
        BusinessResult<Boolean> tableSettingUpdateResult = tableSettingService.updateTableSetting(userId, updateRequest);
        if(null==tableSettingUpdateResult || null==tableSettingUpdateResult.getData() || !tableSettingUpdateResult.getData().booleanValue()){
            logger.error("updateResourceTableAfter error, updateTableSetting is false; userId:{}, updateRequest:{}", userId, JSON.toJSONString(updateRequest));
        }


    }
}