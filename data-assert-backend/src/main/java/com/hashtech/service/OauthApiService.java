package com.hashtech.service;

import com.hashtech.feign.vo.InternalUserInfoVO;
import com.hashtech.feign.vo.SysOrgRespVO;

import java.util.List;

public interface OauthApiService {

    InternalUserInfoVO getUserById(String userId);

    List<SysOrgRespVO> orgPage();
}
