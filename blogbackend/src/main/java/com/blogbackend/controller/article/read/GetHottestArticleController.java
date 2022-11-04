package com.blogbackend.controller.article.read;

import com.blogbackend.service.article.GetHottestArticleService;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetHottestArticleController {

    @Autowired
    private GetHottestArticleService getHottestArticleService;

    @GetMapping("/api/articles/info/hottest/")
    public RespResult getHottestArticle() {
        return getHottestArticleService.getHottestArticle();
    }
}
