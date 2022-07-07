package com.hashtech.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hashtech.entity.TagCategoryEntity;
import com.hashtech.entity.TagEntity;
import com.hashtech.web.request.TagListRequest;
import com.hashtech.web.result.TagCategoryResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
@Repository
public interface TagCategoryMapper extends BaseMapper<TagCategoryEntity> {

    /**
     * 列表查询方法，不分页
     * @author  maoc
     * @create  2022/7/6 15:37
     * @desc
     **/
    List<TagCategoryResult> queryPageList(TagListRequest request);

    /**
     * 批量更新level
     * @author  maoc
     * @create  2022/7/7 13:39
     * @desc
     **/
    Integer batchUpdateLevel(@Param("param") Map<Long, Short> param);
}
