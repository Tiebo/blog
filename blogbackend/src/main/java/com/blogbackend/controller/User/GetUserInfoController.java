package com.blogbackend.controller.User;

import com.blogbackend.service.user.GetUserInfoService;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetUserInfoController {
    @Autowired
    private GetUserInfoService getUserInfoService;

    @GetMapping("/user/account/info/")
    public Result getUserInfo() {
        return getUserInfoService.getUserInfo();
    }
}
