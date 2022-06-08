package com.hashtech.web.result;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hashtech.entity.ResourcePicEntity;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author xujie
 * @since 2022-06-02
 */
public class ResourcePicResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(type = IdType.ID_WORKER)
    private String id;
    /**
     * 资源图标名称
     */
    private String picPath;

    /**
     * 资源图标格式
     */
    private String format;

    /**
     * 资源图标路径，minio地址
     */
    private String picUrl;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public ResourcePicResult() {
    }

    public ResourcePicResult(String id, String picPath, String format, String picUrl) {
        this.id = id;
        this.picPath = picPath;
        this.format = format;
        this.picUrl = picUrl;
    }
}
