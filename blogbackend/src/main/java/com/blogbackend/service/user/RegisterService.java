package com.blogbackend.service.user;


import com.blogbackend.vo.Result;

public interface RegisterService {
    Result register(String username, String password, String confirmPassword);
}
