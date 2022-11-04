package com.blogbackend.controller.categories;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blogbackend.dao.mapper.CategoriesMapper;
import com.blogbackend.dao.pojo.Categories;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class getCategoriesListController {
    @Autowired
    private CategoriesMapper categoriesMapper;

    @GetMapping("/api/categories/info/getlist/")
    public RespResult getCategoriesList() {
        LambdaQueryWrapper<Categories> qw = new LambdaQueryWrapper<>();
        qw.orderBy(true, false, Categories::getArticleCounts);
        List<Categories> categories = categoriesMapper.selectList(qw);

        JSONObject res = new JSONObject();
        res.put("categories", categories);
        return RespResult.success(res);
    }
}
