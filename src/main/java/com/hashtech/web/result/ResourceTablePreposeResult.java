package com.hashtech.web.result;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @author xujie
 * @description 资源表前置返回值
 * @create 2021-11-29 17:58
 **/
@Data
public class ResourceTablePreposeResult {

    public BaseInfo baseInfo;
    public List<Structure> structureList;
    public List<T>  sampleList;
}
