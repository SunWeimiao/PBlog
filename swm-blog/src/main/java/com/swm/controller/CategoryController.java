package com.swm.controller;

import com.swm.domain.ResponseResult;
import com.swm.domain.entity.Category;
import com.swm.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Api(description = "文章分类相关接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    @ApiOperation(value = "获取文章分类列表")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}
