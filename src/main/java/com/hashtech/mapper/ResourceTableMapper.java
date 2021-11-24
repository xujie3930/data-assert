package com.hashtech.mapper;

import com.hashtech.entity.ResourceTableEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

/**
 * <p>
 * 主题资源表 Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2021-11-23
 */
public interface ResourceTableMapper extends BaseMapper<ResourceTableEntity> {

    int deleteByResourceId(@Param("id") String id);
}
