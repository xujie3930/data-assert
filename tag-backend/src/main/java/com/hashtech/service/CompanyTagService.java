package com.hashtech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.entity.CompanyTagEntity;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 企业标签关联表 服务类
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
public interface CompanyTagService extends IService<CompanyTagEntity> {

    List<CompanyTagEntity> getLitsByTagId(String tagId);

    CompanyTagEntity findByTagIdAndCompanyId(String tagId, String companyId);

    List<CompanyTagEntity> getListByCompanyId(String companyInfoId);

    List<Map<String, Object>> countByTagId();

    List<Map<String, Object>> countBycompanyInfoId();

    void deleteCompanyTagByTagIds(String userId, List<String> ids);

    void deleteCompanyTagByCompanyId(String userId, List<String> companyIds);
}
