package com.blogbackend.service.impl.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blogbackend.dao.mapper.UserMapper;
import com.blogbackend.dao.pojo.User;
import com.blogbackend.handler.SystemException;
import com.blogbackend.service.user.RegisterService;
import com.blogbackend.vo.RespResult;
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
    public RespResult register(String username, String password, String confirmPassword) {
        JSONObject res = new JSONObject();
        if (username == null || username.equals("")) {
            throw new SystemException(403, "用户名不能为空");
        }
        if (password == null || confirmPassword == null || password.equals("")) {
            throw new SystemException(403, "密码不能为空");
        }
        if (!password.equals(confirmPassword)) {
            throw new SystemException(403, "俩次密码不同");
        }
        // 去空格
        username = username.trim();
        if (username.length() > 20) {
            throw new SystemException(403, "用户名长度不能大于20");
        }
        if (password.length() > 30) {
            throw new SystemException(403, "密码长度不能大于100");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User confirmUser = userMapper.selectOne(queryWrapper);
        if (confirmUser != null) {
            throw new SystemException(403, "用户名已存在");
        }

        String encodePwd = passwordEncoder.encode(password);
        String photo = "https://cdn.acwing.com/media/user/profile/photo/184248_lg_4fe90ff833.jpg";
        User user = new User(
                null,
                username,
                null,
                encodePwd,
                0,
                photo,
                new Date(),
                new Date(),
                null,
                null,
                "在线"
        );
        userMapper.insert(user);
        res.put("msg", "success");
        return RespResult.success(res);
    }
}
