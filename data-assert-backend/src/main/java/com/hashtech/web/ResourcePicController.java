package com.hashtech.web;


import com.hashtech.common.BusinessResult;
import com.hashtech.common.ResourceCodeClass;
import com.hashtech.entity.ResourcePicEntity;
import com.hashtech.service.ResourcePicService;
import com.hashtech.utils.ObjectUtils;
import com.hashtech.utils.StringUtils;
import com.hashtech.web.result.ResourcePicResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author xujie
 * @since 2022-06-02
 */
@Controller
@RequestMapping("/resource/pic")
public class ResourcePicController {

    @Autowired
    private ResourcePicService resourcePicService;

    @ResponseBody
    @PostMapping("/upload")
    public BusinessResult<Map<String, String>> uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws Exception {
        if((!StringUtils.checkFileType(file, "png", "jpg", "jpeg", "bmp")) || (!ObjectUtils.isImg(file))){
            ResourceCodeClass.ResourceCode rc70000030 = ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000030;
            return BusinessResult.fail(rc70000030.getCode(), rc70000030.getMessage());
        }
        Map<String, String> map = resourcePicService.upload(request, file);
        return BusinessResult.success(map);
    }

    @ResponseBody
    @GetMapping("/list")
    public BusinessResult<List<ResourcePicResult>> uploadFile() {
        List<ResourcePicResult> list = resourcePicService.getList();
        return BusinessResult.success(list);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/iconDisplay")
    public void iconDisplay(HttpServletResponse response, @RequestParam("picUrl") String picUrl) {
        resourcePicService.iconDisplay(response, picUrl);
    }
}

