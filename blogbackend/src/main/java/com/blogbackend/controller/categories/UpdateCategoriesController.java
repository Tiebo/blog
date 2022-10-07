package com.blogbackend.controller.categories;

import com.alibaba.fastjson.JSONObject;
import com.blogbackend.dao.mapper.CategoriesMapper;
import com.blogbackend.dao.pojo.Categories;
import com.blogbackend.handler.SystemException;
import com.blogbackend.service.impl.utils.JudgeUtils;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdateCategoriesController {
    @Autowired
    private CategoriesMapper categoriesMapper;

    @PostMapping("/categories/update/info/")
    public RespResult updateCategories(@RequestParam Map<String, String> data) {
        JudgeUtils.judgeAdmin();

        Integer id = Integer.valueOf(data.get("categories_id"));

        JSONObject res = new JSONObject();
        String name = data.get("categories_name");
        if (name == null || "".equals(name)) {
            throw new SystemException(403, "名称不能为空");

        }
        Categories categories = categoriesMapper.selectById(id);
        if (categories == null) {
            throw new SystemException(403, "该分类不存在");
        }
        name = name.trim();
        categories.setCategoriesName(name);
        categoriesMapper.updateById(categories);
        res.put("msg", "success");
        return RespResult.success(res);
    }
}
