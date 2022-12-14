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
            //????????????????????????????????????
            TagCategoryEntity pentity = getById(request.getPid());
            if(null==pentity){
                ResourceCodeClass.ResourceCode rc10000000 = ResourceCodeClass.ResourceCode.RESOURCE_CODE_10000000;
                return BusinessResult.fail(rc10000000.code, rc10000000.message+":???????????????");
            }
            //????????????
            request.setLevel(pentity.getLevel()==null?1:Integer.valueOf(pentity.getLevel().intValue()+1).shortValue());
        }else{
            //??????????????????
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
            //????????????????????????????????????
            TagCategoryEntity pentity = getById(request.getPid());
            if(null==pentity){
                ResourceCodeClass.ResourceCode rc10000000 = ResourceCodeClass.ResourceCode.RESOURCE_CODE_10000000;
                return BusinessResult.fail(rc10000000.code, rc10000000.message+":???????????????");
            }
            //????????????
            request.setLevel(pentity.getLevel()==null?1:Integer.valueOf(pentity.getLevel().intValue()+1).shortValue());
        }else{
            request.setLevel(entity.getLevel());
        }


        if(request.getLevel().intValue()!=entity.getLevel().intValue()){
            //??????level?????????????????????????????????????????????????????????level
            //????????????????????????
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
                //????????????
                tagCategoryMapper.batchUpdateLevel(updateMap);
            }
        }

        TagCategoryEntity updateEntity = new TagCategoryEntity();
        BeanCopyUtils.copyProperties(request, updateEntity);
        if(updateById(updateEntity)){
            return BusinessResult.success(true);
        }else{
            //???????????????TAG
            ResourceCodeClass.ResourceCode rc10000006 = ResourceCodeClass.ResourceCode.RESOURCE_CODE_10000006;
            throw new AppException(rc10000006.code, rc10000006.message);
        }
    }

    @Override
    @Transactional
    public BusinessResult<Boolean> deleteDef(Long id) {
        //???????????????????????????
        QueryWrapper<TagCategoryRelationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag", DelFlagEnum.ENA_BLED.getCode().byteValue());
        wrapper.eq("tag_category_id", id);

        List<TagCategoryRelationEntity> relations = tagCategoryRelationService.list(wrapper);
        if(null!=relations && !relations.isEmpty()){
            Set<String> tagIds = relations.stream().map(TagCategoryRelationEntity::getTagId).collect(Collectors.toSet());
            Collection<TagEntity> tags = tagService.listByIds(tagIds);
            if(null!=tags && !tags.isEmpty() && tags.stream().filter(e->(e.getState()+"").equals("1")).findAny().isPresent()){
                //???????????????TAG
                ResourceCodeClass.ResourceCode rc10000005 = ResourceCodeClass.ResourceCode.RESOURCE_CODE_10000005;
                return BusinessResult.fail(rc10000005.code, rc10000005.message+":????????????????????????");
            }
            //???????????????
            TagCategoryRelationRequest tcrRequest = new TagCategoryRelationRequest();
            tcrRequest.setTagCategoryId(id);
            if(tagCategoryRelationService.deletes(tcrRequest)<1){
                ResourceCodeClass.ResourceCode rc10000005 = ResourceCodeClass.ResourceCode.RESOURCE_CODE_10000005;
                return BusinessResult.fail(rc10000005.code, rc10000005.message);
            }
        }

        //??????
        TagCategoryEntity updateEntity = new TagCategoryEntity();
        updateEntity.setId(id);
        updateEntity.setDelFlag(DelFlagEnum.DIS_ABLED.getCode().byteValue());
        if(updateById(updateEntity)){
            return BusinessResult.success(true);
        }else{
            //???????????????TAG
            ResourceCodeClass.ResourceCode rc10000005 = ResourceCodeClass.ResourceCode.RESOURCE_CODE_10000005;
            throw new AppException(rc10000005.code, rc10000005.message);
        }
    }
}
