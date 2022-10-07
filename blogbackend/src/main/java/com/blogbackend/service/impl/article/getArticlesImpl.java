package com.blogbackend.service.impl.article;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blogbackend.dao.mapper.ArticleMapper;
import com.blogbackend.dao.pojo.Article;
import com.blogbackend.service.article.getArticlesService;
import com.blogbackend.service.impl.article.utils.Wrapper;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class getArticlesImpl implements getArticlesService {

    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public RespResult getArticles(int page, int pageSize) {

        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.orderBy(true, false, Article::getWeight, Article::getCreateDate);

        JSONObject res = Wrapper.getArticlesByWrapper(page, pageSize, lqw);
        res.put("article_counts", articleMapper.selectCount(null));
        return RespResult.success(res);
    }
}

