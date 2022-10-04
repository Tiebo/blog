package com.blogbackend.service.user;

import com.blogbackend.vo.RespResult;

public interface LoginService {
    RespResult getToken(String username, String password);
}
