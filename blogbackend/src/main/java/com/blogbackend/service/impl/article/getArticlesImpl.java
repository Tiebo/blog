package com.blogbackend.service.impl.article;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blogbackend.dao.pojo.Article;
import com.blogbackend.service.article.getArticlesService;
import com.blogbackend.service.impl.article.utils.Wrapper;
import com.blogbackend.vo.RespResult;
import org.springframework.stereotype.Service;


@Service
public class getArticlesImpl implements getArticlesService {

    @Override
    public RespResult getArticles(int page, int pageSize) {

        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.orderBy(true, false, Article::getWeight, Article::getCreateDate);

        JSONObject res = Wrapper.getArticlesByWrapper(page, pageSize, lqw);
        return RespResult.success(res);
    }
}

