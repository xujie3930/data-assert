package com.hashtech.service.impl;

import com.alibaba.fastjson.JSON;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author xujie
 * @description 获取iframe用户信息
 * @create 2022-01-18 23:35
 **/
@Service
public class OauthApiServiceImpl implements OauthApiService {
    @Autowired
    private MicroOauth2ApiFeignClient microOauth2ApiFeignClient;
    @Resource
    protected RedisTemplate<String, Object> redisTemplate;
    private final String SYS_ORG_MAP = "sys_org_Map";

    @Override
    public InternalUserInfoVO getUserById(String userId) {
        CommonResult<InternalUserInfoVO> result = microOauth2ApiFeignClient.info(userId);
        if (Objects.isNull(result.getData())) {
            throw new AppException(ResourceCodeBean.ResourceCode.RESOURCE_CODE_60000035.getCode());
        }
        return result.getData();
    }

    @Override
    public Map<String, String> orgPage() {
        Object orgListProject = redisTemplate.opsForValue().get(SYS_ORG_MAP);
        if (!Objects.isNull(orgListProject)) {
            Map map = JSON.parseObject(orgListProject.toString(), Map.class);
            return map;
        }
        SysOrgPageReqVO req = new SysOrgPageReqVO();
        req.setPageSize(100);
        req.setParentId("");
        CommonResult<CommonPage<SysOrgRespVO>> page = microOauth2ApiFeignClient.page(req, "0");
        if (page != null && page.getData() != null && CollectionUtils.isNotEmpty(page.getData().getList())){
            Map<String, String> map = page.getData().getList().stream().collect(Collectors.toMap(SysOrgRespVO::getId, SysOrgRespVO::getTitle));
            redisTemplate.opsForValue().set(SYS_ORG_MAP, JSON.toJSONString(map), 1L, TimeUnit.MINUTES);
            return map;
        }
       return null;
    }
}
