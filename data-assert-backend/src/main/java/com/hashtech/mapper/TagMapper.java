package com.hashtech.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hashtech.entity.TagEntity;
import com.hashtech.web.request.TagListRequest;
import com.hashtech.web.result.TagResult;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 标签表 Mapper 接口
 * </p>
 *
 * @author xujie
 * @since 2022-04-06
 */
public interface TagMapper extends BaseMapper<TagEntity> {

    Boolean hasExistCode(@Param("code") String code);

    Boolean hasExistName(@Param("name") String name, @Param("id") String id);

    TagEntity findById(@Param("id") String id);

    List<TagEntity> selectByIds(@Param("ids") List<String> ids);

    Long selectCountByStateAndIds(@Param("state") Integer state, @Param("ids") String[] ids);

    List<TagEntity> getListWithoutPaging();

    List<TagEntity> getByCompanyId(@Param("companyId") String id);
    List<Map<String, Object>> getByCompanyIds(@Param("list") Collection<String> ids);

    void updateUsedTimeById(@Param("count") Long count, @Param("tagId") String tagId);


    Integer queryPageDataCount(TagListRequest request);

    List<TagResult> queryPageDataList(TagListRequest request);
}
