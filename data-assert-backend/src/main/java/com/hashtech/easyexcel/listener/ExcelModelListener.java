package com.hashtech.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.hashtech.common.BeanCopyUtils;
import com.hashtech.easyexcel.bean.CompanyInfoImportContent;
import com.hashtech.entity.CompanyInfoEntity;
import com.hashtech.service.CompanyInfoService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xujie
 * @description 异步读取
 * @create 2022-04-08 17:57
 **/
public class ExcelModelListener extends AnalysisEventListener<CompanyInfoImportContent> {

    //    @Resource
//    private CompanyInfoService companyInfoService;
    @Resource
    private ApplicationContext applicationContext;
    CompanyInfoService companyInfoService;
    @PostConstruct
    public CompanyInfoService init(){
        companyInfoService = applicationContext.getBean(CompanyInfoService.class);
        return companyInfoService;
    }
//    private CompanyInfoService companyInfoService = applicationContext.getBean(CompanyInfoService.class);

    /**
     * 每隔5条存储数据库，实际使用中能够3000条，而后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<CompanyInfoImportContent> list = new ArrayList<>();

    @Override
    public void invoke(CompanyInfoImportContent data, AnalysisContext context) {
        System.out.println("解析到一条数据:{ " + data.toString() + " }");
        list.add(data);
        saveToCompanyInfoTable(list);
//        if (list.size() >= BATCH_COUNT) {
//            //存储到数据库中
//            list.clear();
//        }
    }

    private void saveToCompanyInfoTable(List<CompanyInfoImportContent> list) {

        WebApplicationContext context= ContextLoader.getCurrentWebApplicationContext();
        CompanyInfoService companyInfoService = (CompanyInfoService)context.getBean("CompanyInfoService");
        List<CompanyInfoEntity> companyInfoEntityList = BeanCopyUtils.copy(list, CompanyInfoEntity.class);
        companyInfoService.saveBatch(companyInfoEntityList);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("全部数据解析完成！");
    }

}
