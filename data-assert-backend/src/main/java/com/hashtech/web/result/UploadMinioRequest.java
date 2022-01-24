package com.hashtech.web.result;

/**
 * @author xujie
 * @description 1
 * @create 2022-01-18 15:10
 **/
//@Data
public class UploadMinioRequest {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件base64字符串
     */
    private String base64Url;

    public UploadMinioRequest() {
    }

    public UploadMinioRequest(String fileName, String base64Url) {
        this.fileName = fileName;
        this.base64Url = base64Url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBase64Url() {
        return base64Url;
    }

    public void setBase64Url(String base64Url) {
        this.base64Url = base64Url;
    }
}
