package com.hashtech.feign;

import com.hashtech.feign.result.CommonResult;
import com.hashtech.feign.vo.InternalUserInfoVO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
}
