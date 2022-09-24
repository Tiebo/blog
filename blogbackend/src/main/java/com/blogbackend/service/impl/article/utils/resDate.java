package com.blogbackend.service.impl.article.utils;

import com.blogbackend.dao.pojo.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class resDate {
    private Article article;
    private String username;
    private String categories;
    private String tags;
}
