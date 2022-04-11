package com.hashtech.mapper;

import com.hashtech.entity.CompanyInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 企业信息表 Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
public interface CompanyInfoMapper extends BaseMapper<CompanyInfoEntity> {

    Boolean hasExistUscc(@Param("uscc") String uscc);
}
