package com.blogbackend.service.impl.article;

import com.alibaba.fastjson.JSONObject;
import com.blogbackend.dao.mapper.*;
import com.blogbackend.dao.pojo.*;
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
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CategoriesMapper categoriesMapper;
    @Override
    public Result getArticleById(Integer id) {
        // 查询article
        Article article = articleMapper.selectById(id);
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
        // 查询文章分类
        String categoriesId = article.getCategoriesId();
        Categories categories = categoriesMapper.selectById(categoriesId);
        // 查询作者
        User author = userMapper.selectById(article.getAuthorId());
        // 返回数据
        JSONObject res = new JSONObject();
        article.setAuthorId(0);
        article.setTagsId("");
        res.put("article", article);
        res.put("article_body", article.getBody());
        res.put("article_tags", tags);
        res.put("article_categories",categories.getCategoriesName());
        res.put("article_author", author.getUsername());
        return Result.success(res);
    }
}
