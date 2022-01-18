//package com.hashtech.web;
//
//import com.hashtech.businessframework.exception.interval.AppException;
//import io.minio.MinioClient;
//import io.minio.PutObjectArgs;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.Resource;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
///**
// * @author xujie
// * @description 上传图片
// * @create 2022-01-17 19:47
// **/
//@Slf4j
//@RequestMapping("/resource/pic")
//public class MinioUploadController {
//
//    @Value("${minio.bucketName}")
//    private String bucketName;
//
//    private static final String DIR = "dir";
//
//    @Resource
//    private MinioClient minioClient;
//
//    @PostMapping("/uploadFile")
//    public String uploadFileToMinio(MultipartFile file){
//
//            String filePath = uploadFile(file);
//            return filePath;
//    }
//
//    public String uploadFile(MultipartFile file) {
//
//        String filePath;
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//            String path = LocalDateTime.now().format(formatter);
//            String fileName = file.getOriginalFilename();
//            fileName = DIR + '/' + path + '/' + fileName;
//            PutObjectArgs objectArgs = PutObjectArgs.builder().object(fileName)
//                    .bucket(bucketName)
//                    .contentType(file.getContentType())
//                    .stream(file.getInputStream(),file.getSize(),-1).build();
//            minioClient.putObject(objectArgs);
//            log.info("文件上传成功");
//            filePath =  fileName;
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("文件上传失败，请重新上传");
//            throw new AppException("文件上传失败");
//        }
//        return filePath;
//    }
//}
