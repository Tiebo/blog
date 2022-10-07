package com.blogbackend.service.impl.article;

import com.alibaba.fastjson.JSONObject;
import com.blogbackend.dao.mapper.ArticleMapper;
import com.blogbackend.service.article.DeleteArticleByIdService;
import com.blogbackend.service.impl.article.utils.UpdateCounts;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteArticleByIdImpl implements DeleteArticleByIdService {

    @Autowired
    private ArticleMapper articleMapper;


    @Override
    public RespResult deleteArticleById(Integer id) {
        // 更新文章数
        UpdateCounts updateCounts = new UpdateCounts(false, id);
        updateCounts.start();

        articleMapper.deleteById(id);
        JSONObject res = new JSONObject();
        res.put("msg", "success");
        return RespResult.success(res);
    }
}
