package com.hashtech.web;


import com.hashtech.common.BusinessPageResult;
import com.hashtech.common.BusinessResult;
import com.hashtech.entity.TagEntity;
import com.hashtech.service.TagService;
import com.hashtech.web.request.TagChangeStateRequest;
import com.hashtech.web.request.TagListRequest;
import com.hashtech.web.request.TagSaveRequest;
import com.hashtech.web.request.TagUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;


    @GetMapping("/getTagCode")
    public BusinessResult<String> getTagCode(@RequestParam(value = "len", defaultValue = "8") Integer len) {
        return BusinessResult.success(tagService.getTagCode(len));
    }
    @GetMapping("/hasExistCode")
    public BusinessResult<Boolean> hasExistCode(@RequestParam("code") String code) {
        return BusinessResult.success(tagService.hasExistCode(code));
    }

    @GetMapping("/hasExistName")
    BusinessResult<Boolean> hasExistName(@RequestParam("name") String name) {
        return BusinessResult.success(tagService.hasExistName(name, null));
    }

    @PostMapping("/save")
    BusinessResult<Boolean> saveDef(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody TagSaveRequest request) {
        return BusinessResult.success(tagService.saveDef(userId, request));
    }

    @PostMapping("/update")
    BusinessResult<Boolean> updateDef(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody TagUpdateRequest request) {
        return BusinessResult.success(tagService.updateDef(userId, request));
    }

    @PostMapping("/list")
    BusinessResult<BusinessPageResult> getList(@RequestBody TagListRequest request) {
        return BusinessResult.success(tagService.getList(request));
    }

    @PostMapping("/enOrDisable")
    BusinessResult<Boolean> enOrDisable(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody TagChangeStateRequest request) {
        return BusinessResult.success(tagService.enOrDisable(userId, request));
    }

    @PostMapping("/delete")
    BusinessResult<Boolean> deleteTag(@RequestHeader(value = "userId", defaultValue = "910626036754939904") String userId, @RequestBody String[] ids) {
        return BusinessResult.success(tagService.deleteTag(userId, ids));
    }

    @GetMapping("/detail")
    BusinessResult<TagEntity> detailById(@RequestParam("id") String id) {
        return BusinessResult.success(tagService.detailById(id));
    }

}

