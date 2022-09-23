package com.blogbackend.service.impl.article;

import com.alibaba.fastjson.JSONObject;
import com.blogbackend.dao.mapper.ArticleBodyMapper;
import com.blogbackend.dao.mapper.ArticleMapper;
import com.blogbackend.dao.mapper.TagMapper;
import com.blogbackend.dao.mapper.UserMapper;
import com.blogbackend.dao.pojo.Article;
import com.blogbackend.dao.pojo.ArticleBody;
import com.blogbackend.dao.pojo.Tag;
import com.blogbackend.dao.pojo.User;
import com.blogbackend.service.article.GetArticleBodyService;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class GetArticleBodyImpl implements GetArticleBodyService {

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public Result getArticleById(Integer id) {
        // 查询article
        Article article = articleMapper.selectById(id);
        // 查询body
        ArticleBody articleBody = articleBodyMapper.selectById(article.getBodyId());
        // 更新阅读次数
        article.setViewCounts(article.getViewCounts() + 1);
        articleMapper.updateById(article);
        // 查询标签
        String[] tag_ids = article.getTagsId().split(",");
        List<Tag> tags = new ArrayList<>();
        for (String tag_id : tag_ids) {
            tags.add(tagMapper.selectById(tag_id));
        }
        tags.sort(Comparator.comparingInt(Tag::getTagViewCounts));
        // 查询作者
        User author = userMapper.selectById(article.getAuthorId());
        // 返回数据
        JSONObject res = new JSONObject();
        article.setBodyId(0);
        article.setAuthorId(0);
        article.setTagsId("");
        res.put("article", article);
        res.put("article_body", articleBody.getArticleBody());
        res.put("article_tags", tags);
        res.put("article_author", author.getUsername());
        return Result.success(res);
    }
}
