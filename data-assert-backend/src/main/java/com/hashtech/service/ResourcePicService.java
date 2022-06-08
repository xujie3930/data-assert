package com.hashtech.service;

import com.hashtech.entity.ResourcePicEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hashtech.web.result.ResourcePicResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author xujie
 * @since 2022-06-02
 */
public interface ResourcePicService extends IService<ResourcePicEntity> {

    Map<String, String> upload(MultipartFile file) throws Exception;

    void iconDisplay(HttpServletResponse response, String picUrl);

    List<ResourcePicResult> getList();
}
