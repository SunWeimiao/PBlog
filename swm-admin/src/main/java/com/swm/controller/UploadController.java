package com.swm.controller;

import com.swm.domain.ResponseResult;

import com.swm.service.UploadService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Api("文件上传")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    @ApiOperation("上传头像")
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile multipartFile) {

        return uploadService.uploadImg(multipartFile);

    }
}
