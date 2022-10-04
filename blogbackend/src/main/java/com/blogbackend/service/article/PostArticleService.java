package com.blogbackend.service.article;

import com.blogbackend.vo.RespResult;

import java.util.Map;

public interface PostArticleService {
    RespResult postArticle(Map<String, String> data);
}
