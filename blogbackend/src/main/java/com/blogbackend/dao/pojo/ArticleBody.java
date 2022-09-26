package com.blogbackend.dao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleBody {
    // 文章Id
    private Integer articleId;
    private String articleBody;
}
