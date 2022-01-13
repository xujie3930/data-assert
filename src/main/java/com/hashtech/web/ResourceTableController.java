package com.hashtech.web;


import com.hashtech.businessframework.log.Logable;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.service.ResourceTableService;
import com.hashtech.service.TableSettingService;
import com.hashtech.web.request.*;
import com.hashtech.web.result.BaseInfo;
import com.hashtech.web.result.Structure;
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
    @PostMapping("/hasExistOpenExternalState")
    public BusinessResult<Boolean> hasExistOpenExternalState(@RequestBody ExistOpenExternalRequest request) {
        Boolean hasExistOpenExternalState = resourceTableService.hasExistOpenExternalState(request);
        return BusinessResult.success(hasExistOpenExternalState);
    }

    @Logable
    @PostMapping("/hasExist")
    public BusinessResult<Boolean> hasExitSerialNum(@RequestBody HasExitSerialNumRequest request) {
        Boolean hasExitSerialNum = resourceTableService.hasExitSerialNum(request);
        return BusinessResult.success(hasExitSerialNum);
    }

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
    @PostMapping("/info/baseInfo")
    BusinessResult<BaseInfo> getResourceTableBaseInfo(@RequestBody ResourceTableBaseInfoRequest request) {
        return resourceTableService.getResourceTableBaseInfo(request);
    }

    @Logable
    @PostMapping("/info/structureList")
    BusinessResult<List<Structure>> getResourceTableStructureList(@RequestBody ResourceTableNameRequest request) {
        return resourceTableService.getResourceTableStructureList(request);
    }

    @Logable
    @PostMapping("/info/sampleList")
    BusinessResult<BusinessPageResult<Object>> getResourceTableSampleList(@RequestBody ResourceTablePreposeRequest request) {
        return resourceTableService.getResourceTableSampleList(request);
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

    @Logable
    @PostMapping("/List")
    BusinessResult<List<ResourceTableEntity>> getList(@RequestBody ResourceTableListRequest request) {
        return resourceTableService.getList(request);
    }

    /**
     * 给开放平台提供查询的接口,Post方式
     *
     * @param request
     * @return
     */
    @Logable
    @PostMapping("/getResourceData")
    BusinessResult<List<Object>> getResourceDataByPost(@RequestBody ResourceDataRequest request) {
        return resourceTableService.getResourceData(request);
    }

    /**
     * 给开放平台提供查询的接口,Get方式
     *
     * @param request
     * @return
     */
    @Logable
    @GetMapping("/getResourceData")
    BusinessResult<List<Object>> getResourceDataByGet(@RequestBody ResourceDataRequest request) {
        return resourceTableService.getResourceData(request);
    }

    /**
     *
     * @param requestUrl
     * @return
     */
    @Logable
    @GetMapping("/getResourceTable")
    BusinessResult<ResourceTableEntity> getResourceTable(@RequestParam("requestUrl") String requestUrl) {
        ResourceTableEntity entity = resourceTableService.getByRequestUrl(requestUrl);
        return BusinessResult.success(entity);
    }
}

