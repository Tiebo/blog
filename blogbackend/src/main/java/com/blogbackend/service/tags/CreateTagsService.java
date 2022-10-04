package com.blogbackend.service.tags;

import com.blogbackend.vo.RespResult;

import java.util.Map;

public interface CreateTagsService {
    RespResult CreateTags(Map<String, String> data);
}
