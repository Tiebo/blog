package com.blogbackend.service.impl.article;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blogbackend.dao.mapper.ArticleMapper;
import com.blogbackend.dao.mapper.CategoriesMapper;
import com.blogbackend.dao.mapper.TagMapper;
import com.blogbackend.dao.pojo.Article;
import com.blogbackend.dao.pojo.Categories;
import com.blogbackend.dao.pojo.Tag;
import com.blogbackend.dao.pojo.User;
import com.blogbackend.handler.SystemException;
import com.blogbackend.service.article.PostArticleService;
import com.blogbackend.service.impl.article.utils.UpdateCounts;
import com.blogbackend.service.impl.utils.JudgeUtils;
import com.blogbackend.service.impl.utils.UserDetailsImpl;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class PostArticleImpl implements PostArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private CategoriesMapper categoriesMapper;
    @Override
    public RespResult postArticle(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = userDetails.getUser();

        JSONObject res = new JSONObject();

        Integer id = Integer.valueOf(data.get("id"));

        if (!user.getUid().equals(id)) {
            throw new SystemException(403, "你只能以自己的身份发布文章");
        }

        String title = data.get("title");
        String description = data.get("description");
        String tags = data.get("tags");
        String categories = data.get("categories");
        String article_body = data.get("article_body");
        // 判断合法性
        JudgeUtils.judgeArticle(article_body, tags, title, categories);
        // 处理标签
        String[] TagSplit = tags.split(",");
        QueryWrapper<Tag> tagLqw = new QueryWrapper<>();
        StringBuilder tagsId = new StringBuilder();
        for (String tagName: TagSplit) {
            tagLqw.clear();
            tagLqw.eq("tag_name", tagName);
            System.out.println(tagName);
            Integer tag_id = tagMapper.selectOne(tagLqw).getId();
            tagsId.append(",").append(tag_id);
        }
        tagsId.deleteCharAt(0);
        // 处理分类
        String[] categoriesSplit = categories.split(",");
        LambdaQueryWrapper<Categories> cateLqw = new LambdaQueryWrapper<>();
        StringBuilder categoriesId = new StringBuilder();
        for (String cateName: categoriesSplit) {
            cateLqw.clear();
            cateLqw.eq(Categories::getCategoriesName, cateName);
            Integer cate_id = categoriesMapper.selectOne(cateLqw).getId();
            categoriesId.append(",").append(cate_id);
        }
        categoriesId.deleteCharAt(0);

        Date new_date = new Date();
        // 创建article
        Article article = new Article(
                null,
                0,
                new_date,
                new Date(),
                categoriesId.toString(),
                description,
                title,
                0,
                0,
                user.getUid(),
                article_body,
                tagsId.toString()
        );
        articleMapper.insert(article);
        Integer articleId = article.getId();
        UpdateCounts updateCounts = new UpdateCounts();
        updateCounts.setInfo(true, articleId);
        updateCounts.start();

        res.put("msg", "success");
        return RespResult.success(res);
    }
}
