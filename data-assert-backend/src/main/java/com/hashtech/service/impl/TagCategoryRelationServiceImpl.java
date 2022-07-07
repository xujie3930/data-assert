package com.hashtech.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.entity.TagCategoryRelationEntity;
import com.hashtech.mapper.TagCategoryRelationMapper;
import com.hashtech.service.TagCategoryRelationService;
import com.hashtech.web.request.TagCategoryRelationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagCategoryRelationServiceImpl  extends ServiceImpl<TagCategoryRelationMapper, TagCategoryRelationEntity> implements TagCategoryRelationService {

    @Autowired
    private TagCategoryRelationMapper tagCategoryRelationMapper;
    @Override
    public Integer deletes(TagCategoryRelationRequest request) {
        return tagCategoryRelationMapper.deletes(request);
    }
}
