package com.blogbackend.service.tags;

import com.blogbackend.vo.Result;

public interface GetArticlesByTagsService {
    Result GetArticlesByTags(String tags);
}
