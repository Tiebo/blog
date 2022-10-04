package com.blogbackend.service.article;

import com.blogbackend.vo.RespResult;

import java.util.Map;

public interface UpdateArticleBodyService {
    RespResult updateArticleBody(Map<String, String> data);
}
