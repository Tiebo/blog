package com.blogbackend.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blogbackend.dao.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
