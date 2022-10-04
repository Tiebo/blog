package com.blogbackend.controller.categories;

import com.blogbackend.service.categories.RemoveCategoriesService;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveCategoriesController {

    @Autowired
    private RemoveCategoriesService removeCategoriesService;

    @PostMapping("/categories/remove/info/")
    public RespResult removeCategories(@RequestParam Map<String, String> data) {
        Integer id = Integer.valueOf(data.get("id"));
        return removeCategoriesService.removeCategories(id);
    }

}
