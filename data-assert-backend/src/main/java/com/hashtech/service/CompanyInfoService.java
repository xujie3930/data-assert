package com.hashtech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.common.BusinessPageResult;
import com.hashtech.entity.CompanyInfoEntity;
import com.hashtech.web.request.CompanyListRequest;
import com.hashtech.web.request.CompanySaveRequest;
import com.hashtech.web.request.CompanyUpdateRequest;
import com.hashtech.web.result.CompanyDetailResult;
import com.hashtech.web.result.CompanyListResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 企业信息表 服务类
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
public interface CompanyInfoService extends IService<CompanyInfoEntity> {

    Boolean saveDef(String userId, CompanySaveRequest request);

    Boolean hasExistUscc(String uscc, String id);

    BusinessPageResult getList(CompanyListRequest request);

    Boolean deleteCompanyDef(String userId, String[] ids);

    Boolean updateDef(String userId, CompanyUpdateRequest request);

    CompanyDetailResult detailById(String id);

    void updateTagNumById(Long count, String companyId);

    BusinessPageResult getRelateList(CompanyListRequest request);
}
