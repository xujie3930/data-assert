package com.hashtech.web;


import com.hashtech.businessframework.log.Logable;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.service.ResourceTableService;
import com.hashtech.service.TableSettingService;
import com.hashtech.web.request.*;
import com.hashtech.web.result.ResourceTableInfoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 主题资源表 前端控制器
 * </p>
 *
 * @author xujie
 * @since 2021-11-23
 */
@RestController
@RequestMapping("/resource/table")
public class ResourceTableController {

    @Autowired
    private ResourceTableService resourceTableService;
    @Autowired
    private TableSettingService tableSettingService;

    @Logable
    @GetMapping("/getDataSource")
    public BusinessResult<List<Map<Integer, String>>> getDataSource() {
        return resourceTableService.getDataSource();
    }

    @Logable
    @PostMapping("/prepose/getTablaList")
    BusinessResult<List<Map<String, String>>> getTablaList() {
        return tableSettingService.getTablaList();
    }

    @Logable
    @PostMapping("/save")
    BusinessResult<Boolean> saveResourceTable(@RequestHeader(value = "x-userid", defaultValue = "root") String userId, @RequestBody ResourceTableSaveRequest request) {
        return resourceTableService.saveResourceTable(userId, request);
    }

    @Logable
    @PostMapping("/update")
    BusinessResult<Boolean> updateResourceTable(@RequestHeader(value = "x-userid", defaultValue = "root") String userId, @RequestBody ResourceTableUpdateRequest request) {
        return resourceTableService.updateResourceTable(userId, request);
    }

    @Logable
    @PostMapping("/info")
    BusinessResult<ResourceTableInfoResult> getResourceTableInfo(@RequestBody ResourceTableInfoRequest request) {
        return resourceTableService.getResourceTableInfo(request);
    }

    @Logable
    @PostMapping("/delete")
    BusinessResult<Boolean> deleteResourceTable(@RequestHeader(value = "x-userid", defaultValue = "root") String userId, @RequestBody String[] ids) {
        return resourceTableService.deleteResourceTable(userId, ids);
    }

    @Logable
    @PostMapping("/pageList")
    BusinessResult<BusinessPageResult> pageList(@RequestBody ResourceTablePageListRequest request) {
        return resourceTableService.pageList(request);
    }

    //服务消费者实现实现FeignClient接口比较直观些
    @Logable
    @PostMapping("/getResourceData")
    BusinessResult<List<Object>> getResourceData(@RequestBody ResourceDataRequest request) {
        return resourceTableService.getResourceData(request);
    }
}

