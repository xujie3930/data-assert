package com.hashtech;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Peng
 */
@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = {"com.hashtech.**.mapper"})
@EnableAspectJAutoProxy(exposeProxy = true)//exposeProxy类内部可以获取到当前类的代理对象
@EnableDiscoveryClient
@ComponentScan(value = "com.hashtech", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = {
                "com.hashtech.businessframework.result.FilterConfig"
        })
})
@EnableFeignClients
public class TagApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(TagApplication.class);
        springApplication.run(args);
    }
}
