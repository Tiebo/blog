package com.blogbackend.service.impl.tags;

import com.alibaba.fastjson.JSONObject;
import com.blogbackend.dao.mapper.TagMapper;
import com.blogbackend.dao.pojo.Tag;
import com.blogbackend.service.tags.GetArticlesByTagsService;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class GetArticlesByTagsImpl implements GetArticlesByTagsService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Result GetArticlesByTags(String tags) {

        String[] AllTag = tags.split(",");

        List<Tag> ListTags = new ArrayList<>();

        for (String tagId: AllTag) {
            Tag tag = tagMapper.selectById(tagId);
            ListTags.add(tag);
        }
        JSONObject res = new JSONObject();
        res.put("tags", ListTags);

        return Result.success(res);
    }
}
