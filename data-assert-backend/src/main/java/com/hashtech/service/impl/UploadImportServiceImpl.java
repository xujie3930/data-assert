package com.hashtech.service.impl;

import com.hashtech.config.validate.BusinessParamsValidate;
import com.hashtech.easyexcel.bean.CompanyInfoImportContent;
import com.hashtech.service.CompanyInfoService;
import com.hashtech.service.UploadImportService;
import com.hashtech.utils.excel.ExcelUtils;
import com.hashtech.web.request.CompanySaveRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xujie
 * @description
 * @create 2022-04-14 18:58
 **/
@Service
public class UploadImportServiceImpl implements UploadImportService {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @BusinessParamsValidate
    public Boolean uploadImport(String userId, MultipartFile file, String ids) {
        List<String> tadIdList = new ArrayList<>();
        if (StringUtils.isNotBlank(ids)){
            //根据前端传值做匹配
            ids = ids.replaceAll("\\[", "").replaceAll("]", "").replace("\"", "").replace("null", "");
            String[] idsArr = ids.split(",");
            tadIdList = Arrays.asList(idsArr);
        }
        List<CompanyInfoImportContent> importList = ExcelUtils.readExcelFileData(file, 1, 1, CompanyInfoImportContent.class);
        for (CompanyInfoImportContent content : importList) {
            ((CompanyInfoServiceImpl)applicationContext.getBean("companyInfoServiceImpl")).saveDef(userId, new CompanySaveRequest(content.getUscc(), content.getCorpNm(), tadIdList, content.getDescribe()));
        }
        return true;
    }
}
