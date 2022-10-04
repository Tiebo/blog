package com.blogbackend.controller.article.read;

import com.blogbackend.service.article.getTagsInfoByArcService;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetTagsInfoController {
    @Autowired
    private getTagsInfoByArcService getTagsInfoByArcService;

    @PostMapping("/articles/info/articles/")
    public RespResult getTagsInfo(@RequestParam Map<String, String> data) {
        String Ids = data.get("tagsId");
        return getTagsInfoByArcService.getTagsInfoByArc(Ids);
    }
}
