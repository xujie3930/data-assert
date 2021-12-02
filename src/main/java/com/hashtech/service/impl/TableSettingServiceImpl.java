package com.hashtech.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.businessframework.exception.interval.AppException;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.businessframework.result.util.BeanCopyUtils;
import com.hashtech.businessframework.utils.CollectionUtils;
import com.hashtech.businessframework.utils.StringUtils;
import com.hashtech.businessframework.validate.BusinessParamsValidate;
import com.hashtech.common.ResourceCodeBean;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.entity.TableSettingEntity;
import com.hashtech.mapper.TableSettingMapper;
import com.hashtech.service.ResourceTableService;
import com.hashtech.service.TableSettingService;
import com.hashtech.web.request.ResourceTablePreviewRequest;
import com.hashtech.web.request.ResourceTableUpdateRequest;
import com.hashtech.web.result.TablePreviewResult;
import com.hashtech.web.result.TableSettingResult;
import com.hashtech.web.result.ThemeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

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
    @Autowired
    private ResourceTableService resourceTableService;

    @Override
    public BusinessResult<TableSettingResult> getTableSetting(String id) {
        TableSettingResult result = new TableSettingResult();
        ResourceTableEntity resourceTableEntity = resourceTableService.getById(id);
        result.setRequestUrl(resourceTableEntity.getRequestUrl());
        TableSettingEntity tableSettingEntity = tableSettingMapper.getByResourceTableId(id);
        BeanCopyUtils.copyProperties(tableSettingEntity, result);
        result.setParamInfo(Arrays.asList(tableSettingEntity.getParamInfo().split(",")));
        return BusinessResult.success(result);
    }

    @Override
    @BusinessParamsValidate
    @Transactional(rollbackFor = Exception.class)
    public BusinessResult<Boolean> updateTableSetting(String userId, ResourceTableUpdateRequest request) {
        //更新资源表
        ResourceTableEntity resourceTableEntity = resourceTableService.getById(request.getId());
        if (Objects.isNull(resourceTableEntity)) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000006.getCode());
        }
        resourceTableEntity.setUpdateTime(new Date());
        resourceTableEntity.setUpdateBy(userId);
        resourceTableService.updateById(resourceTableEntity);

        //更新资源表设置
        TableSettingEntity entity = tableSettingMapper.getByResourceTableId(request.getId());
        entity.setRequestWay(request.getRequestWay());
        entity.setExplainInfo(request.getExplainInfo());
        entity.setParamInfo(StringUtils.join(request.getParamInfo(), ","));
        return BusinessResult.success(true);
    }

    @Override
    public BusinessResult<TablePreviewResult> previewTableSetting(String userId, ResourceTablePreviewRequest request) {
        ResourceTableEntity resourceTableEntity = resourceTableService.getById(request.getId());
        String explainInfo = resourceTableEntity.getRequestUrl() + "?";
        if (!CollectionUtils.isEmpty(request.getParamInfo())) {
            for (String param : request.getParamInfo()) {
                explainInfo += param + "=?&";
            }
        }
        explainInfo = explainInfo.substring(0, explainInfo.length() - 1);
        TablePreviewResult result = new TablePreviewResult();
        result.setExplainInfo(explainInfo);
        return BusinessResult.success(result);
    }
}
