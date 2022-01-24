package com.hashtech.web.request;

/**
 * @author xujie
 * @description 判断资源表编号是否重复
 * @create 2022-01-05 16:15
 **/
//@Data
public class HasExitSerialNumRequest {
    /**
     * 资源表编号
     */
    private String serialNum;
    /**
     * 资源表id
     */
    private String id;

    public HasExitSerialNumRequest() {
    }

    public HasExitSerialNumRequest(String serialNum, String id) {
        this.serialNum = serialNum;
        this.id = id;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
