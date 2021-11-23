package com.hashtech;

import com.hashtech.businessframework.sequence.configuration.EnableSequenceService;
import com.hashtech.businessframework.validate.EnableValidator;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Peng
 */
@SpringBootApplication
@EnableSequenceService
@EnableValidator
@EnableScheduling
@MapperScan(basePackages = {"com.hashtech.**.mapper"})
@EnableAspectJAutoProxy(exposeProxy = true)//exposeProxy类内部可以获取到当前类的代理对象
public class DataAssertApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(DataAssertApplication.class);
        springApplication.run(args);
    }
}
