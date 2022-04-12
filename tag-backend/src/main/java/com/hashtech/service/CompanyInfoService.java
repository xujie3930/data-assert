package com.hashtech.service;

import com.hashtech.common.BusinessPageResult;
import com.hashtech.entity.CompanyInfoEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.web.request.CompanyListRequest;
import com.hashtech.web.request.CompanySaveRequest;
import com.hashtech.web.request.TagSaveRequest;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

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

    Boolean hasExistUscc(String uscc);

    BusinessPageResult getList(CompanyListRequest request);

    Boolean deleteCompany(String userId, String[] ids);
}
