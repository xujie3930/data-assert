package com.hashtech.utils.druid;

import com.alibaba.druid.pool.DruidDataSource;

import java.util.Date;

/**
 * Project：icreditstudio-dataapi-service
 * Package：com.jinninghui.datasphere.icreditstudio.dataapi.druid
 * ClassName: AbstractDataApiDruidDataSource
 * Description:  AbstractDataApiDruidDataSource
 * Date: 2022/5/11 12:09
 *
 * @author liyanhui
 */
public class DataApiDruidDataSource {

    private final DruidDataSource dataSource;

    public DataApiDruidDataSource(DruidDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DruidDataSource getDruidDataSource() {
        return dataSource;
    }

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 最新一次使用时间
     */
    private Date lastUseDate;


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public Date getLastUseDate() {
        return lastUseDate;
    }

    public void setLastUseDate(Date lastUseDate) {
        this.lastUseDate = lastUseDate;
    }
}
