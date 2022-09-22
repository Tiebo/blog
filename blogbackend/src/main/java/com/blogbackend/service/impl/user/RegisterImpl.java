package com.blogbackend.service.impl.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blogbackend.dao.mapper.UserMapper;
import com.blogbackend.dao.pojo.User;
import com.blogbackend.service.user.RegisterService;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RegisterImpl implements RegisterService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Result register(String username, String password, String confirmPassword) {
        JSONObject res = new JSONObject();
        if (username == null || username.equals("")) {
            res.put("error_message", "用户名不能为空");
            return Result.success(res);
        }
        if (password == null || confirmPassword == null || password.equals("")) {
            res.put("error_message", "密码不能为空");
            return Result.success(res);
        }
        if (!password.equals(confirmPassword)) {
            res.put("error_message", "俩次密码不同");
            return Result.success(res);
        }
        // 去空格
        username = username.trim();
        if (username.length() > 20) {
            res.put("error_message", "用户名长度不能大于20");
            return Result.success(res);
        }
        if (password.length() > 30) {
            res.put("error_message", "密码长度不能大于100");
            return Result.success(res);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User confirmUser = userMapper.selectOne(queryWrapper);
        if (confirmUser != null) {
            res.put("error_message", "用户名已存在");
            return Result.success(res);
        }

        String encodePwd = passwordEncoder.encode(password);
        String photo = "https://cdn.acwing.com/media/user/profile/photo/184248_lg_4fe90ff833.jpg";
        User user = new User(
                null,
                username,
                null,
                encodePwd,
                false,
                photo,
                new Date(),
                new Date(),
                null,
                null,
                "在线"
        );
        userMapper.insert(user);
        res.put("error_message", "success");
        return Result.success(res);
    }
}
