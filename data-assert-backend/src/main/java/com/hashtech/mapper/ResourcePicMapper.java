package com.hashtech.mapper;

import com.hashtech.entity.ResourcePicEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2022-06-02
 */
public interface ResourcePicMapper extends BaseMapper<ResourcePicEntity> {

    Boolean checkRepetition(@Param("picPath") String picPath);
}
