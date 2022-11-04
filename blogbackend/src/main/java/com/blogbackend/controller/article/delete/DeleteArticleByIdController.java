package com.blogbackend.controller.article.delete;

import com.blogbackend.service.article.DeleteArticleByIdService;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DeleteArticleByIdController {

    @Autowired
    private DeleteArticleByIdService deleteArticleByIdService;

    @PostMapping("/api/articles/delete/info/")
    public RespResult deleteArticleById(@RequestParam Map<String, String> data) {

        Integer body_id = Integer.valueOf(data.get("body_id"));
        return deleteArticleByIdService.deleteArticleById(body_id);
    }
}
