package com.hashtech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.common.BusinessPageResult;
import com.hashtech.common.BusinessResult;
import com.hashtech.entity.TagCategoryEntity;
import com.hashtech.entity.TagEntity;
import com.hashtech.mapper.TagCategoryMapper;
import com.hashtech.web.request.*;
import com.hashtech.web.result.TagCategoryResult;
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
public interface TagCategoryService extends IService<TagCategoryEntity> {

    /**
     * 查询列表
     * @author  maoc
     * @create  2022/7/6 14:44
     * @desc
     **/
    List<TagCategoryResult> queryPageList(TagListRequest request);

    /**
     * 保存
     * @author  maoc
     * @create  2022/7/6 17:12
     * @desc
     **/
    BusinessResult<Boolean> saveDef(String userId, TagCategoryRequest request);

    /**
     * 编辑
     * @author  maoc
     * @create  2022/7/6 17:13
     * @desc
     **/
    BusinessResult<Boolean> updateDef(String userId, TagCategoryRequest request);

    /**
     * 删除
     * @author  maoc
     * @create  2022/7/6 17:28
     * @desc
     **/
    BusinessResult<Boolean> deleteDef(Long id);
}
