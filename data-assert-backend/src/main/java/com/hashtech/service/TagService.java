package com.hashtech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.common.BusinessPageResult;
import com.hashtech.entity.TagEntity;
import com.hashtech.web.request.*;
import com.hashtech.web.result.TagRelateResult;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
public interface TagService extends IService<TagEntity> {

    Boolean hasExistCode(String code);

    Boolean hasExistName(String name, String id);

    Boolean saveDef(String userId, TagSaveRequest request);

    String getTagCode(Integer len);

    Boolean updateDef(String userId, TagUpdateRequest request);

    Object getList(TagListRequest request);

    Boolean enOrDisable(String userId, TagChangeStateRequest request);

    Boolean deleteDef(String userId, String[] ids);

    TagEntity detailById(String id);

    List<TagEntity> getListWithoutPaging();

    List<TagEntity> getByCompanyId(String id);

    TagRelateResult relate(CompanyListRequest request);

    void updateUsedTimeById(Long count, String tagId);
}
