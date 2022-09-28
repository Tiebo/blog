package com.blogbackend.service.tags;

import com.blogbackend.vo.Result;

public interface GetArticlesByTagsService {
    Result GetArticlesByTags(int page, int pageSize,String tags);
}
