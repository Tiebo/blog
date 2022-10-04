package com.blogbackend.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blogbackend.dao.pojo.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
}
