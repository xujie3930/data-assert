package com.hashtech.web;

import com.hashtech.common.BusinessResult;
import com.hashtech.common.ResourceCodeClass;
import com.hashtech.service.UploadImportService;
import com.hashtech.utils.ObjectUtils;
import com.hashtech.utils.StringUtils;
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
    public BusinessResult<Boolean> uploadImport(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestPart("file") MultipartFile file, @RequestParam(value = "ids", required = false) String ids, @RequestParam(value = "industrialIds", required = false) String industrialIds) {
        if((!StringUtils.checkFileType(file, "xlsx", "xls")) || (!ObjectUtils.isExcel(file))){
            ResourceCodeClass.ResourceCode rc70000031 = ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000031;
            return BusinessResult.fail(rc70000031.getCode(), rc70000031.getMessage());
        }
        return BusinessResult.success(uploadImportService.uploadImport(userId, file, ids, industrialIds));
    }
}
