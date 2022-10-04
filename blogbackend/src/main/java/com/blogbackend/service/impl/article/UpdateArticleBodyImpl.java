package com.blogbackend.service.impl.article;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blogbackend.dao.mapper.ArticleMapper;
import com.blogbackend.dao.mapper.CategoriesMapper;
import com.blogbackend.dao.mapper.TagMapper;
import com.blogbackend.dao.pojo.Article;
import com.blogbackend.dao.pojo.Categories;
import com.blogbackend.dao.pojo.Tag;
import com.blogbackend.dao.pojo.User;
import com.blogbackend.handler.SystemException;
import com.blogbackend.service.article.UpdateArticleBodyService;
import com.blogbackend.service.impl.article.utils.UpdateCounts;
import com.blogbackend.service.impl.utils.JudgeUtils;
import com.blogbackend.service.impl.utils.UserDetailsImpl;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class UpdateArticleBodyImpl implements UpdateArticleBodyService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private CategoriesMapper categoriesMapper;
    @Override
    public RespResult updateArticleBody(Map<String, String> data) {
        // 验证token -> 得到user
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = userDetails.getUser();
        // 获取数据
        Integer id = Integer.valueOf(data.get("id"));
        String body = data.get("body");
        String tags = data.get("tags");
        String description = data.get("description");
        String title = data.get("title");
        String categories = data.get("categories");
        System.out.println(tags);
        JSONObject res = new JSONObject();
        // 处理标签
        String[] TagSplit = tags.split(",");
        QueryWrapper<Tag> tagLqw = new QueryWrapper<>();
        StringBuilder tagsId = new StringBuilder();
        for (String tagName: TagSplit) {
            tagLqw.clear();
            tagLqw.eq("tag_name", tagName);
            System.out.println(tagName);
            Integer tag_id = tagMapper.selectOne(tagLqw).getId();
            tagsId.append(",").append(tag_id);
        }
        tagsId.deleteCharAt(0);
        UpdateCounts.set
        // 处理分类
        String[] categoriesSplit = categories.split(",");
        LambdaQueryWrapper<Categories> cateLqw = new LambdaQueryWrapper<>();
        StringBuilder categoriesId = new StringBuilder();
        for (String cateName: categoriesSplit) {
            cateLqw.clear();
            cateLqw.eq(Categories::getCategoriesName, cateName);
            Integer cate_id = categoriesMapper.selectOne(cateLqw).getId();
            categoriesId.append(",").append(cate_id);
        }
        categoriesId.deleteCharAt(0);
        // 获取article
        Article article = articleMapper.selectById(id);
        // 检测更新是否合理
        if (!article.getAuthorId().equals(user.getUid())) {
            throw new SystemException(403, "你只能修改自己的文章");
        }
        JudgeUtils.judgeArticle(body, tags, title, categories);
        // 更新article
        article.setTitle(title);
        article.setDescription(description);
        article.setTagsId(tagsId.toString());
        article.setCategoriesId(categoriesId.toString());
        article.setModifyDate(new Date());
        article.setBody(body);
        articleMapper.updateById(article);
        // 返回结果
        res.put("msg", "success");
        return RespResult.success(res);
    }
}
