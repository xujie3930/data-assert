package com.hashtech.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hashtech.entity.CompanyInfoEntity;
import com.hashtech.web.request.CompanyListRequest;
import com.hashtech.web.request.IndustryListRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 * 企业信息表 Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
public interface CompanyInfoMapper extends BaseMapper<CompanyInfoEntity> {

    Boolean hasExistUscc(@Param("uscc") String uscc, @Param("id") String id);

    CompanyInfoEntity findById(@Param("id") String id);

    CompanyInfoEntity findByUsccAndCorpNm(@Param("uscc") String uscc, @Param("corpNm") String corpNm, @Param("companyInfoId") String companyInfoId);

    List<String> getCompanyIdList(@Param(value = "request") IndustryListRequest request);

    List<CompanyInfoEntity> selectByRequest(@Param(value = "request") CompanyListRequest request);

    Long selectCountByRequest(@Param(value = "request") CompanyListRequest request);
}
