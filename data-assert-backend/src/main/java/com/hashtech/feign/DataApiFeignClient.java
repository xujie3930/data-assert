package com.hashtech.feign;

import com.hashtech.common.BusinessResult;
import com.hashtech.feign.request.DatasourceApiSaveRequest;
import com.hashtech.feign.result.ApiSaveResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author xujie
 * @description 数据服务Feign
 * @create 2021-11-03 14:57
 **/
@FeignClient("dataserve")
public interface DataApiFeignClient {

    @PostMapping("/dataserve/apiBase/datasourceApi/createAndPublish")
    BusinessResult<ApiSaveResult> createAndPublish(@RequestHeader(value = "userId") String userId, @RequestBody DatasourceApiSaveRequest apiSaveRequest);

    @PostMapping("/dataserve/apiBase/deleteByPath")
    BusinessResult<Boolean> deleteByPath(@RequestHeader(value = "userId") String userId, @RequestBody List<String> request);
}
