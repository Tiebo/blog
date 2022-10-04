package com.blogbackend.service.impl.tags;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blogbackend.dao.mapper.TagMapper;
import com.blogbackend.dao.pojo.Tag;
import com.blogbackend.dao.pojo.User;
import com.blogbackend.handler.SystemException;
import com.blogbackend.service.impl.utils.UserDetailsImpl;
import com.blogbackend.service.tags.CreateTagsService;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CreateTagsImpl implements CreateTagsService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public RespResult CreateTags(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = userDetails.getUser();

        JSONObject res = new JSONObject();
        if (user.getIsAdmin() != 1) {
            return RespResult.fail(300, "你不是管理员");
        }

        String tagName = data.get("tagName");
        if (tagName == null || "".equals(tagName)) {
            throw new SystemException(403, "标签名不能为空");
        }
        LambdaQueryWrapper<Tag> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Tag::getTagName, tagName);
        Tag old_tag = tagMapper.selectOne(lqw);

        if (old_tag != null) {
            throw new SystemException(403, "此标签已存在");
        }

        Tag tag = new Tag(null, tagName, 0);
        tagMapper.insert(tag);

        res.put("msg", "success");
        return RespResult.success(res);
    }
}
