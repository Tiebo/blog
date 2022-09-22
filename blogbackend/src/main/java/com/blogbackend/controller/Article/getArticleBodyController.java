package com.blogbackend.controller.Article;

import com.alibaba.fastjson.JSONObject;
import com.blogbackend.dao.mapper.ArticleBodyMapper;
import com.blogbackend.dao.pojo.ArticleBody;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class getArticleBodyController {

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @GetMapping("/articles/info/body/")
    public Result getArticleBody(@RequestParam Map<String, String> data) {
        System.out.println(data);
        Integer id = Integer.valueOf(data.get("body_id"));

        ArticleBody articleBody = articleBodyMapper.selectById(id);

        JSONObject res = new JSONObject();

        res.put("article", articleBody);
        return Result.success(res);
    }
}
