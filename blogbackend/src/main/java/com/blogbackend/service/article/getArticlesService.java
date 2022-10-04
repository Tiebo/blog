package com.blogbackend.service.article;

import com.blogbackend.vo.RespResult;


public interface getArticlesService {
    RespResult getArticles(int page, int pageSize);
}
