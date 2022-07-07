package com.hashtech.web;


import com.hashtech.common.BusinessPageResult;
import com.hashtech.common.BusinessResult;
import com.hashtech.common.ResourceCodeClass;
import com.hashtech.entity.TagEntity;
import com.hashtech.service.TagCategoryService;
import com.hashtech.service.TagService;
import com.hashtech.web.request.*;
import com.hashtech.web.result.TagCategoryResult;
import com.hashtech.web.result.TagRelateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
    private TagCategoryService tagCategoryService;

    @PostMapping("/list")
    BusinessResult<List<TagCategoryResult>> getList(@RequestBody TagListRequest request) {
        //避免空字符串，方便mybatis判断
        if(StringUtils.isEmpty(request.getName())){
            request.setName(null);
        }
        if(StringUtils.isEmpty(request.getCategoryName())){
            request.setCategoryName(null);
        }
        if(StringUtils.isEmpty(request.getLastUsedTimeBegin())){
            request.setLastUsedTimeBegin(null);
        }
        if(StringUtils.isEmpty(request.getLastUsedTimeEnd())){
            request.setLastUsedTimeEnd(null);
        }
        //end



        return BusinessResult.success(tagCategoryService.queryPageList(request));
    }

    @PostMapping("/save")
    BusinessResult<Boolean> saveDef(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody TagCategoryRequest request) {
        request.setId(null);
        return tagCategoryService.saveDef(userId, request);
    }

    @PostMapping("/update")
    BusinessResult<Boolean> updateDef(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody TagCategoryRequest request) {
        ResourceCodeClass.ResourceCode rc10000000 = ResourceCodeClass.ResourceCode.RESOURCE_CODE_10000000;
        if(null==request.getId()){
            return BusinessResult.fail(rc10000000.code, rc10000000.message+":缺少主键");
        }
        return tagCategoryService.updateDef(userId, request);
    }

    @PostMapping("/delete")
    BusinessResult<Boolean> deleteDef(@RequestParam Long id) {
        return tagCategoryService.deleteDef(id);
    }
}

