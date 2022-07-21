package com.hashtech.config;

import com.hashtech.common.AppException;
import io.minio.*;
import io.minio.http.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Component
public class FileParse {
    private static final String DIR = "backend";
    private static final String PREFIX = "data:image/svg+xml;base64,";
    private static final String POSTFIX = ".svg";
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MinioClient minioClient;
    @Value("${minio.bucketName}")
    private String bucketName;

    public String uploadFile(MultipartFile file) {

        String filePath = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String path = LocalDateTime.now().format(formatter);
            String fileName = file.getOriginalFilename();
            fileName = DIR + '/' + path + '/' + fileName;
            PutObjectArgs objectArgs = PutObjectArgs.builder().object(fileName)
                    .bucket(bucketName)
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(), file.getSize(), -1).build();
            minioClient.putObject(objectArgs);
            log.info("文件上传成功");
            filePath = fileName;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("文件上传失败，请重新上传");
            throw new AppException("文件上传失败");
        }
        return filePath;
    }

    public String uploadFile(String fileName, String str) {
        String filePath = null;
        try {
            String str64 = str.replace(PREFIX, "");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String path = LocalDateTime.now().format(formatter);
            fileName = DIR + '/' + path + '/' + fileName + POSTFIX;
            byte[] bytes = DatatypeConverter.parseBase64Binary(str64);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            PutObjectArgs objectArgs = PutObjectArgs.builder().object(fileName)
                    .bucket(bucketName)
                    .contentType("file")
                    .stream(inputStream, inputStream.available(), -1).build();
            minioClient.putObject(objectArgs);
            log.info("文件上传成功");
            filePath = fileName;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("文件上传失败，请重新上传");
            throw new AppException("文件上传失败");
        }
        return filePath;
    }

    public void iconDisplay(HttpServletResponse res, String iconPath) {
        GetObjectArgs objectArgs = GetObjectArgs.builder().bucket(bucketName).object(iconPath).build();
        try (GetObjectResponse response = minioClient.getObject(objectArgs)){
            OutputStream outputStream = res.getOutputStream();
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()){
                while ((len=response.read(buf))!=-1){
                    os.write(buf,0,len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                FileCopyUtils.copy(bytes,outputStream);
                res.setContentType("jpg=image/jpeg");
                res.setHeader("Access-Control-Allow-Headers", "*");
                res.setHeader("Access-Control-Allow-Origin", "*");
                res.setHeader("Cache-Control","no-cache");
                //跨域 Header
                res.setHeader("Access-Control-Allow-Methods", "*");
                res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, x-sso-token,token");
                res.setHeader("Access-Control-Request-Headers", "Origin, X-Requested-With, content-Type, Accept, Authorization, x-sso-token,token");
                //这里设置Credentials为true 也可以前端设置为false
                res.setHeader("Access-Control-Allow-Credentials", "true");
                // 浏览器是会先发一次options请求，如果请求通过，则继续发送正式的post请求
                // 配置options的请求返回
//                if (res.getMethod().equals("OPTIONS")) {
//                    res.setStatus(HttpStatus.OK.value());
//                    res.getWriter().write("OPTIONS returns OK");
//
//                }
                outputStream.flush();

            }
        } catch (Exception e) {
            e.printStackTrace();
//            throw new AppException(JodpErrorConstant.DOWNLOAD_FILE_FAIL.code,JodpErrorConstant.DOWNLOAD_FILE_FAIL.message);
        }
    }

    public String getPreviewFileUrl(String fileName) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET).bucket(bucketName).object(fileName
).expiry(2000,
                            TimeUnit.MINUTES).build());
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

}
