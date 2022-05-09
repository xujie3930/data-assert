package com.hashtech.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hashtech.entity.MasterDataEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 主数据类别表 Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2022-03-23
 */
@Mapper
public interface MasterDataMapper extends BaseMapper<MasterDataEntity> {

    List<MasterDataEntity> getList();

    MasterDataEntity selectByThemeId(@Param("themeId") String themeId);
}
