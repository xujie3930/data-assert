//package com.hashtech.config;
//
//import io.minio.MakeBucketArgs;
//import io.minio.MinioClient;
//import io.minio.errors.*;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//
///**
// * @author xujie
// * @description 上传minio配置
// * @create 2022-01-17 19:44
// **/
//@Configuration
//public class MinIoClientConfig {
//
//
//    @Value("${minio.host}")
//    private String host;
//    @Value("${minio.accessKey}")
//    private String accessKey;
//    @Value("${minio.secretKey}")
//    private String secretKey;
//
//    /**
//     * 注入minio 客户端
//     * @return
//     */
//    @Bean
//    public MinioClient minioClient() {
//        MinioClient build = new MinioClient.Builder()
//                .endpoint(host)
//                .credentials(accessKey, secretKey)
//                .build();
//        return build;
//    }
//}
