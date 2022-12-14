package com.hashtech.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hashtech.entity.ResourceTableEntity;
import com.hashtech.web.request.ResourceTableNameRequest;
import com.hashtech.web.request.ResourceTablePageListRequest;
import com.hashtech.web.result.StatisticsResourceTableResult;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 主题资源表 Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2021-11-23
 */
public interface ResourceTableMapper extends BaseMapper<ResourceTableEntity> {

    int deleteByResourceId(@Param("id") String id);

    List<ResourceTableEntity> getListByResourceId(@Param("resourceId") String id);

    List<ResourceTableEntity> queryPage(Page<ResourceTableEntity> page, @Param(value = "request") ResourceTablePageListRequest request);

    ResourceTableEntity getByResourceTableId(@Param("resourceTableId") String resourceTableId);
    String getChineseName(@Param("name") String name);
    Long getCountDataSizeResourceId(@Param("resourceId") String id);

    Boolean hasExitExternalStateByResourceIds(@Param("resourceIds") String[] ids, @Param("externalState") Integer externalState);

    Boolean hasExitExternalStateByIds(@Param("ids") String[] ids, @Param("externalState") Integer externalState);

    Boolean checkHasExitResourceTable(@Param("name") String name, @Param("resourceId") String resourceId, @Param("resourceTableId") String resourceTableId);

    Boolean hasExitSerialNum(@Param("serialNum") String serialNum, @Param("id") String id);

    Boolean checkHasExitResourceTableInAll(@Param("name") String name, @Param("datasourceId") String datasourceId, @Param("id") String id);

    Boolean checkTableHasExist(@Param("chineseName") String chineseName, @Param("id") String id);

    ResourceTableEntity getByDatasourceIdAndName(@Param("request") ResourceTableNameRequest request);

    void updateThemIdByResourceId(@Param("themeId") String themeId, @Param("resourceId") String resourceId);

    void updateThemIdByResourceIds(@Param("themeId") String themeId, @Param("resourceIds")String[] resourceIds);

    void updateMasterDataByResourceIds(@Param("masterDataFlag")Integer code, @Param("masterDataId")Integer id, @Param("resourceIds")String[] resourceIds);

    StatisticsResourceTableResult statisticsResourceTableInfo(@Param("delFlag") String delFlag);

    List<ResourceTableEntity> getList(@Param("delFlag") String delFlag);

    Long countByMasterData(@Param("delFlag") String desc);

    List<ResourceTableEntity> newlyDayList(@Param("createTime") Date date);
}
