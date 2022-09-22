package com.blogbackend.controller.tags;

import com.blogbackend.service.tags.GetHottestTagService;
import com.blogbackend.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetHottestTagController {

    @Autowired
    private GetHottestTagService getHottestTagService;

    @GetMapping("/tags/info/hottest/")
    public Result getHottestTag() {
        return getHottestTagService.getHottestTag();
    }
}
