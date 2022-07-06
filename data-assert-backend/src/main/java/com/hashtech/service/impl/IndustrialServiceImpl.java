package com.hashtech.service.impl;

import com.hashtech.common.*;
import com.hashtech.config.validate.BusinessParamsValidate;
import com.hashtech.entity.IndustrialEntity;
import com.hashtech.entity.ThemeResourceEntity;
import com.hashtech.feign.vo.InternalUserInfoVO;
import com.hashtech.mapper.IndustrialMapper;
import com.hashtech.service.IndustrialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.service.OauthApiService;
import com.hashtech.utils.CharUtil;
import com.hashtech.web.request.IndustrySaveRequest;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 产业库 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2022-07-06
 */
@Service
public class IndustrialServiceImpl extends ServiceImpl<IndustrialMapper, IndustrialEntity> implements IndustrialService {

    @Autowired
    private OauthApiService oauthApiService;
    @Autowired
    private IndustrialMapper industrialMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @BusinessParamsValidate(argsIndexs = {1})
    public String saveDef(String userId, IndustrySaveRequest request) {
        InternalUserInfoVO user = oauthApiService.getUserById(userId);
        checkName(request.getName());
        checkRepetitionName(request.getName(), null);
        IndustrialEntity entity = getEntitySave(user, request);
        save(entity);
        return entity.getId();
    }

    private IndustrialEntity getEntitySave(InternalUserInfoVO user, IndustrySaveRequest request) {
        IndustrialEntity entity = BeanCopyUtils.copyProperties(request, new IndustrialEntity());
        Date date = new Date();
        entity.setCreateBy(user.getUsername());
        entity.setCreateUserId(user.getUserId());
        entity.setCreateTime(date);
        entity.setUpdateTime(date);
        entity.setUpdateBy(user.getUsername());
        return entity;
    }

    /**
     * 检查重复名称
     * @param name
     * @param id
     */
    private void checkRepetitionName(String name, String id) {
        boolean hasExit = BooleanUtils.isTrue(industrialMapper.hasExitName(name, id));
        if (hasExit) {
            throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000026.getCode());
        }
    }

    /**
     * 检查特殊字符
     * @param name
     */
    private void checkName(String name) {
        if (CharUtil.isSpecialChar(name)) {
            throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000027.getCode());
        }
    }
}
