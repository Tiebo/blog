package com.blogbackend.service.impl.article;

import com.alibaba.fastjson.JSONObject;
import com.blogbackend.dao.mapper.ArticleMapper;
import com.blogbackend.dao.pojo.Article;
import com.blogbackend.dao.pojo.User;
import com.blogbackend.service.article.PostArticleService;
import com.blogbackend.service.impl.utils.UserDetailsImpl;
import com.blogbackend.vo.Result;
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
    @Override
    public Result postArticle(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = userDetails.getUser();

        JSONObject res = new JSONObject();

        Integer id = Integer.valueOf(data.get("id"));
        if (!user.getUid().equals(id)) {
            res.put("error_message", "你只能以自己的身份发布文章");
            return Result.success(res);
        }
        String title = data.get("title");
        String description = data.get("description");
        String tags = data.get("tags");
        String categories = data.get("categories");
        String article_body = data.get("article_body");

        Article article = new Article(
                null,
                0,
                new Date(),
                new Date(),
                categories,
                description,
                title,
                0,
                0,
                user.getUid(),
                article_body,
                tags
        );
        int insert = articleMapper.insert(article);

        String msg = "更新失败";

        if (insert == 1) {
            msg = "更新成功";
        }
        res.put("error_message", msg);
        return Result.success(res);
    }
}
