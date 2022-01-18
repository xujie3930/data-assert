package com.hashtech.web.result;

import lombok.Data;

/**
 * @author xujie
 * @description 1
 * @create 2022-01-18 15:10
 **/
@Data
public class UploadMinioRequest {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件base64字符串
     */
    private String base64Url;
}
