package com.hashtech.web;


import com.hashtech.businessframework.log.Logable;
import com.hashtech.businessframework.result.BusinessResult;
import com.hashtech.service.ResourceTableService;
import com.hashtech.web.request.ResourceSaveRequest;
import com.hashtech.web.request.ThemeSaveRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 主题资源表 前端控制器
 * </p>
 *
 * @author xujie
 * @since 2021-11-23
 */
@RestController
@RequestMapping("/resource")
public class ResourceTableController {

    @Autowired
    private ResourceTableService resourceTableService;

}

