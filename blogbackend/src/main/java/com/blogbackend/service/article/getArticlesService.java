package com.blogbackend.service.article;

import com.blogbackend.vo.Result;


public interface getArticlesService {
    Result getArticles(int page, int pageSize);
}
