package com.hashtech.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hashtech.entity.ThemeResourceEntity;
import com.hashtech.web.result.ThemeResult;
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
public interface ThemeResourceMapper extends BaseMapper<ThemeResourceEntity> {

    Integer getMaxSort();

    Integer getMaxSortByParentId(@Param("parentId") String themeId);

    Boolean hasExitName(@Param("name") String name, @Param("id") String id);

    Boolean hasExitNameByResource(@Param("name") String name, @Param("id") String id);

    int updateSort(@Param("sort") int sort, @Param("id") String themeId);

    void updateParentId(@Param("parentId") String themeId, @Param("ids") String[] resourceIds);

    List<ThemeResult> getResourceByParentId(@Param("parentId") String id);

    Boolean hasExitErrorLevel(@Param("ids") String[] resourceIds, @Param("parentId") String themeParentId);

    String selectNextId(@Param("sort") Integer sort, @Param("parentId") String parentId);

    String selectPreviousId(@Param("sort") Integer sort, @Param("parentId") String parentId);

    Long countByTheme(@Param("delFlag") String desc);

    Long countByResource(@Param("delFlag") String desc);
}
