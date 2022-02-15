package com.hashtech.web;


import com.hashtech.common.BusinessResult;
import com.hashtech.service.RomoteDataSourceService;
import com.hashtech.web.request.DataSourcesListRequest;
import com.hashtech.web.request.TableNameListRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 主题资源表 前端控制器
 * </p>
 *
 * @author xujie
 * @since 2022-02-14
 */
@RestController
@RequestMapping("/")
public class RomoteDatasourceController {

    @Autowired
    private RomoteDataSourceService romoteDataSourceService;

    /**
     * 根据数据源类型，查询数据源列表
     * @param
     * @return
     */
    @PostMapping("/getDatasourceListByType")
    BusinessResult<List<Map<String, Object>>> getDataSourcesList(@RequestBody DataSourcesListRequest request) {
        return romoteDataSourceService.getDataSourcesList(request);
    }

    /**
     * 根据数据源类型，查询数据源列表
     * @param
     * @return
     */
    @PostMapping("/getTableByDatasourceId")
    BusinessResult<List<Map<String, String>>> getTableNameList(@RequestBody TableNameListRequest request) {
        return romoteDataSourceService.getTableNameList(request);
    }
}

