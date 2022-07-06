package com.hashtech.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.entity.TagCategoryEntity;
import com.hashtech.mapper.TagCategoryMapper;
import com.hashtech.service.TagCategoryService;
import com.hashtech.web.request.TagListRequest;
import com.hashtech.web.result.TagCategoryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagCategoryServiceImpl extends ServiceImpl<TagCategoryMapper, TagCategoryEntity> implements TagCategoryService {

    @Autowired
    private TagCategoryMapper tagCategoryMapper;
    @Override
    public List<TagCategoryResult> queryList(TagListRequest request) {
        return null;
    }
}
