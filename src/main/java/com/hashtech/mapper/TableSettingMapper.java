package com.hashtech.mapper;

import com.hashtech.entity.TableSettingEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 资源信息设置表 Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2021-11-29
 */
public interface TableSettingMapper extends BaseMapper<TableSettingEntity> {

    TableSettingEntity getByResourceTableId(@Param("resourceTableId") String id);

    Boolean hasExistInterfaceName(@Param("interfaceName")String interfaceName);
}
