package com.hashtech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.common.BusinessPageResult;
import com.hashtech.entity.CompanyInfoEntity;
import com.hashtech.web.request.*;
import com.hashtech.web.result.CompanyDetailResult;
import com.hashtech.web.result.CompanyListResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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

    Boolean hasExistUscc(String uscc, List<String> industrialIds);

    BusinessPageResult getList(CompanyListRequest request);

    Boolean deleteCompanyDef(String userId, Map<String, String[]> request);

    Boolean updateDef(String userId, CompanyUpdateRequest request);

    CompanyDetailResult detailById(String id);

    BusinessPageResult getRelateList(CompanyListRequest request);

    List<String> getCompanyIdList(IndustryListRequest request);
}
