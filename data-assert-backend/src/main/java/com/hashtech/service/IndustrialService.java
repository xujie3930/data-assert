package com.hashtech.service;

import com.hashtech.entity.IndustrialEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.web.request.IndustrySaveRequest;

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
}
