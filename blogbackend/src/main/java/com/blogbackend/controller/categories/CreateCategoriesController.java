package com.blogbackend.controller.categories;

import com.alibaba.fastjson.JSONObject;
import com.blogbackend.dao.mapper.CategoriesMapper;
import com.blogbackend.dao.pojo.Categories;
import com.blogbackend.handler.SystemException;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CreateCategoriesController {
    @Autowired
    private CategoriesMapper categoriesMapper;

    @PostMapping("/api/categories/create/info/")
    public RespResult CreateCategories(@RequestParam Map<String, String> data) {
        String categoriesName = data.get("categoriesName");
        JSONObject res = new JSONObject();
        if (categoriesName == null || categoriesName.equals("")) {
            throw new SystemException(403, "分类名不能为空");
        }
        Categories categories = categoriesMapper.selectById(categoriesName);
        if (categories != null) {
            throw new SystemException(403, "该分类已存在");
        }
        Categories new_categories = new Categories(null, categoriesName, 0);
        categoriesMapper.insert(new_categories);
        res.put("msg", "success");
        return RespResult.success(res);
    }
}
