package com.hashtech.service.impl;

import com.hashtech.common.DelFalgEnum;
import com.hashtech.mapper.ResourceTableMapper;
import com.hashtech.mapper.ThemeResourceMapper;
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
    @Autowired
    private ThemeResourceMapper themeResourceMapper;
    @Override
    public StatisticsResourceTableResult statisticsResourceTableInfo() {
        StatisticsResourceTableResult statisticsResourceTable = resourceTableMapper.statisticsResourceTableInfo(DelFalgEnum.NOT_DELETE.getDesc());
        Long themeCount = themeResourceMapper.countByTheme(DelFalgEnum.NOT_DELETE.getDesc());
        statisticsResourceTable.setThemeCount(themeCount);
        Long resourceCount = themeResourceMapper.countByResource(DelFalgEnum.NOT_DELETE.getDesc());
        statisticsResourceTable.setResourceCount(resourceCount);
        Long masterDataCount = resourceTableMapper.countByMasterData(DelFalgEnum.NOT_DELETE.getDesc());
        statisticsResourceTable.setMasterDataCount(masterDataCount);
        return statisticsResourceTable;
    }
}
