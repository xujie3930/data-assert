package com.hashtech.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hjs
 */
@Configuration
public class MinIoClientConfig {


    @Value("${minio.host}")
    private String host;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;

    /**
     * 注入minio 客户端
     *
     * @return
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(host)
                .credentials(accessKey, secretKey)
                .build();
    }
}
