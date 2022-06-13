package com.hashtech.web;


import com.hashtech.common.BusinessResult;
import com.hashtech.common.ResourceCodeBean;
import com.hashtech.feign.result.AppGroupListResult;
import com.hashtech.service.TableSettingService;
import com.hashtech.web.request.ExistInterfaceNamelRequest;
import com.hashtech.web.request.TableSettingUpdateRequest;
import com.hashtech.web.result.TableSettingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 资源信息设置表 前端控制器
 * </p>
 *
 * @author xujie
 * @since 2021-11-29
 */
@RestController
@RequestMapping("/table/setting")
public class TableSettingController {

    @Autowired
    private TableSettingService tableSettingService;

    @GetMapping("/info/{id}")
    BusinessResult<TableSettingResult> getTableSetting(@PathVariable("id") String id) {
        return tableSettingService.getTableSetting(id);
    }

    @PostMapping("/update")
    BusinessResult<Boolean> updateTableSetting(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody TableSettingUpdateRequest request) {
        //新增三处非空判断
        //由于TableSettingUpdateRequest类多个地方用到，仅该接口需要判断非空，故不在TableSettingUpdateRequest里面加非空注解
        if(StringUtils.isEmpty(request.getFormats())){
            ResourceCodeBean.ResourceCode rc60000046 = ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000046;
            return BusinessResult.fail(rc60000046.getCode(), rc60000046.getMessage());
        }
        if(StringUtils.isEmpty(request.getRequestWay())){
            ResourceCodeBean.ResourceCode rc60000045 = ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000045;
            return BusinessResult.fail(rc60000045.getCode(), rc60000045.getMessage());
        }
        if(StringUtils.isEmpty(request.getRequestUrl())){
            ResourceCodeBean.ResourceCode rc60000044 = ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000044;
            return BusinessResult.fail(rc60000044.getCode(), rc60000044.getMessage());
        }
        return tableSettingService.updateTableSetting(userId, request);
    }

    @PostMapping("/hasExistInterfaceName")
    public BusinessResult<Boolean> hasExistInterfaceName(@RequestBody ExistInterfaceNamelRequest request) {
        Boolean hasExistOpenExternalState = tableSettingService.hasExistInterfaceName(request);
        return BusinessResult.success(hasExistOpenExternalState);
    }

    @GetMapping("/app/groups")
    public BusinessResult<List<AppGroupListResult>> getAppGroups() {
        return BusinessResult.success(tableSettingService.getAppGroups());
    }

    /*@PostMapping("/app/auth/save")
    public BusinessResult<Boolean> appAuthSave(@RequestHeader(value = "userId", required = false) String userId, @RequestBody AppAuthSaveRequest request) {
        return tableSettingService.appAuthSave(userId, request);
    }

    @PostMapping("/app/auth/info")
    public BusinessResult<AppAuthInfoResult> appAuthInfo(@RequestBody AppAuthInfoRequest request) {
        return BusinessResult.success(tableSettingService.appAuthInfo(request));
    }*/
}

