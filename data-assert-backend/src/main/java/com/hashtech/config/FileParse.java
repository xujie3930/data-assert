package com.hashtech.config;

import com.hashtech.common.AppException;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
                outputStream.flush();

            }
        } catch (Exception e) {
            e.printStackTrace();
//            throw new AppException(JodpErrorConstant.DOWNLOAD_FILE_FAIL.code,JodpErrorConstant.DOWNLOAD_FILE_FAIL.message);
        }
    }
}
