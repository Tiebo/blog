package com.blogbackend.service.user;

import com.blogbackend.vo.RespResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    RespResult uploadImg(MultipartFile img);
}
