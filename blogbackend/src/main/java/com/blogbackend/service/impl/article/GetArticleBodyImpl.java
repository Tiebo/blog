package com.blogbackend.service.impl.article;

import com.alibaba.fastjson.JSONObject;
import com.blogbackend.dao.mapper.*;
import com.blogbackend.dao.pojo.*;
import com.blogbackend.service.article.GetArticleBodyService;
import com.blogbackend.vo.RespResult;
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
    public RespResult getArticleById(Integer id) {
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
        tags.sort(Comparator.comparingInt(Tag::getArticleCounts));
        // 查询文章分类
        String[] categoriesId = article.getCategoriesId().split(",");
        List<Categories> categoriesList = new ArrayList<>();
        for (String categories_id: categoriesId) {
            categoriesList.add(categoriesMapper.selectById(categories_id));
        }
        categoriesList.sort(Comparator.comparingInt(Categories::getArticleCounts));
        // 查询作者
        User author = userMapper.selectById(article.getAuthorId());
        // 返回数据
        JSONObject res = new JSONObject();
        article.setTagsId("");
        res.put("article", article);
        res.put("article_body", article.getBody());
        res.put("article_tags", tags);
        res.put("article_categories",categoriesList);
        res.put("article_author", author.getUsername());
        return RespResult.success(res);
    }
}
