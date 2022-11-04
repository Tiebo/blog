package com.blogbackend.controller.article.post;

import com.blogbackend.service.article.PostArticleService;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PostArticleController {

    @Autowired
    private PostArticleService postArticleService;

    @PostMapping("/api/articles/post/info/")
    public RespResult postArticle(@RequestParam Map<String, String> data) {
        return postArticleService.postArticle(data);
    }
}
