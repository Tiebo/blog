package com.blogbackend.service.categories;

import com.blogbackend.vo.Result;

public interface GetArticlesByCategoriesService {
    Result getArticlesByCategories(int page, int pageSize, String categoriesIds);
}
