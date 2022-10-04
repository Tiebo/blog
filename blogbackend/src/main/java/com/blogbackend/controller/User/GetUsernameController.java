package com.blogbackend.controller.User;

import com.alibaba.fastjson.JSONObject;
import com.blogbackend.dao.mapper.UserMapper;
import com.blogbackend.dao.pojo.User;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetUsernameController {

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/user/info/base/")
    RespResult getUserInfoById(@RequestParam Map<String, String> map) {
        System.out.println(map);
        Integer id = Integer.valueOf(map.get("id"));
        User user = userMapper.selectById(id);
        JSONObject res = new JSONObject();
        res.put("username", user.getUsername());
        return RespResult.success(res);
    }
}
