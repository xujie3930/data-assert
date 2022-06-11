package com.hashtech.schedule;

import com.hashtech.common.DelFalgEnum;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.mapper.ResourceTableMapper;
import com.hashtech.service.ResourceTableService;
import com.hashtech.service.TableSettingService;
import com.hashtech.web.request.ResourceTablePreposeRequest;
import com.hashtech.web.result.BaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xujie
 * @description 资源表相关信息统计（
 * 表：含有资源表数量
 * 数据项：资源表所有字段数量总和
 * 数据量：资源表所有数据量总和）
 * @create 2022-05-09 18:10
 **/
@Component
@Lazy(false)
public class ResourceTableStatisticsTask {

    @Autowired
    private TableSettingService tableSettingService;
    @Autowired
    private ResourceTableMapper resourceTableMapper;
    @Autowired
    private ResourceTableService resourceTableService;


    private static final int THREAD_COUNT_NUM = 5;
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            THREAD_COUNT_NUM,
            THREAD_COUNT_NUM * 2,
            50,
            TimeUnit.SECONDS
            , new LinkedBlockingQueue<Runnable>());

    @Scheduled(cron = "0 */1 * * * ?")
    public void statisticsTask() throws InterruptedException {
        System.out.println("开始执行资源表统计任务");
        Date date = new Date();
        long startTime = System.currentTimeMillis();
        List<ResourceTableEntity> resourceTableList = resourceTableMapper.getList(DelFalgEnum.NOT_DELETE.getDesc());
        if (CollectionUtils.isEmpty(resourceTableList)){
            return;
        }
        for (ResourceTableEntity resourceTableEntity : resourceTableList) {
            //同步数据服务数据
            executor.execute(() -> {
                BaseInfo baseInfo = tableSettingService.getBaseInfo(new ResourceTablePreposeRequest(resourceTableEntity.getDatasourceId(), resourceTableEntity.getName()));
                //如果columnsCount或者dataSize不同，则更新数据库
                if (!resourceTableEntity.getColumnsCount().equals(baseInfo.getColumnsCount()) ||
                        !resourceTableEntity.getDataSize().equals(baseInfo.getDataSize())) {
                    resourceTableEntity.setColumnsCount(baseInfo.getColumnsCount());
                    resourceTableEntity.setDataSize(baseInfo.getDataSize());
                    resourceTableEntity.setTableUpdateTime(date);
                    resourceTableService.updateById(resourceTableEntity);
                    System.out.println("执行完成，资源表id：" + resourceTableEntity.getId());
                }
            });
        }
        long endTime = System.currentTimeMillis();
        long spendTime = (endTime - startTime) ;
        System.out.println("执行资源表统计任务总耗时:" + spendTime + " 毫秒");
    }
}
