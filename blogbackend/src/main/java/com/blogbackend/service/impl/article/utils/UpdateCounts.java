package com.blogbackend.service.impl.article.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blogbackend.dao.mapper.ArticleMapper;
import com.blogbackend.dao.mapper.CategoriesMapper;
import com.blogbackend.dao.mapper.TagMapper;
import com.blogbackend.dao.pojo.Article;
import com.blogbackend.dao.pojo.Categories;
import com.blogbackend.dao.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateCounts extends Thread{
    private static ArticleMapper articleMapper;
    private static TagMapper tagMapper;
    private static CategoriesMapper categoriesMapper;
    @Autowired
    public void setTagMapper(TagMapper tagMapper) {
        UpdateCounts.tagMapper = tagMapper;
    }
    @Autowired
    public void setCategoriesMapper(CategoriesMapper categoriesMapper) {
        UpdateCounts.categoriesMapper = categoriesMapper;
    }
    @Autowired
    public void setArticleMapper(ArticleMapper articleMapper) {
        UpdateCounts.articleMapper = articleMapper;
    }
    private boolean opType;
    private static Integer id;

    /**
     *
     * @param opType: true为加, false为减
     * @param id
     */
    public void setInfo(boolean opType, Integer id) {
        this.opType = opType;
        UpdateCounts.id = id;
    }

    private void update(int con) {
        Article article = articleMapper.selectById(id);

        String tagsId = article.getTagsId();
        String categoriesId = article.getCategoriesId();
        // 更新分类
        LambdaQueryWrapper<Categories> cateLqw = new LambdaQueryWrapper<>();
        cateLqw.like(Categories::getId, categoriesId);
        List<Categories> categoriesList = categoriesMapper.selectList(cateLqw);
        categoriesList.forEach(categories -> {
            Integer articleCounts = categories.getArticleCounts();
            categories.setArticleCounts(articleCounts + con);
            categoriesMapper.updateById(categories);
        });
        // 更新标签
        LambdaQueryWrapper<Tag> lqw = new LambdaQueryWrapper<>();
        lqw.like(Tag::getId, tagsId);
        List<Tag> tags = tagMapper.selectList(lqw);
        tags.forEach(tag -> {
            Integer tagViewCounts = tag.getArticleCounts();
            tag.setArticleCounts(tagViewCounts + con);
            tagMapper.updateById(tag);
        });
    }
    @Override
    public void run() {
        int con = opType ? 1 : -1;
        update(con);
    }
}
