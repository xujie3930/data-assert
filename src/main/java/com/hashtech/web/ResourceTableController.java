package com.hashtech.web;


import com.hashtech.businessframework.log.Logable;
import com.hashtech.businessframework.result.BusinessPageResult;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.service.ResourceTableService;
import com.hashtech.web.request.ResourceDeleteRequest;
import com.hashtech.web.request.ResourceTablePageListRequest;
import com.hashtech.web.request.ResourceTableSaveRequest;
import com.hashtech.web.request.ResourceTableUpdateRequest;
import com.hashtech.web.result.ResourceTableInfoResult;
import com.hashtech.web.result.ThemeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Logable
    @PostMapping("/save")
    BusinessResult<Boolean> saveResourceTable(@RequestHeader(value = "x-userid", defaultValue = "root") String userId, @RequestBody ResourceTableSaveRequest request) {
        return resourceTableService.saveResourceTable(userId, request);
    }

    @Logable
    @PostMapping("/update")
    BusinessResult<Boolean> saveResourceTable(@RequestHeader(value = "x-userid", defaultValue = "root") String userId, @RequestBody ResourceTableUpdateRequest request) {
        return resourceTableService.updateResourceTable(userId, request);
    }

    @Logable
    @GetMapping("/info/{id}")
    BusinessResult<ResourceTableInfoResult> getResourceTableInfo(@PathVariable("id") String id) {
        return resourceTableService.getResourceTableInfo(id);
    }

    @Logable
    @PostMapping("/delete")
    BusinessResult<Boolean> deleteResourceTable(@RequestHeader(value = "x-userid", defaultValue = "root") String userId, @RequestBody String[] ids) {
        return resourceTableService.deleteResourceTable(userId, ids);
    }

    @Logable
    @PostMapping("/pageList")
    BusinessPageResult pageList(@RequestBody ResourceTablePageListRequest request) {
        return resourceTableService.pageList(request);
    }
}

