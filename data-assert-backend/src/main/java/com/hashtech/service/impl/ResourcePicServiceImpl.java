package com.hashtech.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.common.AppException;
import com.hashtech.common.ResourceCodeClass;
import com.hashtech.config.FileParse;
import com.hashtech.entity.ResourcePicEntity;
import com.hashtech.mapper.ResourcePicMapper;
import com.hashtech.service.ResourcePicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.HashMap;
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

    private final String PIC_FORMAT = ".svg";
    @Autowired
    private FileParse fileParse;
    @Value("${server.port}")
    private String port;
    private String CUSTOM  = "/resource/pic/iconDisplay?picUrl=";
    @Override
    public Map<String, String> upload(MultipartFile file) throws Exception{
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        System.out.println(ip);
        System.out.println(port);
        String picUrl = fileParse.uploadFile(file);
        String picPath = file.getOriginalFilename();
        ResourcePicEntity resourcePicEntity = new ResourcePicEntity();
        resourcePicEntity.setPicPath(picPath);
        picUrl = new StringBuilder(ip).append(":").append(port).append(CUSTOM).append(picUrl).toString();
        resourcePicEntity.setPicUrl(picUrl);
        save(resourcePicEntity);
        Map<String, String> map = new HashMap<>();
        map.put("picPath", picPath);
        map.put("picUrl", picUrl);
        return map;
    }
}
