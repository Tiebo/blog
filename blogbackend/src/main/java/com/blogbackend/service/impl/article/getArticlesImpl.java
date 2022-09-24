package com.blogbackend.service.impl.article;

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
import com.blogbackend.service.article.getArticlesService;
import com.blogbackend.service.impl.article.utils.resDate;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class getArticlesImpl implements getArticlesService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoriesMapper categoriesMapper;
    @Override
    public Result getArticles(int page, int pageSize) {
        IPage<Article> currentPage = new Page<>(page, pageSize);

        LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
        lqw.orderBy(true, false, Article::getWeight, Article::getCreateDate);

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
            Categories category = new Categories();
            for (String categoriesId: categoriesIds) {
                category = categoriesMapper.selectById(categoriesId);
                stringBuilder.append("/ ").append(category.getCategoriesName());
            }
            // 前缀处理
            stringBuilder.deleteCharAt(0);
            // 获取标签信息

            resDates.add(new resDate(article, author.getUsername(),stringBuilder.toString(), null));
        }
        res.put("resData", resDates);
        return Result.success(res);
    }
}

