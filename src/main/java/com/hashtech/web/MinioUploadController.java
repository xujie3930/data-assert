package com.hashtech.web;

import com.hashtech.config.FileParse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xujie
 * @description 上传图片
 * @create 2022-01-17 19:47
 **/
@Slf4j
@RequestMapping("/resource/pic")
public class MinioUploadController {

    @Value("${minio.bucketName}")
    private String bucketName;

    private static final String DIR = "dir";

    @Autowired
    private FileParse fileParse;

    @Transactional(rollbackFor = Exception.class)
    @PostMapping("/uploadFile")
    public String uploadFile(MultipartFile file){
        String filePath = fileParse.uploadFile(file);
        return filePath;
    }
}
