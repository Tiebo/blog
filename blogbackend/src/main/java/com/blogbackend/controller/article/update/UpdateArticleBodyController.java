package com.blogbackend.controller.article.update;

import com.blogbackend.service.article.UpdateArticleBodyService;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdateArticleBodyController {
    @Autowired
    private UpdateArticleBodyService updateArticleBodyService;

    @PostMapping("/articles/update/info/")
    public RespResult updateArticleBody(@RequestParam Map<String, String> data) {
        return updateArticleBodyService.updateArticleBody(data);
    }
}
