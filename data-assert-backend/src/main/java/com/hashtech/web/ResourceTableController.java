package com.hashtech.web;

import com.hashtech.common.BusinessPageResult;
import com.hashtech.common.BusinessResult;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.feign.result.ResourceTableResult;
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

    @PostMapping("/hasExistOpenExternalState")
    public BusinessResult<Boolean> hasExistOpenExternalState(@RequestBody ExistOpenExternalRequest request) {
        Boolean hasExistOpenExternalState = resourceTableService.hasExistOpenExternalState(request);
        return BusinessResult.success(hasExistOpenExternalState);
    }

    @PostMapping("/hasExist")
    public BusinessResult<Boolean> hasExitSerialNum(@RequestBody HasExitSerialNumRequest request) {
        Boolean hasExitSerialNum = resourceTableService.hasExitSerialNum(request);
        return BusinessResult.success(hasExitSerialNum);
    }

    @GetMapping("/getDataSource")
    public BusinessResult<List<Map<Integer, String>>> getDataSource() {
        return resourceTableService.getDataSource();
    }

    @PostMapping("/prepose/getTablaList")
    BusinessResult<List<Map<String, String>>> getTablaList(@RequestBody TableNameListRequest request) {
        return tableSettingService.getTablaList(request);
    }

    @PostMapping("/save")
    BusinessResult<Boolean> saveResourceTable(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody ResourceTableSaveRequest request) {
        return resourceTableService.saveResourceTable(userId, request);
    }

    @PostMapping("/update")
    BusinessResult<Boolean> updateResourceTable(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody ResourceTableUpdateRequest request) {
        return resourceTableService.updateResourceTable(userId, request);
    }

    @PostMapping("/update/state")
    BusinessResult<Boolean> updateResourceTableState(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody ResourceTableUpdateStateRequest request) {
        return resourceTableService.updateResourceTableState(userId, request);
    }

    @PostMapping("/info/baseInfo")
    BusinessResult<BaseInfo> getResourceTableBaseInfo(@RequestBody ResourceTableBaseInfoRequest request) {
        return resourceTableService.getResourceTableBaseInfo(request);
    }

    @PostMapping("/info/structureList")
    BusinessResult<List<Structure>> getResourceTableStructureList(@RequestBody ResourceTableNameRequest request) {
        return resourceTableService.getResourceTableStructureList(request);
    }

    @PostMapping("/info/sampleList")
    BusinessResult<BusinessPageResult<Object>> getResourceTableSampleList(@RequestBody ResourceTablePreposeRequest request) {
        return resourceTableService.getResourceTableSampleList(request);
    }

    @PostMapping("/dataPreview")
    Map<String, Object> dataPreview(@RequestBody ResourceDataPreviewRequest request) {
        return resourceTableService.dataPreview(request);
    }

    @PostMapping("/delete")
    BusinessResult<Boolean> deleteResourceTable(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody String[] ids) {
        return resourceTableService.deleteResourceTable(userId, ids);
    }

    @PostMapping("/pageList")
    BusinessResult<BusinessPageResult> pageList(@RequestBody ResourceTablePageListRequest request) {
        return resourceTableService.pageList(request);
    }

    @PostMapping("/List")
    BusinessResult<List<ResourceTableEntity>> getList(@RequestBody ResourceTableListRequest request) {
        return resourceTableService.getList(request);
    }

    /**
     * @param resourceTableId
     * @return
     */
    @GetMapping("/getResourceTable")
    BusinessResult<ResourceTableResult> getResourceTable(@RequestParam("resourceTableId") String resourceTableId) {
        ResourceTableResult result = resourceTableService.getByResourceTableId(resourceTableId);
        return BusinessResult.success(result);
    }
}

