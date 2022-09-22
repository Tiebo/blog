package com.blogbackend.service.user;

import com.blogbackend.vo.Result;

public interface LoginService {
    Result getToken(String username, String password);
}
