package com.blogbackend.controller.Article;

import com.blogbackend.service.article.GetHottestArticleService;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetHottestArticleController {

    @Autowired
    private GetHottestArticleService getHottestArticleService;

    @GetMapping("/articles/info/hottest/")
    public Result getHottestArticle() {
        return getHottestArticleService.getHottestArticle();
    }
}
