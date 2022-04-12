package com.hashtech.schedule;

import com.hashtech.service.CompanyInfoService;
import com.hashtech.service.CompanyTagService;
import com.hashtech.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author xujie
 * @description 定时更新标签和对应的企业数量
 * @create 2021-10-27 10:04
 **/
@Component
@Lazy(false)
public class SyncTagNumTask {


    @Autowired
    private CompanyTagService companyTagService;
    @Autowired
    private CompanyInfoService companyInfoService;
    @Autowired
    private TagService tagService;

    @Scheduled(cron = "0 */10 * * * ?")
    public void syncTagNum() throws InterruptedException {
        System.out.println("开始执行更新任务");
        long startTime = System.currentTimeMillis();

        List<Map<String, Object>> tagList = companyTagService.countByTagId();
        List<Map<String, Object>> companyList = companyTagService.countBycompanyInfoId();
        for (Map<String, Object> tagMap : tagList) {
            String tagId = (String) tagMap.get("tagId");
            Long count = (Long) tagMap.get("count");
            tagService.updateUsedTimeById(count, tagId);
        }
        for (Map<String, Object> companyMap : companyList) {
            String companyId = (String) companyMap.get("companyInfoId");
            Long count = (Long) companyMap.get("count");
            companyInfoService.updateTagNumById(count, companyId);
        }

        long endTime = System.currentTimeMillis();
        long spendTime = (endTime - startTime) / 1000;
        System.out.println("定时更新标签任务总耗时/秒:" + spendTime);
    }
}
