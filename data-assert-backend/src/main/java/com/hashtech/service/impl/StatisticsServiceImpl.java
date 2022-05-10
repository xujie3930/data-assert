package com.hashtech.service.impl;

import com.hashtech.common.DelFalgEnum;
import com.hashtech.mapper.ResourceTableMapper;
import com.hashtech.service.StatisticsService;
import com.hashtech.web.result.StatisticsResourceTableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xujie
 * @description 资源表信息统计
 * @create 2022-05-10 11:31
 **/
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private ResourceTableMapper resourceTableMapper;
    @Override
    public StatisticsResourceTableResult statisticsResourceTableInfo() {
        return resourceTableMapper.statisticsResourceTableInfo(DelFalgEnum.NOT_DELETE.getDesc());
    }
}
