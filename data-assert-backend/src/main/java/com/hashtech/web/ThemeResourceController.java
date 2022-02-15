package com.hashtech.web;


import com.hashtech.common.BusinessResult;
import com.hashtech.service.ThemeResourceService;
import com.hashtech.web.request.*;
import com.hashtech.web.result.IdResult;
import com.hashtech.web.result.ResourceResult;
import com.hashtech.web.result.ThemeResult;
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
@RequestMapping("/theme")
public class ThemeResourceController {

    @Autowired
    private ThemeResourceService themeResourceService;

    @PostMapping("/hasExist")
    public BusinessResult<Boolean> hasExitTheme(@RequestBody ThemeSaveRequest request) {
        return themeResourceService.hasExitTheme(request);
    }

    @PostMapping("/save")
    BusinessResult<String> saveTheme(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody ThemeSaveRequest request) {
        return themeResourceService.saveTheme(userId, request);
    }

    @GetMapping("/info/{id}")
    BusinessResult<ThemeResult> getThemeInfo(@PathVariable("id") String id) {
        return themeResourceService.getThemeInfo(id);
    }

    @PostMapping("/update")
    BusinessResult<String> updateTheme(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody ThemeUpdateRequest request) {
        return themeResourceService.updateTheme(userId, request);
    }

    @PostMapping("/delete")
    BusinessResult<IdResult> deleteTheme(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody ThemeDeleteRequest request) {
        return themeResourceService.deleteTheme(userId, request);
    }

    @PostMapping("/list")
    BusinessResult<List<ThemeResult>> list() {
        return themeResourceService.getList();
    }

    @PostMapping("/rearrangement")
    BusinessResult<Boolean> rearrangement(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody Map<String, String[]> request) {
        return themeResourceService.rearrangement(userId, request);
    }

    @PostMapping("resource/hasExist")
    public BusinessResult<Boolean> hasExitResource(@RequestBody ResourceSaveRequest request) {
        return themeResourceService.hasExitResource(request);
    }

    @PostMapping("/resource/save")
    BusinessResult<String> saveResource(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody ResourceSaveRequest request) {
        return themeResourceService.saveResource(userId, request);
    }

    @GetMapping("/resource/info/{id}")
    BusinessResult<ResourceResult> getResourceInfo(@PathVariable("id") String id) {
        return themeResourceService.getResourceInfo(id);
    }

    @PostMapping("/resource/update")
    BusinessResult<String> updateResource(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody ResourceUpdateRequest request) {
        return themeResourceService.updateResource(userId, request);
    }

    @PostMapping("/resource/delete")
    BusinessResult<IdResult> deleteResource(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody ResourceDeleteRequest request) {
        return themeResourceService.deleteResource(userId, request);
    }
}

