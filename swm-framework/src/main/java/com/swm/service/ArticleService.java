package com.swm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swm.domain.ResponseResult;
import com.swm.domain.dto.AddArticleDto;
import com.swm.domain.dto.ArticleDto;
import com.swm.domain.dto.ArticleListDto;
import com.swm.domain.entity.Article;
import com.swm.domain.vo.ArticleVo;
import com.swm.domain.vo.PageVo;

public interface ArticleService extends IService<Article> {

    ResponseResult getHotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDto article);

    PageVo selectArticlePage(ArticleListDto article, Integer pageNum, Integer pageSize);

    ArticleVo getInfo(Long id);

    void edit(ArticleDto articleDto);
}
