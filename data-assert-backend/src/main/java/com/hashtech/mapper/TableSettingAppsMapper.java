package com.hashtech.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hashtech.entity.TableSettingAppsEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 资源信息设置表 Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2021-11-29
 */
public interface TableSettingAppsMapper extends BaseMapper<TableSettingAppsEntity> {

    int deleteByTableSettingId(@Param("tableSettingId") String tableSettingId);

    int batchInsert(@Param("list") List<TableSettingAppsEntity> list);

    List<TableSettingAppsEntity> queryByTableSettingId(@Param("tableSettingId") String tableSettingId);

}
