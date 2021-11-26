package com.hashtech.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.web.request.ResourceTablePageListRequest;
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

    List<ResourceTableEntity> queryPage(Page<ResourceTableEntity> page, @Param(value = "request")ResourceTablePageListRequest request);
}
