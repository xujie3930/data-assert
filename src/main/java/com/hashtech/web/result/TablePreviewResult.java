package com.hashtech.web.result;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 主题资源表
 * </p>
 *
 * @author xujie
 * @since 2021-12-1
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TablePreviewResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 请求示例说明
     */
    private String explainInfo;
}
