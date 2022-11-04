package com.blogbackend.service.impl.article.utils;

import com.blogbackend.dao.mapper.ArticleMapper;
import com.blogbackend.dao.mapper.CategoriesMapper;
import com.blogbackend.dao.mapper.TagMapper;
import com.blogbackend.dao.pojo.Article;
import com.blogbackend.dao.pojo.Categories;
import com.blogbackend.dao.pojo.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public UpdateCounts() {
    }

    /**
     *
     * @param opType ture -> 加  false -> 减
     * @param id
     */
    public UpdateCounts(boolean opType, Integer id) {
        this.opType = opType;
        this.id = id;
    }

    private Integer id;

    private void update(int con) {
        Article article = articleMapper.selectById(this.id);

        String[] categoriesId = article.getCategoriesId().split(",");
        // 更新分类
        List<Categories> categoriesList = new ArrayList<>();
        for (String id: categoriesId) {
            categoriesList.add(categoriesMapper.selectById(id));
        }
        categoriesList.forEach(categories -> {
            Integer articleCounts = categories.getArticleCounts();
            categories.setArticleCounts(articleCounts + con);
            categoriesMapper.updateById(categories);
        });
        // 更新标签
        String[] tagsId = article.getTagsId().split(",");
        List<Tag> tags = new ArrayList<>();
        for (String id: tagsId) {
            tags.add(tagMapper.selectById(id));
        }
        tags.forEach(tag -> {
            Integer tagViewCounts = tag.getArticleCounts();
            tag.setArticleCounts(tagViewCounts + con);
            tagMapper.updateById(tag);
        });
    }
    @Override
    public void run() {
        int con = this.opType ? 1 : -1;
        update(con);
    }
}
