package com.hashtech.mapper;

import com.hashtech.entity.MasterDataEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

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

    List<Map<Integer, String>> getList();
}
