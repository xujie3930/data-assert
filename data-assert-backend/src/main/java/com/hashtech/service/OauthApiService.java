package com.hashtech.service;

import com.hashtech.feign.vo.InternalUserInfoVO;

public interface OauthApiService {

    InternalUserInfoVO getUserById(String userId);
}
