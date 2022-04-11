package com.hashtech.service;

import com.hashtech.entity.CompanyTagEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
}
