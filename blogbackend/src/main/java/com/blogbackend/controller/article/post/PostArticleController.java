package com.blogbackend.controller.article.post;

import com.blogbackend.service.article.PostArticleService;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PostArticleController {

    @Autowired
    private PostArticleService postArticleService;

    @PostMapping("/articles/post/info/")
    public Result postArticle(@RequestParam Map<String, String> data) {
        return postArticleService.postArticle(data);
    }
}
