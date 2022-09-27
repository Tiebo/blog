package com.blogbackend.controller.categories;

import com.blogbackend.service.categories.GetArticlesByCategoriesService;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetArticlesByCategoriesController {
    @Autowired
    private GetArticlesByCategoriesService getArticlesByCategoriesService;

    @PostMapping ("/categories/info/articles/")
    public Result getArticlesByCategories(@RequestParam Map<String, String> data) {
        String categories = data.get("categories");
        int page = Integer.parseInt(data.get("page"));
        int pageSize = Integer.parseInt(data.get("pageSize"));
        return getArticlesByCategoriesService.getArticlesByCategories(page, pageSize, categories);
    }
}
