package com.hashtech.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hashtech.entity.DataSourceEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2021-12-01
 */
@Mapper
public interface DataSourceMapper extends BaseMapper<DataSourceEntity> {

    List<Map<Integer, String>> getList();
}
