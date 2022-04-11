package com.hashtech.service;

import com.hashtech.common.BusinessPageResult;
import com.hashtech.entity.TagEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.web.request.TagChangeStateRequest;
import com.hashtech.web.request.TagListRequest;
import com.hashtech.web.request.TagSaveRequest;
import com.hashtech.web.request.TagUpdateRequest;

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

    BusinessPageResult getList(TagListRequest request);

    Boolean enOrDisable(String userId, TagChangeStateRequest request);

    Boolean deleteTag(String userId, String[] ids);

    TagEntity detailById(String id);

    List<TagEntity> getListWithoutPaging();

    void updateUsedTime();
}
