package com.hashtech.web.request;

/**
 * @author xujie
 * @description 用于判断主题、资源能否编辑（存在在开放平台开放的表时候，不支持编辑）
 * @create 2022-01-05 19:06
 **/
//@Data
public class ExistInterfaceNamelRequest {
    /**
     * 接口名称
     */
    private String interfaceName;

    /**
     * 主键id
     */
    private String id;

    public ExistInterfaceNamelRequest() {
    }

    public ExistInterfaceNamelRequest(String interfaceName, String id) {
        this.interfaceName = interfaceName;
        this.id = id;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
