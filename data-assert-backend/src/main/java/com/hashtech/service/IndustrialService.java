package com.hashtech.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.common.BusinessPageResult;
import com.hashtech.entity.IndustrialEntity;
import com.hashtech.web.request.IndustryListRequest;
import com.hashtech.web.request.IndustrySaveRequest;
import com.hashtech.web.request.IndustryUpdateRequest;

import java.util.List;

/**
 * <p>
 * 产业库 服务类
 * </p>
 *
 * @author xujie
 * @since 2022-07-06
 */
public interface IndustrialService extends IService<IndustrialEntity> {

    String saveDef(String userId, IndustrySaveRequest request);

    String updateDef(String userId, IndustryUpdateRequest request);

    String delete(String userId, String id);

    List<IndustrialEntity> likeByName(String name);

    BusinessPageResult<IndustrialEntity> getList(IndustryListRequest request);
}
