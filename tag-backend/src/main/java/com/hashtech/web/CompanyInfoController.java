package com.hashtech.web;


import com.hashtech.common.BusinessPageResult;
import com.hashtech.common.BusinessResult;
import com.hashtech.service.CompanyInfoService;
import com.hashtech.web.request.CompanyListRequest;
import com.hashtech.web.request.CompanySaveRequest;
import com.hashtech.web.request.CompanyUpdateRequest;
import com.hashtech.web.result.CompanyListResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = {"/uploadImport", "uploadFile"})
    public BusinessResult<Boolean> uploadImport(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestPart("file") MultipartFile file, @RequestParam String[] ids) {
        return BusinessResult.success(companyInfoService.uploadImport(userId, file, ids));
    }

    @GetMapping("/hasExistUscc")
    public BusinessResult<Boolean> hasExistUscc(@RequestParam("uscc") String uscc) {
        return BusinessResult.success(companyInfoService.hasExistUscc(uscc, null));
    }

    @PostMapping("/save")
    BusinessResult<Boolean> saveDef(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody CompanySaveRequest request) {
        return BusinessResult.success(companyInfoService.saveDef(userId, request));
    }

    @PostMapping("/list")
    BusinessResult<BusinessPageResult> getList(@RequestBody CompanyListRequest request) {
        request.setUpdateTime("desc");
        return BusinessResult.success(companyInfoService.getList(request));
    }

    @PostMapping("/delete")
    BusinessResult<Boolean> deleteCompanyDef(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody String[] ids) {
        return BusinessResult.success(companyInfoService.deleteCompanyDef(userId, ids));
    }

    @PostMapping("/update")
    BusinessResult<Boolean> updateDef(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody CompanyUpdateRequest request) {
        return BusinessResult.success(companyInfoService.updateDef(userId, request));
    }

    @GetMapping("/detail")
    BusinessResult<CompanyListResult> detailById(@RequestParam("id") String id) {
        return BusinessResult.success(companyInfoService.detailById(id));
    }
}

