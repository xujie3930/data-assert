package com.hashtech.web;


import com.hashtech.common.BusinessPageResult;
import com.hashtech.common.BusinessResult;
import com.hashtech.entity.TagEntity;
import com.hashtech.service.TagService;
import com.hashtech.web.request.*;
import com.hashtech.web.result.TagRelateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
@RestController
@RequestMapping("/tag/category")
public class TagCategoryController {

    @Autowired
    private TagService tagService;

}

