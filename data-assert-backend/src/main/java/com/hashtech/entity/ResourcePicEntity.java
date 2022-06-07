package com.hashtech.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author xujie
 * @since 2022-06-02
 */
@TableName("resource_pic")
public class ResourcePicEntity implements Serializable {

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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public ResourcePicEntity() {
    }

    public ResourcePicEntity(String id, String picPath, String picUrl) {
        this.id = id;
        this.picPath = picPath;
        this.picUrl = picUrl;
    }
}
