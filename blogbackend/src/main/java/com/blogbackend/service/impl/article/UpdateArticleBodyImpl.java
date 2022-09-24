package com.blogbackend.service.impl.article;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blogbackend.dao.mapper.ArticleBodyMapper;
import com.blogbackend.dao.mapper.ArticleMapper;
import com.blogbackend.dao.pojo.Article;
import com.blogbackend.dao.pojo.ArticleBody;
import com.blogbackend.dao.pojo.User;
import com.blogbackend.service.article.UpdateArticleBodyService;
import com.blogbackend.service.impl.utils.UserDetailsImpl;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class UpdateArticleBodyImpl implements UpdateArticleBodyService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Override
    public Result updateArticleBody(Map<String, String> data) {

        // 验证token -> 得到user
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = userDetails.getUser();
        // 获取数据
        Integer id = Integer.valueOf(data.get("id"));
        String body = data.get("body");
        String tags = data.get("tags");
        String description = data.get("description");
        String title = data.get("title");
        String categories = data.get("categories");

        JSONObject res = new JSONObject();
        // 更新article
        Article article = articleMapper.selectById(id);
        // 检测更新是否合理
        if (!article.getAuthorId().equals(user.getUid())) {
            res.put("error_message", "你只能修改自己的文章");
            return Result.success(res);
        }
        article.setTitle(title);
        article.setDescription(description);
        article.setModifyDate(new Date());
        articleMapper.updateById(article);
        // 更新body
        QueryWrapper<ArticleBody> qw = new QueryWrapper<>();
        qw.eq("id", article.getBodyId());
        ArticleBody articleBody = articleBodyMapper.selectOne(qw);
        articleBody.setArticleBody(body);
        articleBodyMapper.updateById(articleBody);

        res.put("error_message", "success");
        return Result.success(res);
    }
}
