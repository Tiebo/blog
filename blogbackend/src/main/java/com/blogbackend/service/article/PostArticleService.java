package com.blogbackend.service.article;

import com.blogbackend.vo.Result;

import java.util.Map;

public interface PostArticleService {
    Result postArticle(Map<String, String> data);
}
