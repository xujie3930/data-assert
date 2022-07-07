package com.hashtech.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hashtech.entity.TagCategoryEntity;
import com.hashtech.entity.TagCategoryRelationEntity;
import com.hashtech.web.request.TagCategoryRelationRequest;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
@Repository
public interface TagCategoryRelationMapper extends BaseMapper<TagCategoryRelationEntity> {

    /**
     * 查询数量公用方法
     * @author  maoc
     * @create  2022/7/6 16:56
     * @desc
     **/
    Integer queryCount(TagCategoryRelationRequest request);

    /**
     * 删除
     * @author  maoc
     * @create  2022/7/7 10:26
     * @desc
     **/
    Integer deletes(TagCategoryRelationRequest request);
}
