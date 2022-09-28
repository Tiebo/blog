package com.blogbackend.service.impl.tags;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blogbackend.dao.mapper.TagMapper;
import com.blogbackend.dao.pojo.Article;
import com.blogbackend.service.impl.article.utils.Wrapper;
import com.blogbackend.service.tags.GetArticlesByTagsService;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.ccache.Tag;

import java.util.ArrayList;
import java.util.List;


@Service
public class GetArticlesByTagsImpl implements GetArticlesByTagsService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public Result GetArticlesByTags(int page, int pageSize,String tags) {

        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.orderBy(true, false, Article::getWeight, Article::getViewCounts);
        lqw.like(Article::getTagsId, tags);
        JSONObject res = Wrapper.getArticlesByWrapper(page, pageSize, lqw);

        return Result.success(res);
    }
}
