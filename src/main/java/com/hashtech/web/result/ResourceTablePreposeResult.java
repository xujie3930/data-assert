package com.hashtech.web.result;

import com.hashtech.businessframework.result.BusinessPageResult;
import lombok.Data;

import java.util.List;

/**
 * @author xujie
 * @description 资源表前置返回值
 * @create 2021-11-29 17:58
 **/
@Data
public class ResourceTablePreposeResult {

    //基础信息
    public BaseInfo baseInfo;
    //表结构
    public List<Structure> structureList;
    //采样数据
    public BusinessPageResult<Object> sampleList;
}
