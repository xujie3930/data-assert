package com.hashtech.web;


import com.hashtech.common.BusinessResult;
import com.hashtech.entity.MasterDataEntity;
import com.hashtech.service.MasterDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 主数据类别表 前端控制器
 * </p>
 *
 * @author xujie
 * @since 2022-03-23
 */
@RestController
@RequestMapping("/masterData")
public class MasterDataController {

    @Autowired
    private MasterDataService masterDataService;

    @GetMapping("/getmasterData")
    public BusinessResult<List<MasterDataEntity>> getDataSource() {
        return masterDataService.getDataSource();
    }

}

