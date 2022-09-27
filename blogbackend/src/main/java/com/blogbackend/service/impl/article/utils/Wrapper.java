package com.blogbackend.service.impl.article.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blogbackend.dao.mapper.ArticleMapper;
import com.blogbackend.dao.mapper.CategoriesMapper;
import com.blogbackend.dao.mapper.UserMapper;
import com.blogbackend.dao.pojo.Article;
import com.blogbackend.dao.pojo.Categories;
import com.blogbackend.dao.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Wrapper {

    static private ArticleMapper articleMapper;
    static private UserMapper userMapper;
    static private CategoriesMapper categoriesMapper;

    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper) {
        Wrapper.articleMapper = articleMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        Wrapper.userMapper = userMapper;
    }

    @Autowired
    public void setCategoriesMapper(CategoriesMapper categoriesMapper) {
        Wrapper.categoriesMapper = categoriesMapper;
    }


     public static JSONObject getArticlesByWrapper(int page, int pageSize, LambdaQueryWrapper<Article> lqw) {
        IPage<Article> currentPage = new Page<>(page, pageSize);

        IPage<Article> articlePage = articleMapper.selectPage(currentPage, lqw);

        List<Article> articles = articlePage.getRecords();

        List<resDate> resDates = new ArrayList<>();

        JSONObject res = new JSONObject();

        for (Article article: articles) {
            // 获取作者信息
            User author = userMapper.selectById(article.getAuthorId());
            // 获取文章分类
            String[] categoriesIds = article.getCategoriesId().split(",");
            StringBuilder stringBuilder = new StringBuilder();
            Categories category;
            for (String categoriesId: categoriesIds) {
                category = categoriesMapper.selectById(categoriesId);
                if (category == null) continue;
                stringBuilder.append("/ ").append(category.getCategoriesName());
            }
            // 前缀处理
            stringBuilder.deleteCharAt(0);
            article.setBody("");
            resDates.add(new resDate(article, author.getUsername(),stringBuilder.toString(), null));
        }
        res.put("resData", resDates);
        return res;
    }
}

