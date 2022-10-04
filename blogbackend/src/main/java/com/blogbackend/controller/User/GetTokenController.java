package com.blogbackend.controller.User;

import com.blogbackend.service.user.LoginService;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetTokenController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/user/account/token/")
    public RespResult getToken(@RequestParam Map<String, String> data) {
        String username = data.get("username");
        String password = data.get("password");
        return loginService.getToken(username, password);
    }
}