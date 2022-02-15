package com.hashtech.feign;

import com.hashtech.common.BusinessResult;
import com.hashtech.web.request.DataSourcesListRequest;
import com.hashtech.web.request.TableNameListRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author xujie
 * @description DatasourceFeign
 * @create 2021-11-03 14:57
 **/
@FeignClient("datasource")
public interface DatasourceFeignClient {

    @PostMapping("/datasource/getDataSourcesList")
    BusinessResult<List<Map<String, Object>>> getDataSourcesList(@RequestBody DataSourcesListRequest request);

    /**
     * 根据数据源类型，查询数据源列表
     * @param
     * @return
     */
    @PostMapping("/datasource/getTableByDatasourceId")
    BusinessResult<List<Map<String, String>>> getTableNameList(@RequestBody TableNameListRequest request);
}
