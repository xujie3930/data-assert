package com.hashtech.web.result;

import com.hashtech.common.BusinessPageResult;
import com.hashtech.entity.TagEntity;

/**
 * @author xujie
 * @description 标签关联企业返回
 * @create 2022-04-11 15:56
 **/
public class TagRelateResult {

    private TagEntity tagEntity;

    private BusinessPageResult businessPageResult;

    public TagRelateResult() {
    }

    public TagRelateResult(TagEntity tagEntity, BusinessPageResult businessPageResult) {
        this.tagEntity = tagEntity;
        this.businessPageResult = businessPageResult;
    }

    public TagEntity getTagEntity() {
        return tagEntity;
    }

    public void setTagEntity(TagEntity tagEntity) {
        this.tagEntity = tagEntity;
    }

    public BusinessPageResult getBusinessPageResult() {
        return businessPageResult;
    }

    public void setBusinessPageResult(BusinessPageResult businessPageResult) {
        this.businessPageResult = businessPageResult;
    }
}
