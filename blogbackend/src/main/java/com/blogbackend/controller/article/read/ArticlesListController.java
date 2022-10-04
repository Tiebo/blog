package com.blogbackend.controller.article.read;

import com.blogbackend.service.article.getArticlesService;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ArticlesListController {
    @Autowired
    private getArticlesService getArticlesService;

    @PostMapping("/articles/info/getlist/")
    public RespResult getArticles(@RequestParam Map<String, String> data) {
        int page = Integer.parseInt(data.get("page"));
        int pageSize = Integer.parseInt(data.get("pageSize"));
        return getArticlesService.getArticles(page, pageSize);
    }
}

