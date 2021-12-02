package com.hashtech.web;


import com.hashtech.businessframework.log.Logable;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.service.TableSettingService;
import com.hashtech.web.request.ResourceTablePreviewRequest;
import com.hashtech.web.request.ResourceTableUpdateRequest;
import com.hashtech.web.result.TablePreviewResult;
import com.hashtech.web.result.TableSettingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Logable
    @GetMapping("/info/{id}")
    BusinessResult<TableSettingResult> getTableSetting(@PathVariable("id") String id) {
        return tableSettingService.getTableSetting(id);
    }

    @Logable
    @PostMapping("/update")
    BusinessResult<Boolean> updateTableSetting(@RequestHeader(value = "x-userid", defaultValue = "root") String userId, @RequestBody ResourceTableUpdateRequest request) {
        return tableSettingService.updateTableSetting(userId, request);
    }

    @Logable
    @PostMapping("/preview")
    BusinessResult<TablePreviewResult> previewTableSetting(@RequestHeader(value = "x-userid", defaultValue = "root") String userId, @RequestBody ResourceTablePreviewRequest request) {
        return tableSettingService.previewTableSetting(userId, request);
    }
}

