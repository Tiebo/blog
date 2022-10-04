package com.blogbackend.controller.tags;

import com.blogbackend.service.tags.CreateTagsService;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CreateTagsController {
    @Autowired
    private CreateTagsService createTagsService;

    @PostMapping("/tags/create/info/")
    public RespResult CreateTags(@RequestParam Map<String, String> data) {
        return createTagsService.CreateTags(data);
    }
}
