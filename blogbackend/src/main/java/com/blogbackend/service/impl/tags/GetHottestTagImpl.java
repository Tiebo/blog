package com.blogbackend.service.impl.tags;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blogbackend.dao.mapper.TagMapper;
import com.blogbackend.dao.pojo.Tag;
import com.blogbackend.service.tags.GetHottestTagService;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetHottestTagImpl implements GetHottestTagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Result getHottestTag() {

        QueryWrapper<Tag> qw = new QueryWrapper<>();
        qw.orderBy(true, false, "tag_hot");
        List<Tag> tags = tagMapper.selectList(qw);
        JSONObject res = new JSONObject();
        res.put("data", tags);

        return Result.success(res);
    }
}
