package com.swm.controller;

import com.swm.domain.ResponseResult;
import com.swm.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@Api(description = "文章相关接口")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

//    @GetMapping("/list")
//    public List<Article> test() {
//        return articleService.list();
//    }
    @GetMapping("/hotArticleList")
    @ApiOperation(value = "获取热门文章")
    public ResponseResult hotArticleList(){
        //查询热门文章并封装成ResponseResult
        ResponseResult result = articleService.getHotArticleList();
        return result;
    }
    @GetMapping("/articleList")
    @ApiOperation(value = "获取文章列表")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum, pageSize, categoryId);
    }
    @PutMapping("/updateViewCount/{id}")
    @ApiOperation(value = "更新文章浏览量")
    public ResponseResult updateViewCount(@PathVariable("id") Long id ){
        return articleService.updateViewCount(id);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取文章详情")
    public ResponseResult getArticleDetail(@PathVariable("id")Long id){
        return articleService.getArticleDetail(id);
    }
}
