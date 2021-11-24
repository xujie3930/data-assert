package com.hashtech.service;

import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.entity.ThemeResourceEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.web.request.*;
import com.hashtech.web.result.ThemeResult;

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

    BusinessResult<Boolean> getResourceInfo(String id);

    BusinessResult<Boolean> updateResource(String userId, ResourceUpdateRequest request);

    BusinessResult<Boolean> deleteResource(String userId, ResourceDeleteRequest request);
}
