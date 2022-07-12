package com.hashtech.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 上传企业信息表 服务类
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
public interface UploadImportService {

    Boolean uploadImport(String userId, MultipartFile file, String ids, String industrialIds);
}
