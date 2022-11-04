package com.blogbackend.controller.tags;

import com.blogbackend.service.tags.RemoveTagsService;
import com.blogbackend.vo.RespResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveTagsController {

    @Autowired
    private RemoveTagsService removeTagsService;

    @PostMapping("/api/tags/remove/info/")
    public RespResult removeTags(@RequestParam Map<String, String> data) {
        Integer tag_id = Integer.valueOf(data.get("tag_id"));
        return removeTagsService.removeTags(tag_id);
    }
}
