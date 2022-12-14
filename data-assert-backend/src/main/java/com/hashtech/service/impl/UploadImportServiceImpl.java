package com.hashtech.service.impl;

import com.hashtech.common.AppException;
import com.hashtech.common.ResourceCodeClass;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public Boolean uploadImport(String userId, MultipartFile file, String ids, String industrialIds) {
        List<String> tadIdList = new ArrayList<>();
        if (StringUtils.isNotBlank(ids)){
            //根据前端传值做匹配
            ids = ids.replaceAll("\\[", "").replaceAll("]", "").replace("\"", "").replace("null", "");
            String[] idsArr = ids.split(",");
            tadIdList = Arrays.asList(idsArr).stream().filter(i -> StringUtils.isNotBlank(i)).collect(Collectors.toList());
        }
        List<String> industryIdList = new ArrayList<>();
        if (StringUtils.isNotBlank(industrialIds)){
            //根据前端传值做匹配
            industrialIds = industrialIds.replaceAll("\\[", "").replaceAll("]", "").replace("\"", "").replace("null", "");
            String[] industryIdsArr = industrialIds.split(",");
            industryIdList = Arrays.asList(industryIdsArr).stream().filter(i -> StringUtils.isNotBlank(i)).collect(Collectors.toList());
        }
        List<CompanyInfoImportContent> importList = null;
        try {
            importList = ExcelUtils.readExcelFileData(file, 1, 1, CompanyInfoImportContent.class);
        } catch (Exception e) {
            throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000020.getCode());
        }
        for (CompanyInfoImportContent content : importList) {
            ((CompanyInfoServiceImpl)applicationContext.getBean("companyInfoServiceImpl")).saveDef(userId, new CompanySaveRequest(content.getUscc(), content.getCorpNm(), tadIdList, content.getDescribe(), industryIdList));
        }
        return true;
    }
}
