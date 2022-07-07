package com.hashtech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.entity.TagCategoryEntity;
import com.hashtech.entity.TagCategoryRelationEntity;
import com.hashtech.web.request.TagCategoryRelationRequest;
import com.hashtech.web.request.TagListRequest;
import com.hashtech.web.result.TagCategoryResult;

import java.util.List;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
public interface TagCategoryRelationService extends IService<TagCategoryRelationEntity> {

    /**
     * 删除
     * @author  maoc
     * @create  2022/7/7 10:27
     * @desc
     **/
    Integer deletes(final TagCategoryRelationRequest request);
}
