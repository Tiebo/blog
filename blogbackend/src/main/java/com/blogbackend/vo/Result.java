package com.blogbackend.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private boolean success;

    private int code;

    private String msg;

    private JSONObject data;

    public static Result success(JSONObject data) {
        return new Result(true, 200, "success", data);
    }
    public static Result fail(int code, String msg) {
        return new Result(false, code, msg, null);
    }
}
