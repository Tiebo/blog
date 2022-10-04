package com.blogbackend.service.user;


import com.blogbackend.vo.RespResult;

public interface RegisterService {
    RespResult register(String username, String password, String confirmPassword);
}
