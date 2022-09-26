package com.blogbackend.controller.article.read;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blogbackend.dao.mapper.ArticleMapper;
import com.blogbackend.dao.pojo.Article;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewestArticleController {

    @Autowired
    private ArticleMapper articleMapper;

    @GetMapping("/articles/info/newest/")
    public Result getNewestArticle() {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.orderBy(true, false, "create_date");
        List<Article> articles = articleMapper.selectList(wrapper);
        JSONObject res = new JSONObject();
        articles.forEach(article -> article.setBody(""));
        res.put("data", articles);
        return Result.success(res);
    }
}
