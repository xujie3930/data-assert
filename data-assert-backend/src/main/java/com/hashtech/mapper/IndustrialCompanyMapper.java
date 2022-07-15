package com.hashtech.mapper;

import com.hashtech.entity.IndustrialCompanyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hashtech.entity.IndustrialEntity;
import com.hashtech.web.request.IndustryListRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 产业-企业表 Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2022-07-06
 */
public interface IndustrialCompanyMapper extends BaseMapper<IndustrialCompanyEntity> {

    List<IndustrialCompanyEntity> selectByIndustrialId(@Param("industrialId") String id);

    List<IndustrialCompanyEntity> selectByIndustrialIds(@Param("ids")List<String> ids);

    IndustrialCompanyEntity selectByIndustrialAndCompanyId(@Param("industrialId")String industrialId, @Param("companyInfoId")String companyInfoId);

    List<IndustrialCompanyEntity> selectByCompanyId(@Param("companyInfoId")String companyInfoId);

    List<IndustrialCompanyEntity> selectByCompanyIds(@Param("companyIdList") List<String> companyIdList);

    List<String> hasExistByCompanyIdAndIndustrialIds(@Param("companyInfoId") String id, @Param("industrialIds") List<String> industrialIds);

    List<IndustrialEntity> selectByRequest(@Param("request") IndustryListRequest request);
}
