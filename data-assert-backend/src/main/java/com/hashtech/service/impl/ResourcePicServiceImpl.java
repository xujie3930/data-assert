package com.hashtech.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.common.AppException;
import com.hashtech.common.ResourceCodeClass;
import com.hashtech.config.FileParse;
import com.hashtech.entity.ResourcePicEntity;
import com.hashtech.mapper.ResourcePicMapper;
import com.hashtech.service.ResourcePicService;
import com.hashtech.utils.AddressUtils;
import com.hashtech.web.result.ResourcePicResult;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author xujie
 * @since 2022-06-02
 */
@Service
public class ResourcePicServiceImpl extends ServiceImpl<ResourcePicMapper, ResourcePicEntity> implements ResourcePicService {

    @Autowired
    private FileParse fileParse;
    @Autowired
    private ResourcePicMapper resourcePicMapper;
    @Value("${server.port}")
    private String port;
    private String CUSTOM  = "/resource/pic/iconDisplay?picUrl=";
    private String HTTP_PRE  = "http://";
    @Override
    public Map<String, String> upload(MultipartFile file) throws Exception{
        String picPath = file.getOriginalFilename();
        checkRepetition(picPath);
        //获取当前服务的内网ip
        String ip = AddressUtils.getInnetIp();
        String picUrl = fileParse.uploadFile(file);
        ResourcePicEntity resourcePicEntity = new ResourcePicEntity();
        resourcePicEntity.setPicPath(picPath);
        picUrl = new StringBuilder(HTTP_PRE).append(ip).append(":").append(port).append(CUSTOM).append(picUrl).toString();
        resourcePicEntity.setPicUrl(picUrl);
        save(resourcePicEntity);
        Map<String, String> map = new HashMap<>();
        map.put("picPath", picPath);
        map.put("picUrl", picUrl);
        return map;
    }

    private void checkRepetition(String picPath) {
        boolean hasExist = BooleanUtils.isTrue(resourcePicMapper.checkRepetition(picPath));
        if (hasExist) {
            throw new AppException(ResourceCodeClass.ResourceCode.RESOURCE_CODE_70000022.getCode());
        }
    }

    @Override
    public void iconDisplay(HttpServletResponse response, String picUrl) {
        fileParse.iconDisplay(response, picUrl);
    }

    @Override
    public List<ResourcePicResult> getList() {
        LinkedList<ResourcePicResult> result = new LinkedList<>();
        List<ResourcePicEntity> list = list();
        //将list拷贝到result中,并转换为result
        list.forEach(item -> {
            ResourcePicResult resourcePicResult = new ResourcePicResult();
            //item的picPath最后一个"."之前的内容
            String picPath = item.getPicPath().substring(0, item.getPicPath().lastIndexOf("."));
            //item的picPath最后一个"."之后的内容
            String picType = item.getPicPath().substring(item.getPicPath().lastIndexOf(".") + 1);
            resourcePicResult.setPicPath(picPath);
            resourcePicResult.setFormat(picType);
            resourcePicResult.setPicUrl(item.getPicUrl());
            resourcePicResult.setId(item.getId());
            result.add(resourcePicResult);
        });
        return result;
    }
}
