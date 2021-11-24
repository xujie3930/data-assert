package com.hashtech.service.impl;

import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.mapper.ResourceTableMapper;
import com.hashtech.service.ResourceTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.web.request.ResourceSaveRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 主题资源表 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2021-11-23
 */
@Service
public class ResourceTableServiceImpl extends ServiceImpl<ResourceTableMapper, ResourceTableEntity> implements ResourceTableService {

}
