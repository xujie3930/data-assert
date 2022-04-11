package com.hashtech.web;


import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.metadata.Sheet;
import com.hashtech.common.AppException;
import com.hashtech.common.BusinessResult;
import com.hashtech.easyexcel.bean.CompanyInfoImportContent;
import com.hashtech.easyexcel.listener.ExcelModelListener;
import com.hashtech.service.CompanyInfoService;
import com.hashtech.web.request.CompanySaveRequest;
import com.hashtech.web.request.TagSaveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 企业信息表 前端控制器
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
@RestController
@RequestMapping("/company")
public class CompanyInfoController {

    @Autowired
    private CompanyInfoService companyInfoService;

    @PostMapping(value = {"/fileUpload","uploadFile"})
    public BusinessResult<Boolean> uploadFile(@RequestParam(value = "file") MultipartFile file) {

        Sheet sheet = new Sheet(1,1, CompanyInfoImportContent.class);
        byte [] byteArr= new byte[0];
        try {
            byteArr = file.getBytes();
        } catch (IOException e) {
            throw new AppException("解析文件失败");
        }
        InputStream inputStream = new ByteArrayInputStream(byteArr);
        EasyExcelFactory.readBySax(inputStream,sheet,new ExcelModelListener());
        return BusinessResult.success(true);
    }

    @GetMapping("/hasExistUscc")
    public BusinessResult<Boolean> hasExistUscc(@RequestParam("uscc") String uscc) {
        return BusinessResult.success(companyInfoService.hasExistUscc(uscc));
    }

    @PostMapping("/save")
    BusinessResult<Boolean> saveDef(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody CompanySaveRequest request) {
        return BusinessResult.success(companyInfoService.saveDef(userId, request));
    }
}

