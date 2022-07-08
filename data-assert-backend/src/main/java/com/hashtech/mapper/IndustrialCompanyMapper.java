package com.hashtech.mapper;

import com.hashtech.entity.IndustrialCompanyEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
}
