package com.blogbackend.service.impl.user;

import com.alibaba.fastjson.JSONObject;
import com.blogbackend.dao.mapper.UserMapper;
import com.blogbackend.dao.pojo.User;
import com.blogbackend.service.impl.utils.UserDetailsImpl;
import com.blogbackend.service.user.LoginService;
import com.blogbackend.utils.JwtUtil;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;
    @Override
    public Result getToken(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();

        User user = userDetails.getUser();
        user.setLastLogin(new Date());
        userMapper.updateById(user);

        String jwt = JwtUtil.createJWT(user.getUid().toString());
        JSONObject res = new JSONObject();

        res.put("token", jwt);
        return Result.success(res);
    }
}
