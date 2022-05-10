package com.hashtech.web;

import com.hashtech.common.BusinessResult;
import com.hashtech.service.StatisticsService;
import com.hashtech.service.UploadImportService;
import com.hashtech.web.result.StatisticsResourceTableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xujie
 * @description 统计controller，统计资源表、数据项，数据量
 * @create 2022-05-10 10:54
 **/
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/resourceTable")
    public BusinessResult<StatisticsResourceTableResult> statisticsResourceTableInfo() {
        //directory_count
        return BusinessResult.success(statisticsService.statisticsResourceTableInfo());
    }
}
