package com.blogbackend.service.impl.article;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blogbackend.dao.mapper.ArticleMapper;
import com.blogbackend.dao.pojo.Article;
import com.blogbackend.service.article.GetHottestArticleService;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetHottestArticleImpl implements GetHottestArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public RespResult getHottestArticle() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.orderBy(true, false, "view_counts");
        List<Article> articles = articleMapper.selectList(wrapper);
        JSONObject res  = new JSONObject();
        res.put("data", articles);
        return RespResult.success(res);
    }
}
