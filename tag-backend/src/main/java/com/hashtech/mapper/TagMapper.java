package com.hashtech.mapper;

import com.hashtech.entity.TagEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hashtech.web.request.TagListRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
public interface TagMapper extends BaseMapper<TagEntity> {

    Boolean hasExistCode(@Param("code") String code);

    Boolean hasExistName(@Param("name") String name, @Param("id") String id);

    TagEntity findById(@Param("id")String id);
}
