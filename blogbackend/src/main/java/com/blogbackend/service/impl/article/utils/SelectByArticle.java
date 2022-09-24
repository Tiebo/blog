package com.blogbackend.service.impl.article.utils;

import com.blogbackend.dao.mapper.ArticleBodyMapper;
import com.blogbackend.dao.mapper.CategoriesMapper;
import com.blogbackend.dao.mapper.TagMapper;
import com.blogbackend.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SelectByArticle {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private CategoriesMapper categoriesMapper;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;


}
