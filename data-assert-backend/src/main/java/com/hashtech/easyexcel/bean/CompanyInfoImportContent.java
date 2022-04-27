package com.hashtech.easyexcel.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * @author xujie
 * @description 公司信息，easyexcel导入实体类
 * @create 2022-04-08 17:39
 **/
public class CompanyInfoImportContent extends BaseRowModel {

    @ExcelProperty(index = 0, value = "统一社会信用代码")
    private String uscc;
    @ExcelProperty(index = 1, value = "企业名称")
    private String corpNm;
    @ExcelProperty(index = 2, value = "企业描述")
    private String describe;

    public CompanyInfoImportContent() {
    }

    public CompanyInfoImportContent(String uscc, String corpNm, String describe) {
        this.uscc = uscc;
        this.corpNm = corpNm;
        this.describe = describe;
    }

    public String getUscc() {
        return uscc;
    }

    public void setUscc(String uscc) {
        this.uscc = uscc;
    }

    public String getCorpNm() {
        return corpNm;
    }

    public void setCorpNm(String corpNm) {
        this.corpNm = corpNm;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
