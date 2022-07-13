package com.hashtech.web.request;

/**
 * @author xujie
 * @description 保存公司信息
 * @create 2022-04-07 11:08
 **/

import java.util.List;

//@Data
public class CompanyHasExistRequest {

    //企业统一社会信用代码
    private String uscc;
    //产业库id
    private List<String> industrialIds;

    public CompanyHasExistRequest() {
    }

    public CompanyHasExistRequest(String uscc, List<String> industrialIds) {
        this.uscc = uscc;
        this.industrialIds = industrialIds;
    }

    public String getUscc() {
        return uscc;
    }

    public void setUscc(String uscc) {
        this.uscc = uscc;
    }

    public List<String> getIndustrialIds() {
        return industrialIds;
    }

    public void setIndustrialIds(List<String> industrialIds) {
        this.industrialIds = industrialIds;
    }
}

