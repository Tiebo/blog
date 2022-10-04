package com.blogbackend.service.impl.article.utils;

import com.blogbackend.dao.mapper.CategoriesMapper;
import com.blogbackend.dao.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataProcess {
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private CategoriesMapper categoriesMapper;


}
