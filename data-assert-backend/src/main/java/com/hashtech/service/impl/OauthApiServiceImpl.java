package com.hashtech.service.impl;

import com.hashtech.common.AppException;
import com.hashtech.common.ResourceCodeBean;
import com.hashtech.feign.MicroOauth2ApiFeignClient;
import com.hashtech.feign.result.CommonResult;
import com.hashtech.feign.vo.CommonPage;
import com.hashtech.feign.vo.InternalUserInfoVO;
import com.hashtech.feign.vo.SysOrgPageReqVO;
import com.hashtech.feign.vo.SysOrgRespVO;
import com.hashtech.service.OauthApiService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author xujie
 * @description 获取iframe用户信息
 * @create 2022-01-18 23:35
 **/
@Service
public class OauthApiServiceImpl implements OauthApiService {
    @Autowired
    private MicroOauth2ApiFeignClient microOauth2ApiFeignClient;

    @Override
    public InternalUserInfoVO getUserById(String userId) {
        CommonResult<InternalUserInfoVO> result = microOauth2ApiFeignClient.info(userId);
        if (Objects.isNull(result.getData())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000035.getCode());
        }
        return result.getData();
    }

    @Override
    public List<SysOrgRespVO> orgPage() {
        SysOrgPageReqVO req = new SysOrgPageReqVO();
        req.setPageSize(100);
        req.setParentId("");
        CommonResult<CommonPage<SysOrgRespVO>> page = microOauth2ApiFeignClient.page(req, "0");
        if (page != null && page.getData() != null && CollectionUtils.isNotEmpty(page.getData().getList())){
            return page.getData().getList();
        }
       return null;
    }
}
