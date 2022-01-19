package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author xujie
 * @since 2021-12-01
 */
//@Data
@EqualsAndHashCode(callSuper = false)
@TableName("data_source")
public class DataSourceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据来源描述
     */
    private String name;

    public DataSourceEntity() {
    }

    public DataSourceEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
