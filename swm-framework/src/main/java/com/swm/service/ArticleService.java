package com.swm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.swm.domain.ResponseResult;
import com.swm.domain.entity.Article;

public interface ArticleService extends IService<Article> {

    ResponseResult getHotArticleList();
}
