package com.hashtech.web;


import com.hashtech.businessframework.log.Logable;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.service.ThemeResourceService;
import com.hashtech.web.request.*;
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
@RequestMapping("/theme")
public class ThemeResourceController {

    @Autowired
    private ThemeResourceService themeResourceService;

    @Logable
    @PostMapping("/save")
    BusinessResult<Boolean> saveTheme(@RequestHeader("x-userid") String userId, @RequestBody ThemeSaveRequest request) {
        return themeResourceService.saveTheme(userId, request);
    }

    @Logable
    @GetMapping("/info/{id}")
    BusinessResult<ThemeResult> getThemeInfo(@PathVariable("id") String id) {
        return themeResourceService.getThemeInfo(id);
    }

    @Logable
    @PostMapping("/update")
    BusinessResult<Boolean> updateTheme(@RequestHeader("x-userid") String userId, @RequestBody ThemeUpdateRequest request) {
        return themeResourceService.updateTheme(userId, request);
    }

    @Logable
    @PostMapping("/delete")
    BusinessResult<Boolean> deleteTheme(@RequestHeader("x-userid") String userId, @RequestBody ThemeDeleteRequest request) {
        return themeResourceService.deleteTheme(userId, request);
    }

    @Logable
    @PostMapping("/resource/save")
    BusinessResult<Boolean> saveResource(@RequestHeader("x-userid") String userId, @RequestBody ResourceSaveRequest request) {
        return themeResourceService.saveResource(userId, request);
    }

    @Logable
    @GetMapping("/resource/info/{id}")
    BusinessResult<Boolean> getResourceInfo(@PathVariable("id") String id) {
        return themeResourceService.getResourceInfo(id);
    }

    @Logable
    @PostMapping("/resource/update")
    BusinessResult<Boolean> updateResource(@RequestHeader("x-userid") String userId, @RequestBody ResourceUpdateRequest request) {
        return themeResourceService.updateResource(userId, request);
    }

    @Logable
    @PostMapping("/resource/delete")
    BusinessResult<Boolean> deleteResource(@RequestHeader("x-userid") String userId, @RequestBody ResourceDeleteRequest request) {
        return themeResourceService.deleteResource(userId, request);
    }
}

