package com.blogbackend.controller.Article;

import com.blogbackend.service.article.GetArticleBodyService;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class getArticleByIdController {

    @Autowired
    private GetArticleBodyService  getArticleBodyService;


    @PostMapping("/articles/info/article/")
    public Result getArticleById(@RequestParam Map<String, String> data) {
        int id = Integer.parseInt(data.get("id"));
        return getArticleBodyService.getArticleById(id);
    }
}
