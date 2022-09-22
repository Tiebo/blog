package com.blogbackend.service.impl.user;

import com.alibaba.fastjson.JSONObject;
import com.blogbackend.dao.pojo.User;
import com.blogbackend.service.impl.utils.UserDetailsImpl;
import com.blogbackend.service.user.GetUserInfoService;
import com.blogbackend.vo.Result;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GetUserInfoImpl implements GetUserInfoService {
    @Override
    public Result getUserInfo() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = userDetails.getUser();

        user.setPassword("0");
        JSONObject res = new JSONObject();
        res.put("user", user);
        return Result.success(res);
    }
}
