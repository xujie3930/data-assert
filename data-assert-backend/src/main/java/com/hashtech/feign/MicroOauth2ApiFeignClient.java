package com.hashtech.feign;

import com.hashtech.feign.result.CommonResult;
import com.hashtech.feign.vo.CommonPage;
import com.hashtech.feign.vo.InternalUserInfoVO;
import com.hashtech.feign.vo.SysOrgPageReqVO;
import com.hashtech.feign.vo.SysOrgRespVO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * @author xujie
 * @description iCreditBanner类
 * @create 2021-08-19 14:17
 **/
@Component
@org.springframework.cloud.openfeign.FeignClient("micro-oauth2-api")
public interface MicroOauth2ApiFeignClient {

    @GetMapping("/sys/user/internal/info/{id}")
    CommonResult<InternalUserInfoVO> info(
            @PathVariable("id") String id);

    @PostMapping("/sys/org/page")
    CommonResult<CommonPage<SysOrgRespVO>> page(
            @RequestBody SysOrgPageReqVO reqVO, @RequestHeader(value = "userInfo") String userInfo);
}
