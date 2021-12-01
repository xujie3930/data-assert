package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author xujie
 * @since 2021-12-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("data_source")
public class DataSourceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据来源描述
     */
    private String name;


}
