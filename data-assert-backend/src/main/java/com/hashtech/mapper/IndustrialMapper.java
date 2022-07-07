package com.hashtech.mapper;

import com.hashtech.entity.IndustrialEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 产业库 Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2022-07-06
 */
public interface IndustrialMapper extends BaseMapper<IndustrialEntity> {

    Boolean hasExitName(@Param("name") String name, @Param("id") String id);
}
