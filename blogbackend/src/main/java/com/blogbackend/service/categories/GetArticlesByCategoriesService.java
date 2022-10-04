package com.blogbackend.service.categories;

import com.blogbackend.vo.RespResult;

public interface GetArticlesByCategoriesService {
    RespResult getArticlesByCategories(int page, int pageSize, String categoriesIds);
}
