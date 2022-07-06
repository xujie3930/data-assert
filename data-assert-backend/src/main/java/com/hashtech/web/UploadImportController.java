package com.hashtech.web;

import com.hashtech.common.BusinessResult;
import com.hashtech.service.UploadImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xujie
 * @description 上传企业模板信息controller
 * @create 2022-04-14 18:54
 **/
@RestController
@RequestMapping()
public class UploadImportController {

    @Value("${template.path}")
    private String filePath;
    @Autowired
    private UploadImportService uploadImportService;

    @PostMapping(value = {"/company/uploadImport", "uploadFile"})
    public BusinessResult<Boolean> uploadImport(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestPart("file") MultipartFile file, @RequestParam(value = "ids", required = false) String ids, @RequestParam(value = "industryIds", required = false) String industryIds) {
        return BusinessResult.success(uploadImportService.uploadImport(userId, file, ids, industryIds));
    }
}
