package com.hashtech.web;

import com.hashtech.config.FileParse;
import com.hashtech.web.result.UploadMinioRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xujie
 * @description 上传minio图片
 * @create 2022-01-17 19:47
 **/
@Slf4j
@RequestMapping("/resource/pic")
@RestController
public class MinioUploadController {

    @Autowired
    private FileParse fileParse;

    @Deprecated
    @PostMapping("/import")
    public String uploadFile(@RequestParam("uploadFile") MultipartFile file) {
        String filePath = fileParse.uploadFile(file);
        return filePath;
    }

    @Deprecated
    @PostMapping("/import/string")
    public String uploadStrinig(@RequestBody UploadMinioRequest request) {
        String filePath = fileParse.uploadFile(request.getFileName(), request.getBase64Url());
        return filePath;
    }
}
