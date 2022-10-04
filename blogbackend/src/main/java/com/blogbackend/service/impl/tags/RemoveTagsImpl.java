package com.blogbackend.service.impl.tags;

import com.alibaba.fastjson.JSONObject;
import com.blogbackend.dao.mapper.TagMapper;
import com.blogbackend.dao.pojo.Tag;
import com.blogbackend.handler.SystemException;
import com.blogbackend.service.impl.utils.JudgeUtils;
import com.blogbackend.service.tags.RemoveTagsService;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoveTagsImpl implements RemoveTagsService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public RespResult removeTags(Integer id) {
        JSONObject res = new JSONObject();
        JudgeUtils.judgeAdmin();

        Tag tag = tagMapper.selectById(id);
        if (tag == null) {
            throw new SystemException(403, "该标签不存在或已被删除");
        }
        if (tag.getArticleCounts() != 0) {
            throw new SystemException(403, "该标签中还有文章");

        }
        tagMapper.deleteById(id);
        res.put("msg", "success");
        return RespResult.success(res);
    }
}
