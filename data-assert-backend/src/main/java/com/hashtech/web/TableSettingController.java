package com.hashtech.web;


import com.hashtech.common.BusinessResult;
import com.hashtech.feign.result.AppGroupListResult;
import com.hashtech.service.TableSettingService;
import com.hashtech.web.request.ExistInterfaceNamelRequest;
import com.hashtech.web.request.TableSettingUpdateRequest;
import com.hashtech.web.result.TableSettingResult;
import org.springframework.beans.factory.annotation.Autowired;
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
        return tableSettingService.updateTableSetting(userId, request);
    }

    @PostMapping("/hasExistInterfaceName")
    public BusinessResult<Boolean> hasExistInterfaceName(@RequestBody ExistInterfaceNamelRequest request) {
        Boolean hasExistOpenExternalState = tableSettingService.hasExistInterfaceName(request);
        return BusinessResult.success(hasExistOpenExternalState);
    }

    @GetMapping("/appGroups")
    BusinessResult<List<AppGroupListResult>> getAppGroups() {
        return BusinessResult.success(tableSettingService.getAppGroups());
    }
}

