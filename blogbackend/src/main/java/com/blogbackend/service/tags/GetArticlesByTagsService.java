package com.blogbackend.service.tags;

import com.blogbackend.vo.RespResult;

public interface GetArticlesByTagsService {
    RespResult GetArticlesByTags(int page, int pageSize, String tags);
}
