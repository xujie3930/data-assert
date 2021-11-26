package com.hashtech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.entity.ThemeResourceEntity;
import com.hashtech.web.request.*;
import com.hashtech.web.result.ResourceResult;
import com.hashtech.web.result.ThemeResult;

import java.util.Map;

/**
 * <p>
 * 主题资源表 服务类
 * </p>
 *
 * @author xujie
 * @since 2021-11-23
 */
public interface ThemeResourceService extends IService<ThemeResourceEntity> {

    BusinessResult<Boolean> saveTheme(String userId, ThemeSaveRequest request);

    BusinessResult<ThemeResult> getThemeInfo(String id);

    BusinessResult<Boolean> updateTheme(String userId, ThemeUpdateRequest request);

    BusinessResult<Boolean> deleteTheme(String userId, ThemeDeleteRequest request);

    BusinessResult<Boolean> saveResource(String userId, ResourceSaveRequest request);

    BusinessResult<ResourceResult> getResourceInfo(String id);

    BusinessResult<Boolean> updateResource(String userId, ResourceUpdateRequest request);

    BusinessResult<Boolean> deleteResource(String userId, ResourceDeleteRequest request);

    BusinessResult<Boolean> rearrangement(String userId, Map<String, String[]> request);

    BusinessResult<Boolean> hasExitTheme(ThemeSaveRequest request);
}
