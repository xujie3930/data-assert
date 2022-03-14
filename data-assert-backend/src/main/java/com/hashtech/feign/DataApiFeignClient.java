package com.hashtech.feign;

import com.hashtech.common.BusinessResult;
import com.hashtech.feign.request.DatasourceApiSaveRequest;
import com.hashtech.feign.result.ApiSaveResult;
import com.hashtech.feign.result.DatasourceDetailResult;
import com.hashtech.web.request.DataSourcesListRequest;
import com.hashtech.web.request.TableNameListRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author xujie
 * @description 数据服务Feign
 * @create 2021-11-03 14:57
 **/
@FeignClient("dataserve")
public interface DataApiFeignClient {

    @PostMapping("/apiBase/datasourceApi/create")
    BusinessResult<ApiSaveResult> createDataSourceApi(@RequestHeader(value = "userId") String userId, @RequestBody DatasourceApiSaveRequest apiSaveRequest);

    @PostMapping("/datasourceApi/createAndPublish")
    BusinessResult<ApiSaveResult> createOrUpdateDataSourceApiAndPublish(@RequestHeader(value = "userId") String userId, @RequestBody DatasourceApiSaveRequest apiSaveRequest);
}
