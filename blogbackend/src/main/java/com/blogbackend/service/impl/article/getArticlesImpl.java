package com.blogbackend.service.impl.article;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blogbackend.dao.pojo.Article;
import com.blogbackend.service.article.getArticlesService;
import com.blogbackend.service.impl.article.utils.Wrapper;
import com.blogbackend.vo.Result;
import org.springframework.stereotype.Service;


@Service
public class getArticlesImpl implements getArticlesService {

    @Override
    public Result getArticles(int page, int pageSize) {
        IPage<Article> currentPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.orderBy(true, false, Article::getWeight, Article::getCreateDate);

        JSONObject res = Wrapper.getArticlesByWrapper(page, pageSize, lqw);
        return Result.success(res);
    }
}

