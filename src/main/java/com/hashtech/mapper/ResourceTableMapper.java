package com.hashtech.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hashtech.entity.ResourceTableEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<ResourceTableEntity> getListByResourceId(@Param("resourceId") String id);
}
