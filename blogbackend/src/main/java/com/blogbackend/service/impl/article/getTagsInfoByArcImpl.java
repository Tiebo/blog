package com.blogbackend.service.impl.article;

import com.alibaba.fastjson.JSONObject;
import com.blogbackend.dao.mapper.TagMapper;
import com.blogbackend.dao.pojo.Tag;
import com.blogbackend.service.article.getTagsInfoByArcService;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class getTagsInfoByArcImpl implements getTagsInfoByArcService {

    @Autowired
    private TagMapper tagMapper;
    @Override
    public Result getTagsInfoByArc(String strIds) {
        List<Tag> tags = new ArrayList<>();

        String[] split = strIds.split(",");
        for (String s : split) {
            int id = Integer.parseInt(s);

            tags.add(tagMapper.selectById(id));
        }
        JSONObject res = new JSONObject();
        res.put("tags", tags);
        return Result.success(res);
    }
}
