package com.hashtech.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.entity.TableSettingEntity;
import com.hashtech.mapper.TableSettingMapper;
import com.hashtech.service.TableSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资源信息设置表 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2021-11-29
 */
@Service
public class TableSettingServiceImpl extends ServiceImpl<TableSettingMapper, TableSettingEntity> implements TableSettingService {

    @Autowired
    private TableSettingMapper tableSettingMapper;

    @Override
    public TableSettingEntity getByResourceTableId(String id) {
        return tableSettingMapper.getByResourceTableId(id);
    }
}
