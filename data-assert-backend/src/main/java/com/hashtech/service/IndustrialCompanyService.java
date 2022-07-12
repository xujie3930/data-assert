package com.hashtech.service;

import com.hashtech.entity.IndustrialCompanyEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.feign.vo.InternalUserInfoVO;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 产业-企业表 服务类
 * </p>
 *
 * @author xujie
 * @since 2022-07-06
 */
public interface IndustrialCompanyService extends IService<IndustrialCompanyEntity> {

    List<IndustrialCompanyEntity> selectByIndustrialId(String id);

    List<IndustrialCompanyEntity> selectByIndustrialIds(List<String> ids);

    IndustrialCompanyEntity selectByIndustrialAndCompanyId(String industrialId, String companyInfoId);

    List<IndustrialCompanyEntity> selectByCompanyId(String companyInfoId);

    void saveOrUpdateIndustrialCompanyBatch(InternalUserInfoVO user, Date date, String companyInfoId, List<String> industrialIds);

    List<IndustrialCompanyEntity> selectByCompanyIds(List<String> companyIdList);
}
