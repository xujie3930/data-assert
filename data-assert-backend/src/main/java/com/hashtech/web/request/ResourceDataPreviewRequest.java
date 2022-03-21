package com.hashtech.web.request;

import com.hashtech.common.BusinessBasePageForm;

public class ResourceDataPreviewRequest extends BusinessBasePageForm {

    private String resourceTableId;

    public String getResourceTableId() {
        return resourceTableId;
    }

    public void setResourceTableId(String resourceTableId) {
        this.resourceTableId = resourceTableId;
    }
}
