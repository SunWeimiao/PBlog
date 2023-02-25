package com.swm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swm.domain.ResponseResult;
import com.swm.domain.entity.Article;
import com.swm.mapper.ArticleMapper;
import com.swm.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public ResponseResult getHotArticleList() {
        //查询热门文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是已发布的文章
        queryWrapper.eq(Article::getStatus, 0);
        //按浏览量降序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多查十条
        Page<Article> page = new Page<>(1,10);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();
        return ResponseResult.okResult(articles);
    }
}
