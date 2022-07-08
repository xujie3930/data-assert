package com.hashtech.web;


import com.hashtech.common.BusinessPageResult;
import com.hashtech.common.BusinessResult;
import com.hashtech.entity.IndustrialEntity;
import com.hashtech.service.IndustrialService;
import com.hashtech.web.request.IndustryListRequest;
import com.hashtech.web.request.IndustrySaveRequest;
import com.hashtech.web.request.IndustryUpdateRequest;
import com.hashtech.web.request.TagSaveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 产业库 前端控制器
 * </p>
 *
 * @author xujie
 * @since 2022-07-06
 */
@RestController
@RequestMapping("/industrial")
public class IndustrialController {

    @Autowired
    private IndustrialService industrialService;

    @PostMapping("/save")
    BusinessResult<String> saveDef(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody IndustrySaveRequest request) {
        return BusinessResult.success(industrialService.saveDef(userId, request));
    }

    @PostMapping("/update")
    BusinessResult<String> update(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody IndustryUpdateRequest request) {
        return BusinessResult.success(industrialService.updateDef(userId, request));
    }

    @GetMapping("/delete")
    BusinessResult<String> delete(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestParam("id") String id) {
        return BusinessResult.success(industrialService.delete(userId, id));
    }

    @PostMapping("/list")
    BusinessResult<BusinessPageResult> list(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody IndustryListRequest request) {
        return BusinessResult.success(industrialService.getList(request));
    }

    @GetMapping("/hasExistName")
    public BusinessResult<Boolean> hasExistName(@RequestParam("name") String name) {
        return BusinessResult.success(industrialService.hasExistName(name, null));
    }
}

