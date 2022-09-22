package com.blogbackend.service.impl.article;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blogbackend.dao.mapper.ArticleMapper;
import com.blogbackend.dao.pojo.Article;
import com.blogbackend.service.article.getArticlesService;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class getArticlesImpl implements getArticlesService {

    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public Result getArticles(int page, int pageSize) {

        Page<Article> currentPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);

        Page<Article> articlePage = articleMapper.selectPage(currentPage, queryWrapper);
        List<Article> articles = articlePage.getRecords();
        JSONObject res = new JSONObject();
        res.put("articles", articles);
        return Result.success(res);
    }
}
