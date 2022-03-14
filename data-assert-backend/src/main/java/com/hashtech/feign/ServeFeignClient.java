package com.hashtech.feign;

import com.hashtech.common.BusinessResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author xujie
 * @description 数据开放平台Feign
 * @create 2021-11-03 14:57
 **/
@FeignClient("serve")
public interface ServeFeignClient {

    @GetMapping("/backend/source/directory/getOpenDirIds")
    BusinessResult<String[]> getOpenDirIds(@RequestParam("classifyId") String classifyId);

    @GetMapping("/backend/source/directory/getOpenTopicAndClassifyIds")
    BusinessResult<Map<String, List<String>>> getOpenTopicAndClassifyIds();
}
