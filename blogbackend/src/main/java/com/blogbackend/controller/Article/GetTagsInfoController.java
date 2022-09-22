package com.blogbackend.controller.Article;

import com.blogbackend.service.article.getTagsInfoByArcService;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetTagsInfoController {
    @Autowired
    private getTagsInfoByArcService getTagsInfoByArcService;

    @PostMapping("/articles/info/arctags/")
    public Result getTagsInfo(@RequestParam Map<String, String> data) {
        String Ids = data.get("tagsId");
        System.out.println(data);
        return getTagsInfoByArcService.getTagsInfoByArc(Ids);
    }
}
