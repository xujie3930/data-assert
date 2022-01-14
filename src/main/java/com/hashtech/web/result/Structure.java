package com.hashtech.web.result;

import lombok.Data;

/**
 * @author xujie
 * @description
 * @create 2021-11-29 18:58
 **/
@Data
public class Structure {

    //字段英文名称
    private String fieldEnglishName;
    //字段中文名称
    private String fieldChineseName;
    //表英文名称
    private String tableEnglishName;
    //表中文名称
    private String tableChineseName;
    //字段类型
    private String type;
    //是否必填
    private Boolean required = true;
}
