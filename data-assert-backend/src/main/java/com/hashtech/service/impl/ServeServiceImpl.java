package com.hashtech.service.impl;

import com.hashtech.feign.ServeFeignClient;
import com.hashtech.service.ServeService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xujie
 * @description 开放平台方法类
 * @create 2022-08-26 12:38
 **/
@Service
public class ServeServiceImpl implements ServeService {

    @Resource
    private ServeFeignClient serveFeignClient;

    @Override
    @Async
    public void asyncSourceDirInfo(String id){
        serveFeignClient.asyncSourceDirInfo(id);
    }
}
