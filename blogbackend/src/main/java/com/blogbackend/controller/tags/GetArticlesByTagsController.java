package com.blogbackend.controller.tags;

import com.blogbackend.service.tags.GetArticlesByTagsService;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetArticlesByTagsController {

    @Autowired
    private GetArticlesByTagsService getArticlesByTagsService;

    @GetMapping("/tags/info/articles/")
    public Result getArticlesByTags(@RequestParam Map<String, String> data) {
        String tags = data.get("tags");
        return getArticlesByTagsService.GetArticlesByTags(tags);
    }
}
