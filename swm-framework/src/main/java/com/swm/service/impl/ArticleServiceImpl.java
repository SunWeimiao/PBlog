package com.swm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swm.domain.entity.Article;
import com.swm.mapper.ArticleMapper;
import com.swm.service.ArticleService;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
