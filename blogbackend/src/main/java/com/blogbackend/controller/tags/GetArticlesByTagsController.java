package com.blogbackend.controller.tags;

import com.blogbackend.service.tags.GetArticlesByTagsService;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetArticlesByTagsController {

    @Autowired
    private GetArticlesByTagsService getArticlesByTagsService;

    @PostMapping("/api/tags/info/articles/")
    public RespResult getArticlesByTags(@RequestParam Map<String, String> data) {
        String tags = data.get("tags");
        int page = Integer.parseInt(data.get("page"));
        int pageSize = Integer.parseInt(data.get("pageSize"));
        return getArticlesByTagsService.GetArticlesByTags(page, pageSize, tags);
    }
}
