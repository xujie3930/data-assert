package com.hashtech.web.request;

import lombok.Data;

/**
 * @author xujie
 * @description 根据表明获取源库信息
 * @create 2021-11-29 17:40
 **/
@Data
public class ResourceTablePreposeRequest {
    private String tableName;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
