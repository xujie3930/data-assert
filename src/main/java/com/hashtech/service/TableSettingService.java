package com.hashtech.service;

import com.hashtech.entity.TableSettingEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 资源信息设置表 服务类
 * </p>
 *
 * @author xujie
 * @since 2021-11-29
 */
public interface TableSettingService extends IService<TableSettingEntity> {

    TableSettingEntity getByResourceTableId(String id);
}
