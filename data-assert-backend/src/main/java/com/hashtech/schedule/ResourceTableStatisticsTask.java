package com.hashtech.schedule;

import com.hashtech.common.DelFalgEnum;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.mapper.ResourceTableMapper;
import com.hashtech.service.ResourceTableService;
import com.hashtech.service.TableSettingService;
import com.hashtech.utils.druid.DataApiDruidDataSourceService;
import com.hashtech.web.request.ResourceTablePreposeRequest;
import com.hashtech.web.result.BaseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

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
    private final Logger logger = LoggerFactory.getLogger(ResourceTableStatisticsTask.class);

    private static final int THREAD_COUNT_NUM = 5;
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            THREAD_COUNT_NUM,
            THREAD_COUNT_NUM * 2,
            50,
            TimeUnit.SECONDS
            , new LinkedBlockingQueue<Runnable>(100000)
            , new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    throw new RejectedExecutionException("Task "+r.toString()+" rejected from "+ executor.toString());
                }
    });
    private final AtomicInteger taskNum = new AtomicInteger(0);
    /*private final AtomicLong taskTime = new AtomicLong();*/

    @Scheduled(cron = "* * * * * ?")
    public void statisticsTask() throws InterruptedException {
        if(!canExec()){
            //有任务执行，抛弃
            logger.warn("有任务正在执行，抛弃当前任务");
            //System.out.println("有任务正在执行，抛弃当前任务");
            return;
        }
        //System.out.println("开始执行资源表统计任务");
        logger.warn("开始执行资源表统计任务");
        Date date = new Date();
        long startTime = System.currentTimeMillis();
        List<ResourceTableEntity> resourceTableList = resourceTableMapper.getList(DelFalgEnum.NOT_DELETE.getDesc());
        if (CollectionUtils.isEmpty(resourceTableList)){
            return;
        }
        for (ResourceTableEntity resourceTableEntity : resourceTableList) {
            //同步数据服务数据
            taskNum.incrementAndGet();
            executor.execute(() -> {
                try{
                BaseInfo baseInfo = tableSettingService.getBaseInfo(new ResourceTablePreposeRequest(resourceTableEntity.getDatasourceId(), resourceTableEntity.getName()));
                //如果columnsCount或者dataSize不同，则更新数据库
                if (!resourceTableEntity.getColumnsCount().equals(baseInfo.getColumnsCount()) ||
                        !resourceTableEntity.getDataSize().equals(baseInfo.getDataSize())) {
                        ResourceTableEntity entity = new ResourceTableEntity();
                        entity.setId(resourceTableEntity.getId());
                        entity.setColumnsCount(baseInfo.getColumnsCount());
                        entity.setDataSize(baseInfo.getDataSize());
                        entity.setTableUpdateTime(date);
                        resourceTableService.updateById(entity);
                    System.out.println("执行完成，资源表id：" + resourceTableEntity.getId());
                }
                }finally {
                    taskNum.decrementAndGet();
                }
            });
        }
        //子线程异步执行，所以统计时间不准
        long endTime = System.currentTimeMillis();
        long spendTime = (endTime - startTime) ;
        logger.warn("执行资源表统计任务总耗时:" + spendTime + " 毫秒");
        //System.out.println("执行资源表统计任务总耗时:" + spendTime + " 毫秒");
    }

    private boolean canExec(){
        if(taskNum.get()<=0 || executor.getQueue().isEmpty()){
            taskNum.set(0);
            //taskTime.set(System.currentTimeMillis());
            return true;
        }else {
            return false;
        }
        //如果大于0，判断上次发起时间
        /*long lastTime = taskTime.get();
        long nowTime = System.currentTimeMillis();
        if(lastTime>0 && (nowTime-lastTime)>(1800000l)){
            //上次执行时间距今超过30分钟，可执行
            taskNum.set(0);
            taskTime.set(nowTime);
            return true;
        }
        return false;*/
    }
}
