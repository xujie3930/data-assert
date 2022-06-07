package com.hashtech.feign;

import com.hashtech.common.BusinessResult;
import com.hashtech.feign.request.AppQueryListRequest;
import com.hashtech.feign.request.AuthSaveApiRequest;
import com.hashtech.feign.request.DatasourceApiSaveRequest;
import com.hashtech.feign.result.ApiSaveResult;
import com.hashtech.feign.result.AppGroupListResult;
import com.hashtech.feign.result.AuthListResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/dataserve/appGroup/list")
    BusinessResult<List<AppGroupListResult>> appGroupList(@RequestHeader(value = "userId") String userId, @RequestBody AppQueryListRequest request);

    @GetMapping("/dataserve/auth/queryApiAuthListByPath")
    BusinessResult<List<AuthListResult>> queryApiAuthListByPath(@RequestParam(value = "path") String path);

    @PostMapping("/dataserve/auth/saveOuterApi")
    BusinessResult<ApiSaveResult> saveOuterApi(@RequestHeader(value = "userId") String userId, @RequestBody AuthSaveApiRequest request);

    /*@PostMapping("/dataserve/auth/save")
    BusinessResult<Boolean> appAuthSave(@RequestHeader(value = "userId") String userId, @RequestBody AppAuthSaveRequest request);

    @PostMapping("/dataserve/auth/info")
    BusinessResult<AppAuthInfoResult> appAuthInfo(@RequestBody AppAuthInfoRequest request);*/
}
