package com.blogbackend.service.impl.categories;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blogbackend.dao.mapper.ArticleMapper;
import com.blogbackend.dao.mapper.UserMapper;
import com.blogbackend.dao.pojo.Article;
import com.blogbackend.service.categories.GetArticlesByCategoriesService;
import com.blogbackend.service.impl.article.utils.Wrapper;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetArticlesByCategoriesImpl implements GetArticlesByCategoriesService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Result getArticlesByCategories(int page, int pageSize, String categoriesIds) {

        LambdaQueryWrapper<Article> qw = new LambdaQueryWrapper<>();
        qw.like(Article::getCategoriesId, categoriesIds);
        qw.orderBy(true, false, Article::getCreateDate);
        JSONObject res = Wrapper.getArticlesByWrapper(1, 10, qw);

        return Result.success(res);
    }
}
