package com.blogbackend.service.impl.tags;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blogbackend.dao.mapper.TagMapper;
import com.blogbackend.dao.pojo.Tag;
import com.blogbackend.service.tags.GetHottestTagService;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetHottestTagImpl implements GetHottestTagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public RespResult getHottestTag() {

        QueryWrapper<Tag> qw = new QueryWrapper<>();
        qw.orderBy(true, false, "article_counts");
        List<Tag> tags = tagMapper.selectList(qw);
        JSONObject res = new JSONObject();
        res.put("data", tags);

        return RespResult.success(res);
    }
}
