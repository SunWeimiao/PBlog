package com.swm.controller;

import com.swm.domain.ResponseResult;
import com.swm.domain.dto.AddArticleDto;
import com.swm.domain.dto.ArticleDto;
import com.swm.domain.dto.ArticleListDto;
import com.swm.domain.vo.ArticleVo;
import com.swm.domain.vo.PageVo;
import com.swm.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
@Api(description = "文章管理")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping
    @ApiOperation("新增文章")
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }
    @GetMapping("/list")
    @ApiOperation("获取文章列表")
    public ResponseResult list(ArticleListDto articleListDto, Integer pageNum, Integer pageSize)
    {
        PageVo pageVo = articleService.selectArticlePage(articleListDto,pageNum,pageSize);
        return ResponseResult.okResult(pageVo);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("获取文章详情")
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        ArticleVo article = articleService.getInfo(id);
        return ResponseResult.okResult(article);
    }

    @PutMapping
    @ApiOperation("更新文章")
    public ResponseResult edit(@RequestBody ArticleDto article){
        articleService.edit(article);
        return ResponseResult.okResult();
    }
    @DeleteMapping("/{id}")
    @ApiOperation("删除文章")
    public ResponseResult delete(@PathVariable Long id){
        articleService.removeById(id);
        return ResponseResult.okResult();
    }

}
