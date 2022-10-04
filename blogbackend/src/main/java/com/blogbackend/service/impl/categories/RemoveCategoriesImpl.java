package com.blogbackend.service.impl.categories;

import com.alibaba.fastjson.JSONObject;
import com.blogbackend.dao.mapper.CategoriesMapper;
import com.blogbackend.dao.pojo.Categories;
import com.blogbackend.handler.SystemException;
import com.blogbackend.service.categories.RemoveCategoriesService;
import com.blogbackend.service.impl.utils.JudgeUtils;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoveCategoriesImpl implements RemoveCategoriesService {

    @Autowired
    private CategoriesMapper categoriesMapper;

    @Override
    public RespResult removeCategories(Integer id) {
        JudgeUtils.judgeAdmin();
        JSONObject res = new JSONObject();

        Categories categories = categoriesMapper.selectById(id);
        if (categories == null) {
            throw new SystemException(403, "该分类不存在或已被删除");

        }
        Integer articleCounts = categories.getArticleCounts();
        if (articleCounts != 0) {
            throw new SystemException(403, "请先删除分类中的文章");
        }
        categoriesMapper.deleteById(id);
        res.put("msg", "success");
        return RespResult.success(res);
    }
}
