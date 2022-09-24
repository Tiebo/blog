package com.blogbackend.service.article;

import com.blogbackend.vo.Result;

import java.util.Map;

public interface UpdateArticleBodyService {
    Result updateArticleBody(Map<String, String> data);
}
