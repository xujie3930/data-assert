package com.hashtech.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hashtech.common.*;
import com.hashtech.entity.TagCategoryEntity;
import com.hashtech.entity.TagCategoryRelationEntity;
import com.hashtech.entity.TagEntity;
import com.hashtech.feign.vo.InternalUserInfoVO;
import com.hashtech.mapper.TagCategoryMapper;
import com.hashtech.service.OauthApiService;
import com.hashtech.service.TagCategoryRelationService;
import com.hashtech.service.TagCategoryService;
import com.hashtech.service.TagService;
import com.hashtech.web.request.TagCategoryRelationRequest;
import com.hashtech.web.request.TagCategoryRequest;
import com.hashtech.web.request.TagListRequest;
import com.hashtech.web.result.TagCategoryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TagCategoryServiceImpl extends ServiceImpl<TagCategoryMapper, TagCategoryEntity> implements TagCategoryService {

    @Autowired
    private OauthApiService oauthApiService;
    @Autowired
    private TagCategoryMapper tagCategoryMapper;
    @Autowired
    private TagCategoryRelationService tagCategoryRelationService;
    @Autowired
    private TagService tagService;
    @Override
    public List<TagCategoryResult> queryPageList(TagListRequest request) {
        List<TagCategoryResult> tagCategoryResults = tagCategoryMapper.queryPageList(request);
        return tagCategoryResults==null?new ArrayList<>(0):tagCategoryResults;
    }

    @Override
    public BusinessResult<Boolean> saveDef(String userId, TagCategoryRequest request) {
        if(!StringUtils.isEmpty(userId)){
            InternalUserInfoVO user = oauthApiService.getUserById(userId);
            request.setCreateUserId(userId);
            request.setUpdateUserId(userId);
            if(null!=user){
                request.setCreateBy(user.getUsername());
                request.setUpdateBy(user.getUsername());
            }
        }
        if(request.getPid().longValue()>0l){
            //有上级，判断上级是否存在
            TagCategoryEntity pentity = getById(request.getPid());
            if(null==pentity){
                ResourceCodeClass.ResourceCode rc10000000 = ResourceCodeClass.ResourceCode.RESOURCE_CODE_10000000;
                return BusinessResult.fail(rc10000000.code, rc10000000.message+":上级不存在");
            }
            //设置层数
            request.setLevel(pentity.getLevel()==null?1:Integer.valueOf(pentity.getLevel().intValue()+1).shortValue());
        }else{
            //自己就是父级
            request.setPid(0l);
            request.setLevel(Integer.valueOf(1).shortValue());
        }

        TagCategoryEntity entity = new TagCategoryEntity();
        BeanCopyUtils.copyProperties(request, entity);
        return BusinessResult.success(save(entity));
    }

    @Override
    public BusinessResult<Boolean> updateDef(String userId, TagCategoryRequest request) {
        if(!StringUtils.isEmpty(userId)){
            InternalUserInfoVO user = oauthApiService.getUserById(userId);
            request.setUpdateUserId(userId);
            request.setUpdateBy(null==user?null:user.getUsername());
        }
        TagCategoryEntity entity = getById(request.getId());
        if(request.getPid()!=null && request.getPid().longValue()!=entity.getPid().intValue()){
            //有上级，判断上级是否存在
            TagCategoryEntity pentity = getById(request.getPid());
            if(null==pentity){
                ResourceCodeClass.ResourceCode rc10000000 = ResourceCodeClass.ResourceCode.RESOURCE_CODE_10000000;
                return BusinessResult.fail(rc10000000.code, rc10000000.message+":上级不存在");
            }
            //设置层数
            request.setLevel(pentity.getLevel()==null?1:Integer.valueOf(pentity.getLevel().intValue()+1).shortValue());
        }else{
            request.setLevel(entity.getLevel());
        }


        if(request.getLevel().intValue()!=entity.getLevel().intValue()){
            //由于level改变，需要同步更新该节点下所有子节点的level
            //查询所有子孙节点
            Set<Long> ids = new HashSet<>();
            Map<Long, Short> updateMap = new HashMap<>();
            ids.add(request.getId());
            for(int i=1;i<=50;i++){
                QueryWrapper<TagCategoryEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.in("pid", ids);
                List<TagCategoryEntity> sentitys = list(queryWrapper);
                ids.clear();
                if(null==sentitys || sentitys.isEmpty()){
                    break;
                }
                for(TagCategoryEntity sentity:sentitys){
                    ids.add(sentity.getId());
                    updateMap.put(sentity.getId(), Integer.valueOf(request.getLevel().intValue()+i).shortValue());
                }
            }
            if(!updateMap.isEmpty()){
                //更新层数
                tagCategoryMapper.batchUpdateLevel(updateMap);
            }
        }

        TagCategoryEntity updateEntity = new TagCategoryEntity();
        BeanCopyUtils.copyProperties(request, updateEntity);
        if(updateById(updateEntity)){
            return BusinessResult.success(true);
        }else{
            //存在启用的TAG
            ResourceCodeClass.ResourceCode rc10000006 = ResourceCodeClass.ResourceCode.RESOURCE_CODE_10000006;
            throw new AppException(rc10000006.code, rc10000006.message);
        }
    }

    @Override
    @Transactional
    public BusinessResult<Boolean> deleteDef(Long id) {
        //查询分类对应的标签
        QueryWrapper<TagCategoryRelationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag", DelFlagEnum.ENA_BLED.getCode().byteValue());
        wrapper.eq("tag_category_id", id);

        List<TagCategoryRelationEntity> relations = tagCategoryRelationService.list(wrapper);
        if(null!=relations && !relations.isEmpty()){
            Set<String> tagIds = relations.stream().map(TagCategoryRelationEntity::getTagId).collect(Collectors.toSet());
            Collection<TagEntity> tags = tagService.listByIds(tagIds);
            if(null!=tags && !tags.isEmpty() && tags.stream().filter(e->(e.getState()+"").equals("1")).findAny().isPresent()){
                //存在启用的TAG
                ResourceCodeClass.ResourceCode rc10000005 = ResourceCodeClass.ResourceCode.RESOURCE_CODE_10000005;
                return BusinessResult.fail(rc10000005.code, rc10000005.message+":存在已启用的标签");
            }
            //删除关联表
            TagCategoryRelationRequest tcrRequest = new TagCategoryRelationRequest();
            tcrRequest.setTagCategoryId(id);
            if(tagCategoryRelationService.deletes(tcrRequest)<1){
                ResourceCodeClass.ResourceCode rc10000005 = ResourceCodeClass.ResourceCode.RESOURCE_CODE_10000005;
                return BusinessResult.fail(rc10000005.code, rc10000005.message);
            }
        }

        //删除
        TagCategoryEntity updateEntity = new TagCategoryEntity();
        updateEntity.setId(id);
        updateEntity.setDelFlag(DelFlagEnum.DIS_ABLED.getCode().byteValue());
        if(updateById(updateEntity)){
            return BusinessResult.success(true);
        }else{
            //存在启用的TAG
            ResourceCodeClass.ResourceCode rc10000005 = ResourceCodeClass.ResourceCode.RESOURCE_CODE_10000005;
            throw new AppException(rc10000005.code, rc10000005.message);
        }
    }
}
