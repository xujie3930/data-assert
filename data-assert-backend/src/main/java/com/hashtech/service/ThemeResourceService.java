package com.hashtech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.common.AppException;
import com.hashtech.common.BusinessResult;
import com.hashtech.entity.ThemeResourceEntity;
import com.hashtech.web.request.*;
import com.hashtech.web.result.IdResult;
import com.hashtech.web.result.ResourceResult;
import com.hashtech.web.result.ThemeResult;

import java.util.List;
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

    BusinessResult<String> saveTheme(String userId, ThemeSaveRequest request);

    BusinessResult<ThemeResult> getThemeInfo(String id);

    BusinessResult<String> updateTheme(String userId, ThemeUpdateRequest request);

    BusinessResult<IdResult> deleteTheme(String userId, ThemeDeleteRequest request);

    BusinessResult<String> saveResource(String userId, ResourceSaveRequest request);

    BusinessResult<ResourceResult> getResourceInfo(String id);

    BusinessResult<String> updateResource(String userId, ResourceUpdateRequest request);

    BusinessResult<IdResult> deleteResource(String userId, ResourceDeleteRequest request) throws AppException;

    BusinessResult<Boolean> rearrangement(String userId, Map<String, String[]> request);

    BusinessResult<Boolean> hasExitTheme(ThemeSaveRequest request);

    BusinessResult<Boolean> hasExitResource(ResourceSaveRequest request);

    BusinessResult<List<ThemeResult>> getList();

    Boolean masterDataJudge(MasterDataJudgeRequest request);
}
