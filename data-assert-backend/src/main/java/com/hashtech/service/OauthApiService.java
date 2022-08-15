package com.hashtech.service;

import com.hashtech.feign.vo.InternalUserInfoVO;
import com.hashtech.feign.vo.SysOrgRespVO;

import java.util.List;
import java.util.Map;

public interface OauthApiService {

    InternalUserInfoVO getUserById(String userId);

    Map<String, String> orgPage();
}
