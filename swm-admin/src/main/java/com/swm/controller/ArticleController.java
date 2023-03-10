package com.swm.controller;

import com.swm.domain.ResponseResult;
import com.swm.domain.dto.AddArticleDto;
import com.swm.domain.dto.ArticleDto;
import com.swm.domain.dto.ArticleListDto;
import com.swm.domain.entity.Article;
import com.swm.domain.vo.ArticleVo;
import com.swm.domain.vo.PageVo;
import com.swm.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }
    @GetMapping("/list")
    public ResponseResult list(ArticleListDto articleListDto, Integer pageNum, Integer pageSize)
    {
        PageVo pageVo = articleService.selectArticlePage(articleListDto,pageNum,pageSize);
        return ResponseResult.okResult(pageVo);
    }

    @GetMapping(value = "/{id}")
    public ResponseResult getInfo(@PathVariable(value = "id")Long id){
        ArticleVo article = articleService.getInfo(id);
        return ResponseResult.okResult(article);
    }

    @PutMapping
    public ResponseResult edit(@RequestBody ArticleDto article){
        articleService.edit(article);
        return ResponseResult.okResult();
    }
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id){
        articleService.removeById(id);
        return ResponseResult.okResult();
    }

}
