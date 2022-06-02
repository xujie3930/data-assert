package com.hashtech.aop;

import com.hashtech.common.BusinessResult;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.entity.TableSettingEntity;
import com.hashtech.feign.vo.InternalUserInfoVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Map;

//@Aspect
//@Component
public class ResourceTableAop {

    @Pointcut("execution(* com.hashtech.service.ResourceTableService.updateResourceTable(..))")
    public void updateResourceTablePointcut() {
    }

    @AfterReturning(value = "updateResourceTablePointcut()", returning = "result")
    public void updateResourceTableAfter(JoinPoint joinPoint, Object result){
        final BusinessResult<Map<String, Object>> resultMap = (BusinessResult<Map<String, Object>>) result;
        Map<String, Object> data = resultMap.getData();
        Boolean bool = (Boolean) data.get("bool");
        if(null==bool || !bool.booleanValue()){
            return;
        }
        InternalUserInfoVO user = (InternalUserInfoVO) data.get("user");
        ResourceTableEntity resourceTableEntity = (ResourceTableEntity) data.get("resourceTable");
        TableSettingEntity tableSettingEntity = (TableSettingEntity) data.get("tableSetting");
    }
}
