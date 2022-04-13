package com.hashtech.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hashtech.entity.CompanyTagEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 企业标签关联表 Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
public interface CompanyTagMapper extends BaseMapper<CompanyTagEntity> {

    List<CompanyTagEntity> getLitsByTagId(@Param("tagId") String tagId);

    CompanyTagEntity findByTagIdAndCompanyId(@Param("tagId") String tagId, @Param("companyInfoId") String companyId);

    List<CompanyTagEntity> getListByCompanyId(@Param("companyInfoId") String companyInfoId);

    List<Map<String, Object>> countByTagId();

    List<Map<String, Object>> countBycompanyInfoId();

    void deleteCompanyTagByTagIds(@Param("userId") String userId, @Param("ids") List<String> ids);

    void deleteCompanyTagByCompanyId(@Param("userId") String userId, @Param("ids") List<String> companyIds);
}
