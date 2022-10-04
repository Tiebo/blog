package com.blogbackend.service.impl.utils;

import com.blogbackend.dao.pojo.User;
import com.blogbackend.handler.SystemException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class JudgeUtils {
    public static void judgeAdmin() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = userDetails.getUser();
        if (user.getIsAdmin() != 1) {
            throw new SystemException(401 ,"权限错误");
        }
    }
    public static void judgeArticle(String body, String tags, String title, String categories) {
        if (title == null || "".equals(title)) {
            throw new SystemException(403, "标题不能为空");
        }
        if (categories == null || "".equals(categories)) {
            throw new SystemException(403, "分类不能为空");

        }
        if (tags == null || "".equals(tags)) {
            throw new SystemException(403, "标签不能为空");
        }
        if (body == null || "".equals(body)) {
            throw new SystemException(403, "文章内容不能为空");
        }
    }
}
